package nkteam.com.passkeeper.passlist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import nkteam.com.passkeeper.R;
import nkteam.com.passkeeper.database.DatabaseHandler;
import nkteam.com.passkeeper.database.PKDataModel;

public class ContentFragment extends Fragment {

    private static final String TAG = "ContentFragment";

    List<PKDataModel> passList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseHandler db = new DatabaseHandler(getActivity());

        passList = db.getAllDataPasses();

        final PassAdapter adapter = new PassAdapter(passList, getActivity());

        RecyclerView rv = (RecyclerView) getActivity().findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        rv.setItemAnimator(itemAnimator);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
