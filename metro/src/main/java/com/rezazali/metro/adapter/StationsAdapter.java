package com.rezazali.metro.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rezazali.metro.R;
import com.rezazali.metro.activity.StationDetailActivity;
import com.rezazali.metro.model.Station;

import java.util.List;

public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.StationVH> {

    Context context;
    List<Station> stationList;

    public StationsAdapter(Context context, List<Station> stationList) {
        this.stationList = stationList;
        this.context = context;
    }

    @NonNull
    @Override
    public StationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.station_row , null);
        return new StationVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationVH holder, int position) {


        Station station = stationList.get(position);

        holder.txt_title.setText(station.getTitle());
        holder.txt_english.setText(station.getTitleEnglish());

        holder.rel_crossLine.setVisibility(View.GONE);


        switch (station.getLineId()) {

            case 1:

                holder.rel_stations.setBackgroundResource(R.color.Red);


                break;

            case 2:
                holder.rel_stations.setBackgroundResource(R.color.Blue);
                break;

            case 3:
                holder.rel_stations.setBackgroundResource(R.color.LowBlue);
                break;

            case 4:
                holder.rel_stations.setBackgroundResource(R.color.Yellow);
                break;


            case 5:
                holder.rel_stations.setBackgroundResource(R.color.Green);
                break;

            case 6:
                holder.rel_stations.setBackgroundResource(R.color.Purple);
                break;

            case 7:

                holder.rel_stations.setBackgroundResource(R.color.Purple);
                break;


        }




        if (station.getCrossLineId()>0) {
            holder.rel_crossLine.setVisibility(View.VISIBLE);
            holder.txt_crossLine.setText(station.getCrossLineId()+"");
        }


        holder.rel_stations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context , StationDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data" ,station );

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    class StationVH extends RecyclerView.ViewHolder {

        AppCompatTextView txt_title;
        AppCompatTextView txt_english;
        AppCompatTextView txt_crossLine;
        RelativeLayout rel_stations;
        RelativeLayout rel_crossLine;

        public StationVH(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_english = itemView.findViewById(R.id.txt_english);
            txt_crossLine = itemView.findViewById(R.id.txt_crossLine);
            rel_stations = itemView.findViewById(R.id.rel_stations);
            rel_crossLine = itemView.findViewById(R.id.rel_crossLine);
        }
    }
}
