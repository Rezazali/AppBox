package com.rezazali.unitconverter.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rezazali.unitconverter.R;
import com.rezazali.unitconverter.adapter.QuantitiesAdapter;
import com.rezazali.unitconverter.database.DataSource;
import com.rezazali.unitconverter.dataprovider.QuantitiesDataProvider;
import com.rezazali.unitconverter.model.DataItemQuantities;

import java.util.Collections;
import java.util.List;

public class QuantitiesFragment extends Fragment {

    private View view;
    private Context context;
    private DataSource mDataSource;

    private List<DataItemQuantities> dataItemQuantitiesList =
            QuantitiesDataProvider.dataItemQuantitiesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quantities, container, false);
        context = view.getContext();
        mDataSource = new DataSource(context);
        mDataSource.open();
        mDataSource.seedDataBase(dataItemQuantitiesList);
        quantitiesRecyclerViews();

        return view;
    }

    private void quantitiesRecyclerViews() {
        dataItemQuantitiesList = mDataSource.getAllItems(false);

        Collections.sort(dataItemQuantitiesList, (o1, o2) ->
                getString(o1.getTitle()).compareTo(getString(o2.getTitle())));

        QuantitiesAdapter quantitiesAdapter = new QuantitiesAdapter(context, dataItemQuantitiesList);
        RecyclerView quantitiesRV = view.findViewById(R.id.rv_quantities);
        quantitiesRV.setLayoutManager(new GridLayoutManager(context, 3));
        quantitiesRV.setAdapter(quantitiesAdapter);
    }
}
