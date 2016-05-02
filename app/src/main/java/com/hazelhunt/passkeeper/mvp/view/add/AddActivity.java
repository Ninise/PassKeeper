package com.hazelhunt.passkeeper.mvp.view.add;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.mvp.model.DatabaseWorker;
import com.hazelhunt.passkeeper.mvp.model.PassModel;
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
    private long id;
    private PassModel mModel;

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
        mModel = new PassModel();

        if (getBundleNotNull()) {
            id = this.getIntent().getLongExtra(DatabaseWorker.KEY_ID, 0);
            mUrlEditText.setText(this.getIntent().getStringExtra(DatabaseWorker.KEY_URL));
            mEmailEditText.setText(this.getIntent().getStringExtra(DatabaseWorker.KEY_EMAIL));
            mLoginEditText.setText(this.getIntent().getStringExtra(DatabaseWorker.KEY_LOGIN));
            mPassEditText.setText(this.getIntent().getStringExtra(DatabaseWorker.KEY_PASS));
            mExtraEditText.setText(this.getIntent().getStringExtra(DatabaseWorker.KEY_EXTRA));
        }

        setSupportActionBar(mAddToolbar);
        mAddToolbar.setTitle(mToolTitleString);
        mAddToolbar.setNavigationIcon(mBackDrawable);

        RxToolbar.navigationClicks(mAddToolbar).subscribe(aVoid -> onBackPressed());

        RxToolbar.itemClicks(mAddToolbar).subscribe(item -> {
            mModel.setId(id);
            mPresenter.delete(item, createObj());
        });

        RxView.clicks(mSaveButton).subscribe(aVoid -> {
                    if (getBundleNotNull()) {
                        mModel.setId(id);
                        createObj();
                        mPresenter.update(mModel);
                    } else {
                        mPresenter.add(createObj());
                    }
                }
        );
    }

    private boolean getBundleNotNull() {
        return (this.getIntent().getExtras() != null);
    }

    private PassModel createObj() {
        mModel.setUrl(mUrlEditText.getText().toString());
        mModel.setLogin(mLoginEditText.getText().toString());
        mModel.setPass(mPassEditText.getText().toString());
        mModel.setEmail(mEmailEditText.getText().toString());
        mModel.setExtra(mExtraEditText.getText().toString());

        return mModel;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        mModel = null;
        super.onDestroy();
    }

    @Override
    public void saved() {
        Toast.makeText(this, mSaveString, Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}
