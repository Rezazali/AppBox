package com.rezazali.metro.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rezazali.metro.R;
import com.rezazali.metro.activity.StationsListActivity;
import com.rezazali.metro.config.AppConfig;
import com.rezazali.metro.model.Lines;

import java.util.List;

public class LinesAdapter extends RecyclerView.Adapter<LinesAdapter.ZaliVH> {

    List<Lines> linesList;
    LayoutInflater inflater;
    AppConfig appConfig;
    Context context;
    public LinesAdapter(Context context, List<Lines> linesList) {
        this.linesList = linesList;
        inflater = LayoutInflater.from(context);
        this.context = context;
        appConfig = new AppConfig(context);
    }


    //کار این متد این است که یک لایوت سفارشی را به این آداپتر شاسایی کند
    @NonNull
    @Override
    public ZaliVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.lines_row, null);

        return new ZaliVH(view);
    }


    // مقدارهی اطلاعات است
    @Override
    public void onBindViewHolder(@NonNull ZaliVH holder, int position) {

        Lines lines = linesList.get(position);

        //Typeface typeface = Typeface.createFromAsset(context.getAssets() , "fonts/BHoma.ttf");

        holder.txt_title.setText(lines.getTitle());
        //holder.txt_title.setTypeface(typeface);
        holder.txt_english_title.setText(lines.getEnglishTitle());
        //holder.txt_english_title.setTypeface(typeface);

        switch (lines.getId()) {

            case 1:

                holder.rel_lines.setBackgroundResource(R.color.Red);


                break;

            case 2:
                holder.rel_lines.setBackgroundResource(R.color.Blue);
                break;

            case 3:
                holder.rel_lines.setBackgroundResource(R.color.LowBlue);
                break;

            case 4:
                holder.rel_lines.setBackgroundResource(R.color.Yellow);
                break;


            case 5:
                holder.rel_lines.setBackgroundResource(R.color.Green);
                break;

            case 6:
                holder.rel_lines.setBackgroundResource(R.color.Purple);
                break;

            case 7:

                holder.rel_lines.setBackgroundResource(R.color.Purple);
                break;


        }

        holder.rel_lines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                appConfig.saveInfo(lines.getTitle() , lines.getEnglishTitle());

                Intent intent = new Intent(context  , StationsListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", lines.getId());
                intent.putExtra("name", lines.getTitle());
                intent.putExtra("englishTitle" , lines.getEnglishTitle());
                context.startActivity(intent);


            }
        });


    }

    //سایز اداپتر
    @Override
    public int getItemCount() {
        return linesList.size();
    }

    class ZaliVH extends RecyclerView.ViewHolder {

        AppCompatTextView txt_title;
        AppCompatTextView txt_english_title;
        RelativeLayout rel_lines;


        public ZaliVH(@NonNull View itemView) {
            super(itemView);

            txt_title = itemView.findViewById(R.id.txt_title);
            txt_english_title = itemView.findViewById(R.id.txt_english_title);
            rel_lines = itemView.findViewById(R.id.rel_lines);

        }
    }

}
