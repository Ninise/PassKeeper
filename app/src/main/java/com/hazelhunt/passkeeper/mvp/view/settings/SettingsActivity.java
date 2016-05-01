package com.hazelhunt.passkeeper.mvp.view.settings;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.mvp.presenter.settings.SettingsPresenter;
import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements ISettingsView {

    @Bind(R.id.settingsToolbar) Toolbar mSettingsToolbar;
    @Bind(R.id.settignsChangePincodeEditText) EditText mChangePinEditText;
    @Bind(R.id.settingsDeleteAllDataCheckBox) AppCompatCheckBox mDeleteAllCompatCheckBox;
    @Bind(R.id.settignsSaveButton) Button mSaveButton;
    @BindString(R.string.settings) String mToolTitleString;
    @BindString(R.string.settings_changes_saved) String mSavedString;
    @BindDrawable(R.drawable.ic_action_navigation_arrow_back) Drawable mBackDrawable;

    private SettingsPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        ButterKnife.bind(this);

        mPresenter = new SettingsPresenter(this);

        setSupportActionBar(mSettingsToolbar);
        mSettingsToolbar.setTitle(mToolTitleString);
        mSettingsToolbar.setNavigationIcon(mBackDrawable);
        RxToolbar.navigationClicks(mSettingsToolbar).subscribe(aVoid -> onBackPressed());

        RxView.clicks(mSaveButton).subscribe(aVoid -> mPresenter.save(
                this,
                mDeleteAllCompatCheckBox.isChecked(),
                mChangePinEditText.getText().toString())
        );
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void changesSaved() {
        Toast.makeText(this, mSavedString, Toast.LENGTH_SHORT).show();
    }
}
