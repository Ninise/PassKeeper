package com.hazelhunt.passkeeper.mvp.presenter.settings;

import android.content.Context;

import com.hazelhunt.passkeeper.mvp.view.settings.ISettingsView;
import com.hazelhunt.passkeeper.utils.HashGenerator;
import com.hazelhunt.passkeeper.utils.preferences.UserAccountPreferences;

public class SettingsPresenter implements ISettingsPresenter {

    private ISettingsView mView;

    public SettingsPresenter(ISettingsView view) {
        this.mView = view;
    }

    @Override
    public void save(Context context, boolean deleteAll, String newPin) {
        if (deleteAll) {
            UserAccountPreferences.getInstance(context).register(false);
            UserAccountPreferences.getInstance(context).savePin("");
        }

        if (!newPin.equals("")) {
            UserAccountPreferences.getInstance(context).savePin(HashGenerator.md5(newPin));
        }

        mView.changesSaved();
    }
}
