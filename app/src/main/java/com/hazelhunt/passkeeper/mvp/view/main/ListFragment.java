package com.hazelhunt.passkeeper.mvp.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.mvp.presenter.main.ListPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import sqlite4a.SQLiteDb;

public class ListFragment extends Fragment implements IRecyclerListView {

    @Bind(R.id.rv) RecyclerView mRecyclerView;

    private ListPresenter mPresenter;

    static {
        SQLiteDb.loadLibrary();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ListPresenter(this);
        mPresenter.onAttach(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycler_view, container, false);

        ButterKnife.bind(this, v);

        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);

        final RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getRecyclerAdapter(getActivity());
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void setRecyclerAdapter(RecyclerView.Adapter adapter) {
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
    }
}
