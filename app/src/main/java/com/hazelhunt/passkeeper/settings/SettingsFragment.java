package com.hazelhunt.passkeeper.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.database.DatabaseHandler;
import com.hazelhunt.passkeeper.utils.UserDataWorker;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    private AppCompatSpinner mSpinner;
    private EditText mPinEditText;
    private EditText mSecretEditText;
    private CheckedTextView mDeleteADCheckedTextView;
    private Button mSaveButton;

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

        mPinEditText = (EditText) v.findViewById(R.id.changePinEditText);
        mSecretEditText = (EditText) v.findViewById(R.id.changeSecretEditText);

        mDeleteADCheckedTextView = (CheckedTextView) v.findViewById(R.id.deleteAllDataCheckedTextView);
        mDeleteADCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
            }
        });

        mSaveButton = (Button) v.findViewById(R.id.settingsButton);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNotEmpty(mPinEditText.getText().toString())) {
                    mUserDataWorker.changePin(mUserData, mPinEditText.getText().toString().trim());
                }

                if (isNotEmpty(mSecretEditText.getText().toString())) {
                    mUserDataWorker.changeSecret(mUserData, mSecretEditText.getText().toString().trim());
                }

                if (mDeleteADCheckedTextView.isChecked()) {
                    DatabaseHandler db = new DatabaseHandler(getActivity());
                    db.deleteAllDataPasses();
                }
                Toast.makeText(getActivity(), R.string.toast_settings, Toast.LENGTH_SHORT).show();

                getActivity().onBackPressed();

            }
        });

        return v;
    }

    private boolean isNotEmpty(String value) {
        return !value.isEmpty();
    }

}
