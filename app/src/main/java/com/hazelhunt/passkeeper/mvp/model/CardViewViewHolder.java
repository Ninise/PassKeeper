package com.hazelhunt.passkeeper.mvp.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hazelhunt.passkeeper.R;

public class CardViewViewHolder extends RecyclerView.ViewHolder {

    public final CardView mCardView;
    public final TextView mTitleTextView;

    public CardViewViewHolder(View itemView) {
        super(itemView);
        mCardView = (CardView) itemView.findViewById(R.id.cv);
        mTitleTextView = (TextView) itemView.findViewById(R.id.titleCardView);
    }
}
