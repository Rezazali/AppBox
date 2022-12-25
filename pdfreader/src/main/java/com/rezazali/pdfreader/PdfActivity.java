package com.rezazali.pdfreader;

import static android.util.Log.i;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PdfActivity extends AppCompatActivity implements OnPdfFileSelectListener {

    private PdfAdapter adapter;
    private List<File> pdfList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        runtimePermission();
    }
    private void runtimePermission() {
        Dexter.withContext(PdfActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displayPdf();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(PdfActivity.this, "Permission is Required", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public ArrayList<File> findPdfs(File file){
        ArrayList<File> arrayList=new ArrayList<>();
        File[] files= file.listFiles();
        i("FilesList", "findPdfs: "+file.listFiles());
        if (files !=null){
            for(File singleFile : files){
                if (singleFile.isDirectory() && !singleFile.isHidden()){
                    arrayList.addAll(findPdfs(singleFile));
                }else {
                    if (singleFile.getName().endsWith(".pdf")){
                        arrayList.add(singleFile);
                    }
                }
            }
        }else {
            Toast.makeText(this, "files value is null", Toast.LENGTH_SHORT).show();
        }

        return  arrayList;
    }
    private void displayPdf() {
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        pdfList=new ArrayList<>();
        pdfList.addAll(findPdfs(Environment.getExternalStorageDirectory()));
        adapter=new PdfAdapter(this,pdfList);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onPdfSelected(File file) {
        startActivity(new Intent(PdfActivity.this ,DocumentActivity.class)
                .putExtra("path",file.getAbsolutePath()));
    }
}