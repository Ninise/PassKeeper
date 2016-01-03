package nkteam.com.passkeeper.passlist;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.List;

import nkteam.com.passkeeper.database.DatabaseHandler;
import nkteam.com.passkeeper.database.PKDataModel;

public class ContentFragment extends ListFragment {

    private static final String TAG = "ContentFragment";

    List<PKDataModel> passList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseHandler db = new DatabaseHandler(getActivity());

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
    }
}
