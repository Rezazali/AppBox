package com.rezazali.flashlight;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FlashLightActivity extends AppCompatActivity {

    private CameraManager objCameraManager;
    private String mCameraId;
    private ImageView ivOnOFF;
    private MediaPlayer objMediaPlayer;

    private Boolean isTorchOn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);

        ivOnOFF = (ImageView) findViewById(R.id.ivOnOFF);
        isTorchOn = false;

        /**
         * Check if device contains flashlight
         *
         * if not then exit from screen
         *
         */
        Boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {
            AlertDialog alert = new AlertDialog.Builder(FlashLightActivity.this).create();
            alert.setTitle(getString(R.string.app_name));
            alert.setMessage(getString(R.string.msg_error));
            alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.lbl_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
            return;
        }


        objCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = objCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        ivOnOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isTorchOn) {
                        turnOffLight();
                        isTorchOn = false;
                    } else {
                        turnOnLight();
                        isTorchOn = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Method for turning light ON
     */
    public void turnOnLight() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                objCameraManager.setTorchMode(mCameraId, true);
                playOnOffSound();
                ivOnOFF.setImageResource(R.drawable.on);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for turning light OFF
     */
    public void turnOffLight() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                objCameraManager.setTorchMode(mCameraId, false);
                playOnOffSound();
                ivOnOFF.setImageResource(R.drawable.off);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playOnOffSound() {
        objMediaPlayer = MediaPlayer.create(FlashLightActivity.this, R.raw.flash_sound);
        objMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        objMediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isTorchOn) {
            turnOffLight();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isTorchOn) {
            turnOffLight();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isTorchOn) {
            turnOnLight();
        }
    }
}
