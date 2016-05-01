package com.hazelhunt.passkeeper.mvp.presenter.settings;

import android.content.Context;

import com.hazelhunt.passkeeper.mvp.view.settings.ISettingsView;

public class SettingsPresenter implements ISettingsPresenter {

    private ISettingsView mView;

    public SettingsPresenter(ISettingsView view) {
        this.mView = view;
    }

    @Override
    public void save(Context context, boolean deleteAll, String newPin) {
        
    }
}
