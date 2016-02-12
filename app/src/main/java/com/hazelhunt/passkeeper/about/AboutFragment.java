package com.hazelhunt.passkeeper.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hazelhunt.passkeeper.R;

public class AboutFragment extends Fragment {

    public static final String TAG = "AboutFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about_layout, container, false);

        final Button okButton = (Button) v.findViewById(R.id.aboutOkButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        Log.d(TAG, "ON CREATE");

        return v;
    }
}
