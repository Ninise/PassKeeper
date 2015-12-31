package nkteam.com.passkeeper.passlist;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;


import java.util.List;

import nkteam.com.passkeeper.database.DatabaseHandler;
import nkteam.com.passkeeper.database.PKDataModel;

public class PassListFragment extends ListFragment {

    public static final String TAG = "PassListFragment";
    List<PKDataModel> passList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHandler db = new DatabaseHandler(getActivity());

        Log.d(TAG, "INSERTING");
        db.addDataPass(new PKDataModel("https://ex.ua", "provekra", "qwerty", "niki@ex.ua", "norm"));
        db.addDataPass(new PKDataModel("https://xakep.ru", "proverka2", "ytrewq", "nk@in.ua", "guten"));

        Log.d(TAG, "READING");
        passList = db.getAllDataPasses();

        ArrayAdapter<PKDataModel> adapter = new PassAdapter(getActivity(), passList);

        setListAdapter(adapter);

        for (PKDataModel model : passList) {
            String log = "ID: " + model.getId() +
                         " URL: " + model.getUrl() +
                         " LOGIN: " + model.getLogin() +
                         " PASS: " + model.getPass() +
                         " EMAIL: " + model.getEmail() +
                         " EXTRA: " + model.getExtra();
            Log.d(TAG, log);
        }

        Log.d(TAG, String.valueOf(db.getDataPass(1)));

        db.deleteAllDataPasses();
    }
}
