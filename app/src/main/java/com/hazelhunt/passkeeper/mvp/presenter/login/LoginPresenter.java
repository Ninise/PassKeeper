package com.hazelhunt.passkeeper.mvp.presenter.login;

import android.content.Context;

import com.hazelhunt.passkeeper.utils.HashGenerator;
import com.hazelhunt.passkeeper.utils.preferences.UserAccountPreferences;
import com.hazelhunt.passkeeper.mvp.view.login.ILoginView;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView mView;

    public LoginPresenter(ILoginView view) {
        this.mView = view;
    }


    @Override
    public void checkFirstEntry(Context context) {
        if (!UserAccountPreferences.getInstance(context).isRegistered()) {
            mView.showRegisterDialog();
        }
    }

    @Override
    public void registration(Context context, String pin) {
        if (!pin.equals("")) {
            UserAccountPreferences.getInstance(context).register(true);
            UserAccountPreferences.getInstance(context).savePin(HashGenerator.md5(pin));
        }
    }

    @Override
    public void entry(Context context, String pin) {
        if (HashGenerator.md5(pin).equals(UserAccountPreferences.getInstance(context).getPin())) {
            mView.onEntrySuccess();
        } else {
            mView.onEntryFailed();
        }
    }
}
