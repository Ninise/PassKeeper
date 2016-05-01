package com.hazelhunt.passkeeper.mvp.presenter.add;

import android.content.Context;

import com.hazelhunt.passkeeper.mvp.model.DatabaseWorker;
import com.hazelhunt.passkeeper.mvp.model.PassModel;
import com.hazelhunt.passkeeper.mvp.view.add.IAddView;

public class AddPresenter implements IAddPresenter {

    private IAddView mView;

    public AddPresenter(IAddView view) {
        this.mView = view;
    }

    @Override
    public void onAttach(Context context) {
        DatabaseWorker.register(context);
    }

    @Override
    public void add(String url, String login, String pass, String email, String extra) {
        PassModel model = new PassModel(url, login, pass, email, extra);
        DatabaseWorker.save(model).subscribe(model1 -> {
            mView.saved();
        });
    }
}
