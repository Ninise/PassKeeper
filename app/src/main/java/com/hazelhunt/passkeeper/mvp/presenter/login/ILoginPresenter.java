package com.hazelhunt.passkeeper.mvp.presenter.login;


import android.content.Context;

public interface ILoginPresenter {

    void checkFirstEntry(Context context);
    void registration(Context context, String pin);
    void entry(Context context, String pin);

}
