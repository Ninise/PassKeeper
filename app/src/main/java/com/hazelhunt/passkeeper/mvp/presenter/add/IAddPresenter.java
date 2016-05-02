package com.hazelhunt.passkeeper.mvp.presenter.add;

import android.content.Context;
import android.view.MenuItem;

import com.hazelhunt.passkeeper.mvp.model.PassModel;

public interface IAddPresenter {

    void onAttach(Context context);
    void add(PassModel model);
    void update(PassModel model);
    boolean delete(MenuItem item, PassModel model);

}
