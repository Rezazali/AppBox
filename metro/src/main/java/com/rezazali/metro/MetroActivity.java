package com.rezazali.metro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rezazali.metro.adapter.LinesAdapter;
import com.rezazali.metro.databinding.ActivityMetroBinding;
import com.rezazali.metro.model.Lines;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MetroActivity extends AppCompatActivity {

    ActivityMetroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMetroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.progressBar.setVisibility(View.VISIBLE);

        String url = "https://androidsupport.ir/pack/metro/getLines.php";
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            Log.e("","");

            binding.progressBar.setVisibility(View.GONE);

            try {
                JSONArray array = new JSONArray(response);
                Log.e("","");

                List<Lines> linesList = new ArrayList<>();

                for(int i = 0 ; i< array.length() ; i++){

                    JSONObject object = array.getJSONObject(i);

                    int id = Integer.parseInt(object.getString("id"));
                    String title = object.getString("title");
                    String englishTitle = object.getString("EnglishTitle");

                    Lines lines = new Lines(id, title , englishTitle);

                    linesList.add(lines);
                }

                LinesAdapter adapter = new LinesAdapter(getApplicationContext() , linesList);
                binding.recyclerViewLines.setAdapter(adapter);

                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext() , RecyclerView.VERTICAL , false);
                binding.recyclerViewLines.setLayoutManager(manager);

                Log.e("","");

            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("","");
                binding.progressBar.setVisibility(View.GONE);

            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);



    }
}