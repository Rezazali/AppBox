package com.rezazali.unitconverter.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rezazali.unitconverter.ConvertActivity;
import com.rezazali.unitconverter.R;
import com.rezazali.unitconverter.model.DataItemQuantities;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class QuantitiesAdapter extends RecyclerView.Adapter<QuantitiesAdapter.ViewHolder> {

    public static final String ITEM_KEY = "item_key";
    private final List<DataItemQuantities> mItems;
    private final Context mContext;

    public QuantitiesAdapter(Context context, List<DataItemQuantities> items) {
        this.mContext = context;
        this.mItems = items;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.grid_item_quantities, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DataItemQuantities item = mItems.get(position);

        holder.textView.setText(item.getTitle());
        try {
            String imageFile = item.getImage();
            InputStream inputStream = mContext.getAssets().open(imageFile);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.relativeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ConvertActivity.class);
            intent.putExtra(ITEM_KEY, item);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        RelativeLayout relativeLayout;
        ImageView imageView;
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.rl_list_quantities);
            imageView = itemView.findViewById(R.id.iv_list_quantities);
            textView = itemView.findViewById(R.id.tv_list_quantities);

            mView = itemView;
        }
    }
}