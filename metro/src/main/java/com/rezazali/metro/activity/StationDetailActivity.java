package com.rezazali.metro.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rezazali.metro.R;
import com.rezazali.metro.config.AppConfig;
import com.rezazali.metro.databinding.ActivityStationDetailBinding;
import com.rezazali.metro.model.Station;

public class StationDetailActivity extends AppCompatActivity {

    ActivityStationDetailBinding binding;

    Station station;
    Bundle bundle;

    AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStationDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        appConfig = new AppConfig(getApplicationContext());
        bundle = getIntent().getExtras();

        station = bundle.getParcelable("data");

        Log.e("", "");

        binding.txtStationTitle.setText(station.getTitle());
        binding.txtEnglishTitle.setText(station.getTitleEnglish());
        binding.txtAddress.setText(station.getAddr());
        binding.txtLineTitle.setText(appConfig.getTitle());
        binding.txtLineTitleEnglish.setText(appConfig.getEnglishTitle());

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (station.getAtm() == 0) {

            binding.txtAtmEn.setTextColor(getResources().getColor(R.color.White));
            binding.txtAtmFa.setTextColor(getResources().getColor(R.color.White));
            binding.imgAtm.setColorFilter(ContextCompat.getColor(this,
                    R.color.White), android.graphics.PorterDuff.Mode.SRC_IN);


        }


        switch (station.getLineId()) {

            case 1:

                binding.relStation.setBackgroundResource(R.color.Red);
                binding.linFeatures.setBackgroundResource(R.color.Red);


                break;

            case 2:
                binding.relStation.setBackgroundResource(R.color.Blue);
                binding.linFeatures.setBackgroundResource(R.color.Blue);
                break;

            case 3:
                binding.relStation.setBackgroundResource(R.color.LowBlue);
                binding.linFeatures.setBackgroundResource(R.color.LowBlue);
                break;

            case 4:
                binding.relStation.setBackgroundResource(R.color.Yellow);
                binding.linFeatures.setBackgroundResource(R.color.Yellow);
                break;


            case 5:
                binding.relStation.setBackgroundResource(R.color.Green);
                binding.linFeatures.setBackgroundResource(R.color.Green);
                break;

            case 6:
                binding.relStation.setBackgroundResource(R.color.Purple);
                binding.linFeatures.setBackgroundResource(R.color.Purple);
                break;

            case 7:

                binding.relStation.setBackgroundResource(R.color.Purple);
                binding.linFeatures.setBackgroundResource(R.color.Purple);
                break;


        }


    }
}