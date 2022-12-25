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
import com.rezazali.unitconverter.model.DataItemUnits;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UnitsAdapter extends RecyclerView.Adapter<UnitsAdapter.ViewHolder> {

    public static String UNIT_TITLE;

    private final List<DataItemUnits> mItems;
    private final Context mContext;
    private final DataItemQuantities mItemQuantities;

    public UnitsAdapter(Context context, List<DataItemUnits> items,
                        DataItemQuantities itemQuantities) {
        this.mContext = context;
        this.mItems = items;
        this.mItemQuantities = itemQuantities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item_units, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DataItemUnits item = mItems.get(position);

        holder.titleTV.setText(item.getTitle());
        holder.symbolTV.setText(item.getSymbol());
        try {
            String imageFile = item.getImage();
            InputStream inputStream = mContext.getAssets().open(imageFile);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.relativeLayout.setOnClickListener(v -> {

            if (ConvertActivity.FROM_UNIT_SELECTION_KEY.equals("from_unit_selection_key")) {
                UNIT_TITLE = mContext.getString(item.getTitle());
            } else if (ConvertActivity.TO_UNIT_SELECTION_KEY.equals("to_unit_selection_key")) {
                UNIT_TITLE = mContext.getString(item.getTitle());
            }

            Intent backIntent = new Intent(mContext, ConvertActivity.class);
            backIntent.putExtra(QuantitiesAdapter.ITEM_KEY, mItemQuantities);
            mContext.startActivity(backIntent);
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
        TextView titleTV, symbolTV;

        ViewHolder(View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.rl_list_units);
            imageView = itemView.findViewById(R.id.iv_list_units);
            titleTV = itemView.findViewById(R.id.tv_list_units_title);
            symbolTV = itemView.findViewById(R.id.tv_list_units_symbol);

            mView = itemView;
        }
    }
}