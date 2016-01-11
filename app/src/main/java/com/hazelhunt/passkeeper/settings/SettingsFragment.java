package com.hazelhunt.passkeeper.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.utils.UserDataWorker;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "SettingsFragment";

    private AppCompatSpinner mSpinner;
    private EditText mPinEditText;

    private UserDataWorker mUserDataWorker;
    private SharedPreferences mUserData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserDataWorker = new UserDataWorker();

        mUserData = getActivity().getSharedPreferences(mUserDataWorker.APP_PREFERENCES, Context.MODE_PRIVATE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings_layout, container, false);

        mSpinner = (AppCompatSpinner) v.findViewById(R.id.themeSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.themes,
                R.layout.item_spinner
        );

        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        mPinEditText = (EditText) v.findViewById(R.id.changePinEditText);
        mPinEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = String.valueOf(parent.getItemAtPosition(position));
        switch (item) {
            case "Light":
                Log.d(TAG, String.valueOf(parent.getItemAtPosition(position)));
                break;
            case "Dark":
                Log.d(TAG, String.valueOf(parent.getItemAtPosition(position)));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
