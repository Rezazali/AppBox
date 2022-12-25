package com.rezazali.metro.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rezazali.metro.adapter.StationsAdapter;
import com.rezazali.metro.constant.Constant;
import com.rezazali.metro.databinding.ActivityStationsListBinding;
import com.rezazali.metro.model.Station;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationsListActivity extends AppCompatActivity {

    ActivityStationsListBinding binding;


    Bundle bundle;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStationsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bundle = getIntent().getExtras();

        String name = bundle.getString("name");
        String englishTitle = bundle.getString("englishTitle");
        id = bundle.getInt("id");
        Log.e("", "");

        binding.txtTitle.setText(name);
        binding.txtTitleEnglish.setText(englishTitle);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, Constant.GET_STATIONS, response -> {

            binding.progressBar.setVisibility(View.GONE);
            Log.e("", "");

            List<Station> stationList = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(response);

                for (int i = 0; i < array.length(); i++) {

                    JSONObject object = array.getJSONObject(i);

                    int id = Integer.parseInt(object.getString("id"));
                    int lineId = Integer.parseInt(object.getString("LineId"));
                    int orderId = Integer.parseInt(object.getString("OrderID"));
                    int stationDuration = Integer.parseInt(object.getString("Station_Duration"));
                    String title = object.getString("Title");
                    String titleEnglish = object.getString("Title_English");
                    int crossLineId = Integer.parseInt(object.getString("CrossLine_ID"));
                    String addr = object.getString("addr");

                    int ticket = Integer.parseInt(object.getString("ticket"));
                    int escalator = Integer.parseInt(object.getString("escalator"));
                    int atm = Integer.parseInt(object.getString("atm"));
                    int taxi = Integer.parseInt(object.getString("taxi"));
                    int bus = Integer.parseInt(object.getString("bus"));
                    int phone = Integer.parseInt(object.getString("phone"));
                    int water = Integer.parseInt(object.getString("water"));
                    int lost = Integer.parseInt(object.getString("lost"));
                    int parking = Integer.parseInt(object.getString("parking"));
                    int elevator = Integer.parseInt(object.getString("elevator"));

                    String latitude = object.getString("latitude");
                    String longitude = object.getString("longitude");

                    Station station = new Station();
                    station.setId(id);
                    station.setLineId(lineId);
                    station.setOrderID(orderId);
                    station.setStationDuration(stationDuration);
                    station.setTitle(title);
                    station.setTitleEnglish(titleEnglish);
                    station.setCrossLineId(crossLineId);
                    station.setAddr(addr);
                    station.setTicket(ticket);
                    station.setEscalator(escalator);
                    station.setAtm(atm);
                    station.setTaxi(taxi);
                    station.setBus(bus);
                    station.setPhone(phone);
                    station.setWater(water);
                    station.setLost(lost);
                    station.setParking(parking);
                    station.setElevator(elevator);
                    station.setLatitude(latitude);
                    station.setLongitude(longitude);

                    stationList.add(station);
                }

                Log.e("","");

                StationsAdapter adapter = new StationsAdapter(getApplicationContext() ,stationList );
                binding.recyclerStations.setAdapter(adapter);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext() , RecyclerView.VERTICAL , false);
                binding.recyclerStations.setLayoutManager(linearLayoutManager);


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", "");
                binding.progressBar.setVisibility(View.GONE);
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id", id + "");

                return params;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        queue.add(request);


    }

}