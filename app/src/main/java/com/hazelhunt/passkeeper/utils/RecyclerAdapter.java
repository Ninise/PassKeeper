package com.hazelhunt.passkeeper.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.mvp.model.CardViewViewHolder;
import com.hazelhunt.passkeeper.mvp.model.PassModel;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<CardViewViewHolder> {

    private List<PassModel> mDataSet;

    public RecyclerAdapter(List<PassModel> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(CardViewViewHolder holder, int position) {
        holder.mTitleTextView.setText(mDataSet.get(holder.getAdapterPosition()).getUrl());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}