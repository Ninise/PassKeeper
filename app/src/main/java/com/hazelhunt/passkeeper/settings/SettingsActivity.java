package com.hazelhunt.passkeeper.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.utils.UserDataWorker;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";

    public static String SECRET = "";

    private Toolbar toolbar;

    private UserDataWorker mUserDataWorker;
    private SharedPreferences mUserData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        mUserDataWorker = new UserDataWorker();

        mUserData = getSharedPreferences(mUserDataWorker.APP_PREFERENCES, Context.MODE_PRIVATE);

        showAlertDialog();
    }

    private void viewSettingsFragment() {
        SettingsFragment fragment = new SettingsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameSettings, fragment);
        fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        fragmentTransaction.commit();
    }

    private void showAlertDialog() {
        LayoutInflater factory = LayoutInflater.from(this);

        final View textEntryView = factory.inflate(R.layout.alert_dialog_settings_layout, null);

        final EditText inputSecret = (EditText) textEntryView.findViewById(R.id.secretSettingsEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MaterialDialogSheet);

        builder.setCancelable(false)
                .setView(textEntryView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SECRET = String.valueOf(inputSecret.getText());
                        if (checkSecret(SECRET)) {
                            viewSettingsFragment();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    R.string.alertFailSecret,
                                    Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });

        final AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBtn = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeBtn = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                positiveBtn.setTextSize(30);
                negativeBtn.setTextSize(30);
            }
        });
        alert.show();
    }

    private boolean checkSecret(String SECRET) {
         return mUserDataWorker.isTrueSecret(mUserData, SECRET);
    }

}
