package com.hazelhunt.passkeeper.mvp.presenter.main;


import android.util.Log;
import android.view.MenuItem;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.mvp.view.about.AboutActivity;
import com.hazelhunt.passkeeper.mvp.view.add.AddActivity;
import com.hazelhunt.passkeeper.mvp.view.main.IMainView;
import com.hazelhunt.passkeeper.mvp.view.settings.SettingsActivity;

public class MainPresenter implements IMainPresenter {

    private IMainView mView;

    public MainPresenter(IMainView view) {
        this.mView = view;
    }

    @Override
    public boolean menuSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menuAddNew:
                mView.switchToActivity(AddActivity.class);
                return true;
            case R.id.menuSettings:
                mView.switchToActivity(SettingsActivity.class);
                return true;
            case R.id.menuAbout:
                mView.switchToActivity(AboutActivity.class);
                return true;
            case R.id.menuExit:
                mView.exit();
                return true;
        }

        return false;
    }
}
