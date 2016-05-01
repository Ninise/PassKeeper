package com.hazelhunt.passkeeper.mvp.presenter.main;

import android.content.Context;

public interface IListPresenter {

    void onAttach(Context context);
    void getRecyclerAdapter(Context context);
}
