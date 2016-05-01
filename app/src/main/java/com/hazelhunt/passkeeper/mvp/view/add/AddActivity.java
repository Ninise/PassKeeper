package com.hazelhunt.passkeeper.mvp.view.add;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.mvp.presenter.add.AddPresenter;
import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;
import sqlite4a.SQLiteDb;

public class AddActivity extends AppCompatActivity implements IAddView {

    @Bind(R.id.addToolbar) Toolbar mAddToolbar;
    @Bind(R.id.addUrlEditText) EditText mUrlEditText;
    @Bind(R.id.addLoginEditText) EditText mLoginEditText;
    @Bind(R.id.addPassEditText) EditText mPassEditText;
    @Bind(R.id.addEmailEditText) EditText mEmailEditText;
    @Bind(R.id.addExtraEditText) EditText mExtraEditText;
    @Bind(R.id.addSaveButton) Button mSaveButton;
    @BindString(R.string.add_new) String mToolTitleString;
    @BindString(R.string.settings_changes_saved) String mSaveString;
    @BindDrawable(R.drawable.ic_action_navigation_arrow_back) Drawable mBackDrawable;

    private AddPresenter mPresenter;

    static {
        SQLiteDb.loadLibrary();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        ButterKnife.bind(this);

        mPresenter = new AddPresenter(this);
        mPresenter.onAttach(this);

        setSupportActionBar(mAddToolbar);
        mAddToolbar.setTitle(mToolTitleString);
        mAddToolbar.setNavigationIcon(mBackDrawable);
        RxToolbar.navigationClicks(mAddToolbar).subscribe(aVoid -> onBackPressed());

        RxView.clicks(mSaveButton).subscribe(aVoid ->
                mPresenter.add(
                        mUrlEditText.getText().toString(),
                        mLoginEditText.getText().toString(),
                        mPassEditText.getText().toString(),
                        mEmailEditText.getText().toString(),
                        mExtraEditText.getText().toString())
        );
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void saved() {
        Toast.makeText(this, mSaveString, Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}
