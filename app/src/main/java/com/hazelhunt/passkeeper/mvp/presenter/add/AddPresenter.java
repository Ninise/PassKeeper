package com.hazelhunt.passkeeper.mvp.presenter.add;

import android.content.Context;
import android.view.MenuItem;

import com.hazelhunt.passkeeper.R;
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
    public void add(PassModel model) {
        DatabaseWorker.save(model).subscribe(model1 -> {
            mView.saved();
        });
    }

    @Override
    public void update(PassModel model) {
        DatabaseWorker.save(model).subscribe(model1 -> mView.saved());
    }

    @Override
    public boolean delete(MenuItem item, PassModel model) {
        switch (item.getItemId()) {
            case R.id.menuDelete:
                DatabaseWorker.remove(model).subscribe(model1 -> mView.saved());
                return true;
        }
        return false;
    }
}
