package com.rezazali.pdfreader;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class DocumentActivity extends AppCompatActivity {

    private String filePath="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        PDFView pdfView =findViewById(R.id.pdfView);
        filePath=getIntent().getStringExtra("path");

        File file =new File(filePath);
        Uri path =Uri.fromFile(file);
        pdfView.fromUri(path).load();
    }
}