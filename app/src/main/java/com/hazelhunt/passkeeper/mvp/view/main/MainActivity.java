package com.hazelhunt.passkeeper.mvp.view.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.mvp.presenter.main.MainPresenter;
import com.jakewharton.rxbinding.support.design.widget.RxNavigationView;
import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainView {

    @Bind(R.id.mainToolbar) Toolbar mMainToolbar;
    @Bind(R.id.mainDrawer) DrawerLayout mMainDrawerLayout;
    @Bind(R.id.mainNavigationView) NavigationView mMainNavigationView;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);
        mPresenter = new MainPresenter(this);

        setSupportActionBar(mMainToolbar);
        RxToolbar.itemClicks(mMainToolbar).subscribe(mPresenter::menuSelected);

        RxNavigationView.itemSelections(mMainNavigationView).subscribe(menuItem -> {
            mMainDrawerLayout.closeDrawers();
            mPresenter.menuSelected(menuItem);
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mMainDrawerLayout, mMainToolbar, R.string.openDrawer,
                R.string.closeDrawer);

        mMainDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        showList();
    }

    private void showList() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.MainContainer, new ListFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void switchToActivity(Class c) {
        startActivity(new Intent(this, c));
    }

    @Override
    public void exit() {
        finish();
    }
}
