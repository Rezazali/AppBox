package com.rezazali.sudoku;

import static android.widget.Toast.LENGTH_LONG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Scanner;
import java.util.Stack;

public class GameActivity extends AppCompatActivity {

    static public final int[] NUMBER_OF_EMPTY_CELLS = {0, 30, 35, 45, 50};
    static public final String[] DIFFICULT_NAME = {"NONE", "Easy", "Normal", "Hard", "Extreme"};

    static private SudokuGrid grid;
    static private Numpad numpad;
    static private Stack<CellState> stack = new Stack<>();

    SudokuSolver solver = new SudokuSolver();


    /* game state */
    private int[][] solution;
    private int difficulty;
    static private int status; // -3 game done | -2: auto solved | -1: auto fill | 0: playing | 1: player solved
    private Timer timer;

    private void generateGrid() {
        // generate a grid
        solution = solver.getRandomGrid(NUMBER_OF_EMPTY_CELLS[difficulty]);
        int[][] masks = new int[9][9];

        // compute masks
        for (int row = 0; row < 9; ++row) {
            for (int col = 0; col < 9; ++col) {
                if(solution[row][col] > 9) {
                    masks[row][col] = 0;
                }
                else {
                    masks[row][col] = (1 << solution[row][col]);
                }
            }
        }

        grid = new SudokuGrid(this, masks, solution);
    }

    private void restoreGrid(String solutionString, String gridString) {
        // restore solution
        solution = new int[9][9];
        Scanner scanner = new Scanner(solutionString);
        for (int row = 0; row < 9; ++row) {
            for (int col = 0; col < 9; ++col) {
                solution[row][col] = scanner.nextInt();
            }
        }
        // restore masks
        int[][] masks = new int[9][9];
        scanner = new Scanner(gridString);
        for (int row = 0; row < 9; ++row) {
            for (int col = 0; col < 9; ++col) {
                masks[row][col] = scanner.nextInt();
            }
        }

        grid = new SudokuGrid(this, masks, solution);
    }

    private void saveGame() {
        if (status < -1) return;
        int[][] currentMask = grid.getCurrentMasks();
        GameState state = new GameState(status, difficulty, timer.getElapsedSeconds(), solution, currentMask);
        try {
            DatabaseHelper DBHelper = DatabaseHelper.newInstance(this);
            SQLiteDatabase database = DBHelper.getWritableDatabase();
            database.insert("GameState", null, state.getContentValues());
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        saveGame();
        super.onPause();
    }

    boolean wannaBack = false;
    @Override
    public void onBackPressed() {
        if (wannaBack) {
            super.onBackPressed();
            return;
        }

        wannaBack = true;
        Toast.makeText(this, "B???m 'BACK' l???n n???a ????? quay l???i menu ch??nh", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                wannaBack = false;
            }
        }, 3000);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_solve) {
            onClickSolve();
        } else if (itemId == R.id.action_reset) {
            onClickReset();
        } else if (itemId == R.id.action_autofill) {
            onClickAutoFill();
        } else if (itemId == R.id.action_tutorial) {
            onClickTutorial();
        }

        return true;
    }

    private void onClickAutoFill() {
        if (status < -1) return;
        if (status == -1) {
            autoFill();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog);
            dialog.setMessage("N???u s??? d???ng t??nh n??ng n??y, k???t qu??? c???a b???n s??? kh??ng ???????c c??ng nh???n tr??n b???ng x???p h???ng. \nB???n c?? ch???c ch???n mu???n s??? d???ng?")
                    .setTitle("Ch?? ??\n")
                    .setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            status = -1;
                            autoFill();
                        }
                    })
                    .setNegativeButton("T??? ch???i", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .show();
        }
    }

    private void onClickTutorial() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.MyTutorialTheme);
        Spannable message = SpannableWithImage.getTextWithImages(this, getString(R.string.tutorial), 50);

        int position = getString(R.string.tutorial).indexOf("Ch?? ??:");
        message.setSpan(new RelativeSizeSpan(1.2f), position, position + 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        dialog.setMessage(message).setTitle("H?????ng d???n").show();
    }

    private void autoFill() {
        grid.fill();
        updateNumpad();
    }

    @SuppressLint({"ResourceAsColor", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle bundle = getIntent().getExtras();
        difficulty = bundle.getInt("difficulty", 0);
        status = bundle.getInt("status", 0);

        // lock screen orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // setup action bar title
        //getSupportActionBar().setTitle(DIFFICULT_NAME[difficulty]);

        // hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setTypeface(AppConstant.APP_FONT);

        String solutionString = bundle.getString("solutionString", "none");
        String gridString = bundle.getString("gridString", "none");
        if (solutionString.equals("none") || gridString.equals("none")) {
            generateGrid();
        } else {
            restoreGrid(solutionString, gridString);
        }

        numpad = new Numpad(this);

        int elapsedTime = bundle.getInt("elapsedSeconds", 0);
        timer = new Timer(this, elapsedTime);
        timer.start();
    }

    public static void updateNumpad() {
        Cell selectedCell = grid.getSelectedCell();
        if (selectedCell != null) {
            numpad.update(selectedCell.getMask(), selectedCell.isMarked());
        }
    }

    public void onClickSubmit(View view) {
        if (!grid.isLegalGrid()) {
            Toast.makeText(this, "H??y ho??n thi???n b???ng", LENGTH_LONG).show();
            return;
        }
        if (solver.checkValidGrid(grid.getNumbers())) {
            Toast.makeText(this, "Ch??c m???ng, ????p ??n ch??nh x??c", LENGTH_LONG).show();
            status = 0;
            if (status >= 0) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog);
                dialog.setMessage("B???n c?? mu???n l??u k???t qu??? tr??n b???ng x???p h???ng?")
                        .setTitle("Th??ng b??o\n")
                        .setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                saveAchievement();
                            }
                        })
                        .setNegativeButton("T??? ch???i", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
                timer.stop();
                status = 1;
            }
            // set status to GAME_DONE
            status = -3;
        } else {
            Toast.makeText(this, "????p ??n sai", LENGTH_LONG).show();
        }
    }

    private void saveAchievement() {
        Intent intent = new Intent(this, SaveAchievementActivity.class);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("elapsedSeconds", timer.getElapsedSeconds());
        startActivity(intent);
    }

    public void onClickSolve() {
        if(status >= 0) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog);
            dialog.setMessage("N???u s??? d???ng t??nh n??ng n??y, k???t qu??? c???a b???n s??? kh??ng ???????c c??ng nh???n tr??n b???ng x???p h???ng. \nB???n c?? ch???c ch???n mu???n s??? d???ng?")
                    .setTitle("Ch?? ??\n")
                    .setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            status = -2;
                            onClickSolve();
                        }
                    })
                    .setNegativeButton("T??? ch???i", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .show();
        }

        if(status < 0) {
            status = -2;
            grid.showSolution();
            updateNumpad();
            timer.stop();
        }
    }

    private void onClickReset() {
        if (status < -1) return;
        grid.clear();
        updateNumpad();
    }

    public static void onPressNumpad(int number) {
        if(status < -1) return;
        Cell selectedCell = grid.getSelectedCell();
        if (number < 10) {
            // backup current selected cell state
            stack.push(selectedCell.getState());
            // add a number to selected cell
            selectedCell.addNumber(number);
        } else if (number == 10) {
            // mark selected cell
            selectedCell.setMarked(!selectedCell.isMarked());
        } else if (number == 11) {
            // restore previous selected cell state
            if (!stack.isEmpty()) {
                CellState preState = stack.peek();
                stack.pop();
                int index = preState.index;
                int row = index / 9;
                int col = index - row * 9;
                grid.getCell(row, col).setMask(preState.mask);
            }
        }
        updateNumpad();
    }

    public static void setNumpadVisible(int state) {
        numpad.setVisibility(state);
    }

    public static void highlightNeighborCells(int index) {
        grid.highlightNeighborCells(index);
    }

    public static void setSelectedCell(int index) {
        grid.setSelectedCell(index);
    }
}
