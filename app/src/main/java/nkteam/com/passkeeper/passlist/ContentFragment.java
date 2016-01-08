package nkteam.com.passkeeper.passlist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nkteam.com.passkeeper.R;
import nkteam.com.passkeeper.database.DatabaseHandler;
import nkteam.com.passkeeper.database.PKDataModel;

public class ContentFragment extends Fragment {

    private static final String TAG = "ContentFragment";

    public static final String EXTRA_URL = "URL";
    public static final String EXTRA_LOGIN = "LOGIN";
    public static final String EXTRA_PASS = "PASS";
    public static final String EXTRA_EMAIL = "EMAIL";
    public static final String EXTRA_EXTRA = "EXTRA";
    public static final String EXTRA_ID = "ID";

    List<PKDataModel> passList;
    RecyclerView mRecyclerView;
    PassAdapter adapter;
    DatabaseHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseHandler(getActivity());
        passList = db.getAllDataPasses();
        adapter = new PassAdapter(passList, getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content_layout, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);

        ItemTouchHelper.Callback callback = new MovieTouchHelper(adapter, getActivity());
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        sendData(position);
                    }
                }));

        return v;
    }

    private void sendData(int position) {
        PassDataFragment fragment = new PassDataFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ID, passList.get(position).getId());
        bundle.putString(EXTRA_URL, passList.get(position).getUrl());
        bundle.putString(EXTRA_LOGIN, passList.get(position).getLogin());
        bundle.putString(EXTRA_PASS, passList.get(position).getPass());
        bundle.putString(EXTRA_EMAIL, passList.get(position).getEmail());
        bundle.putString(EXTRA_EXTRA, passList.get(position).getExtra());

        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();

    }
}
