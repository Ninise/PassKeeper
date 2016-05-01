package com.hazelhunt.passkeeper.mvp.presenter.main;

import android.content.Context;

import com.hazelhunt.passkeeper.mvp.model.DatabaseWorker;
import com.hazelhunt.passkeeper.mvp.view.main.IRecyclerListView;
import com.hazelhunt.passkeeper.utils.RecyclerAdapter;

public class ListPresenter implements IListPresenter {

    private IRecyclerListView mView;

    public ListPresenter(IRecyclerListView view) {
        this.mView = view;
    }


    @Override
    public void onAttach(Context context) {
        DatabaseWorker.register(context);
    }

    @Override
    public void getRecyclerAdapter(Context context) {
        DatabaseWorker.loadAll().toList().subscribe(models -> {
            mView.setRecyclerAdapter(new RecyclerAdapter(context, models));
        });
    }
}
