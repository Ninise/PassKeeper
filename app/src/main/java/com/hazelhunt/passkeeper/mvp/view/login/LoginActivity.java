package com.hazelhunt.passkeeper.mvp.view.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.mvp.presenter.login.LoginPresenter;
import com.hazelhunt.passkeeper.mvp.view.main.MainActivity;
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
    @BindString(R.string.registration) String mRegistrationString;
    @BindString(R.string.registration_content) String mContentDialogString;
    @BindString(R.string.string_ok) String mOkString;
    @BindString(R.string.string_cancel) String mCancelString;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        ButterKnife.bind(this);

        mPresenter = new LoginPresenter(this);

        setSupportActionBar(mLoginToolbar);
        mLoginToolbar.setTitle(mAppNameString);

        RxView.clicks(mEntryButton).subscribe(aVoid -> mPresenter.entry(this, mPinCodeEditText.getText().toString()));

        mPresenter.checkFirstEntry(this);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onEntrySuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onEntryFailed() {
        Toast.makeText(this, mWrongPasswordString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRegisterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);

        Context dialogContext = builder.getContext();
        EditText pinEditText = new EditText(dialogContext);
        pinEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setTitle(mRegistrationString);
        builder.setMessage(mContentDialogString);
        builder.setPositiveButton(mOkString, (dialogInterface, i) -> {
           mPresenter.registration(getApplicationContext(), pinEditText.getText().toString());
        });

        builder.setNegativeButton(mCancelString, null);

        builder.setView(pinEditText).show();
    }
}
