package com.hazelhunt.passkeeper.mvp.presenter.login;

import com.hazelhunt.passkeeper.mvp.view.login.ILoginView;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView mView;

    public LoginPresenter(ILoginView view) {
        this.mView = view;
    }


    @Override
    public void entry(String pin) {

    }
}
