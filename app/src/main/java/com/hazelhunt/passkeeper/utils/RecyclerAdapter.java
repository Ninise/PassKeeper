package com.hazelhunt.passkeeper.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.mvp.model.CardViewViewHolder;
import com.hazelhunt.passkeeper.mvp.model.DatabaseWorker;
import com.hazelhunt.passkeeper.mvp.model.PassModel;
import com.hazelhunt.passkeeper.mvp.view.add.AddActivity;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<CardViewViewHolder> {

    private List<PassModel> mDataSet;
    private Context mContext;


    public RecyclerAdapter(Context context, List<PassModel> dataSet) {
        mDataSet = dataSet;
        mContext = context;
    }

    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(CardViewViewHolder holder, int position) {
        holder.mTitleTextView.setText(mDataSet.get(holder.getAdapterPosition()).getUrl());
        holder.mCardView.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, AddActivity.class)
            .putExtra(DatabaseWorker.KEY_ID, mDataSet.get(holder.getAdapterPosition()).getId())
            .putExtra(DatabaseWorker.KEY_URL, mDataSet.get(holder.getAdapterPosition()).getUrl())
            .putExtra(DatabaseWorker.KEY_LOGIN, mDataSet.get(holder.getAdapterPosition()).getLogin())
            .putExtra(DatabaseWorker.KEY_PASS, mDataSet.get(holder.getAdapterPosition()).getPass())
            .putExtra(DatabaseWorker.KEY_EMAIL, mDataSet.get(holder.getAdapterPosition()).getEmail())
            .putExtra(DatabaseWorker.KEY_EXTRA, mDataSet.get(holder.getAdapterPosition()).getExtra())
            );
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}