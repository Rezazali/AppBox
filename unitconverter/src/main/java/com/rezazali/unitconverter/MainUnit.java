package com.rezazali.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.rezazali.unitconverter.database.DataSource;
import com.rezazali.unitconverter.fragment.QuantitiesFragment;

public class MainUnit extends AppCompatActivity {

    public static final String DEVICE_INFORMATION = "Device model: " +
            Build.MODEL + "\nAndroid version: " + Build.VERSION.RELEASE;
    public static String APP_VERSION;
    public static String BACK_FROM_CHILD_KEY = "quantities_fragment";

    private DataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_unit);

        getSupportFragmentManager().beginTransaction().replace(
                R.id.fl_main, new QuantitiesFragment(), "quantities_fragment").commit();

        mDataSource = new DataSource(this);
        mDataSource.open();


        try {
            APP_VERSION = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        mDataSource.open();
    }
}
