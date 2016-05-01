package com.hazelhunt.passkeeper.mvp.presenter.settings;

import android.content.Context;

public interface ISettingsPresenter {

    void save(Context context, boolean deleteAll, String newPin);

}
