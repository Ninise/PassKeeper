package nkteam.com.passkeeper.passlist;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import nkteam.com.passkeeper.database.DatabaseHandler;
import nkteam.com.passkeeper.database.PKDataModel;

public class PassListFragment extends Fragment {

    public static final String TAG = "PassListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHandler db = new DatabaseHandler(getActivity());

        Log.d(TAG, "INSERTING");
        db.addDataPass(new PKDataModel("https://google.com" ,"provekra", "qwerty", "niki@ex.ua", "norm"));

        Log.d(TAG, "READING");
        List<PKDataModel> passList = db.getAllDataPasses();
        for (PKDataModel model : passList) {
            String log = "ID: " + model.getId() +
                    "URL: " + model.getUrl() +
                    " LOGIN: " + model.getLogin() +
                    " PASS: " + model.getPass() +
                    " EMAIL: " + model.getEmail() +
                    " EXTRA: " + model.getExtra();
            Log.d(TAG, log);
        }

        db.deleteAllDataPasses();
    }
}
