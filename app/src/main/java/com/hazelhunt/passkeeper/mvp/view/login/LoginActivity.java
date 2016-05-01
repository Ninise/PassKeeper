package com.hazelhunt.passkeeper.mvp.view.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.mvp.presenter.login.LoginPresenter;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    @Bind(R.id.loginToolbar) Toolbar mLoginToolbar;
    @Bind(R.id.loginEntryButton) Button mEntryButton;
    @Bind(R.id.loginPinCodeEditText) EditText mPinCodeEditText;
    @BindString(R.string.app_name) String mAppNameString;
    @BindString(R.string.login_right_passwrod) String mRightPasswordString;
    @BindString(R.string.login_wrong_password) String mWrongPasswordString;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        ButterKnife.bind(this);

        mPresenter = new LoginPresenter(this);

        setSupportActionBar(mLoginToolbar);
        mLoginToolbar.setTitle(mAppNameString);

        RxView.clicks(mEntryButton).subscribe(aVoid -> mPresenter.entry(mPinCodeEditText.getText().toString()));

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onEntrySuccess() {
        Toast.makeText(this, mRightPasswordString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEntryFailed() {
        Toast.makeText(this, mWrongPasswordString, Toast.LENGTH_SHORT).show();
    }
}
