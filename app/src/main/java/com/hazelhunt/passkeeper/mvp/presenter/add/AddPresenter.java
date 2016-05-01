package com.hazelhunt.passkeeper.mvp.presenter.add;

import com.hazelhunt.passkeeper.mvp.view.add.IAddView;

public class AddPresenter implements IAddPresenter {

    private IAddView mView;

    public AddPresenter(IAddView view) {
        this.mView = view;
    }

    @Override
    public void add(String url, String login, String pass, String email, String extra) {

    }
}
