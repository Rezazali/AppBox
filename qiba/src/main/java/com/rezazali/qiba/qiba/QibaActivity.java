package com.rezazali.qiba.qiba;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import com.rezazali.qiba.databinding.ActivityQibaBinding;
import com.rezazali.qiba.qiba.utily.AppSetting;
import com.rezazali.qiba.qiba.utily.GPSTracker;

import java.util.List;

public class QibaActivity extends AppCompatActivity {

    ActivityQibaBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQibaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}