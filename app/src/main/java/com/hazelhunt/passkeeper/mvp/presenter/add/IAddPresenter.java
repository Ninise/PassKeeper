package com.hazelhunt.passkeeper.mvp.presenter.add;

import android.content.Context;

public interface IAddPresenter {

    void onAttach(Context context);
    void add(String url, String login, String pass, String email, String extra);

}
