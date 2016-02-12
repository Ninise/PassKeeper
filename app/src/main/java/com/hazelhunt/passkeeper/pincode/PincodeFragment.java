package com.hazelhunt.passkeeper.pincode;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.passlist.PassListActivity;
import com.hazelhunt.passkeeper.utils.UserDataWorker;
import com.hazelhunt.passkeeper.utils.Utils;

import java.security.NoSuchAlgorithmException;

public class PincodeFragment extends Fragment {

    private static final String TAG = "PincodeFragment";

    public static String PINCODE = "";
    public static String SECRET = "";

    public static String INPUT_PINCODE = "";

    private TextView mPinCodeTextView;

    private SharedPreferences mUserData;
    private UserDataWorker mUserDataWorker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mUserDataWorker = new UserDataWorker();

        mUserData = getActivity().getSharedPreferences(mUserDataWorker.APP_PREFERENCES, Context.MODE_PRIVATE);

        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pincode_layout, container, false);

        mPinCodeTextView = (TextView) v.findViewById(R.id.code_textView);

        // if the user first in the program
        boolean hasVisited = mUserData.getBoolean("hasVisited", false);

        if (!hasVisited) {
            showNewUserAlertDialog();
            SharedPreferences.Editor e = mUserData.edit();
            e.putBoolean("hasVisited", true);
            e.apply();
        }

        View.OnClickListener numberButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                String working = mPinCodeTextView.getText().toString();
                String text = textView.getText().toString();
                mPinCodeTextView.setText(working + text);
                INPUT_PINCODE = mPinCodeTextView.getText().toString();
            }
        };

        TableLayout tableLayout = (TableLayout) v.findViewById(R.id.fragment_pincode_layout);
        int number = 1;
        for (int i = 0; i < tableLayout.getChildCount() - 1; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                Button button = (Button) row.getChildAt(j);
                button.setText("" + number);
                button.setOnClickListener(numberButtonListener);
                number++;
            }
        }

        TableRow bottomRow = (TableRow) tableLayout.getChildAt(tableLayout.getChildCount() - 1);
        Button deleteBtn = (Button) bottomRow.getChildAt(0);
        deleteBtn.setText(R.string.del_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPinCodeTextView.length() != 0) {
                    mPinCodeTextView.setText(mPinCodeTextView.getText().subSequence(0, mPinCodeTextView.length() - 1));
                    INPUT_PINCODE = mPinCodeTextView.getText().toString();
                }
            }
        });

        Button clearBtn = (Button) bottomRow.getChildAt(2);
        clearBtn.setText(R.string.clr_btn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinCodeTextView.setText("");
            }
        });

        Button zeroBtn = (Button) bottomRow.getChildAt(1);
        zeroBtn.setText("0");
        zeroBtn.setOnClickListener(numberButtonListener);

        Button loginBtn = (Button) v.findViewById(R.id.login_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mUserDataWorker.login(mUserData, Utils.stringToMD5(INPUT_PINCODE))) {
                        viewPassList();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                R.string.login_failed,
                                Toast.LENGTH_SHORT)
                                .show();
                        mPinCodeTextView.setText("");
                        }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pincode_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_forgot_pass:
                showForgotPassAlertDialog();
                break;
            case R.id.menu_item_help:
                showHelpAlertDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showNewUserAlertDialog() {
        LayoutInflater factory = LayoutInflater.from(getActivity());

        final View textEntryView = factory.inflate(R.layout.alert_dialog_pin_layout, null);

        final EditText inputPin = (EditText) textEntryView.findViewById(R.id.pincodeEditText);
        final EditText inputSecret = (EditText) textEntryView.findViewById(R.id.secretEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MaterialDialogSheet);

        builder.setCancelable(false)
               .setView(textEntryView)
               .setPositiveButton(R.string.ok_btn, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       PINCODE = String.valueOf(inputPin.getText());
                       SECRET = String.valueOf(inputSecret.getText());
                       if (!PINCODE.isEmpty() && !SECRET.isEmpty()) {
                           mUserDataWorker.createUser(mUserData, PINCODE, SECRET);
                       } else {
                           Toast.makeText(getActivity(), R.string.pin_and_secret_is_empty, Toast.LENGTH_LONG).show();
                           showNewUserAlertDialog();
                       }
                   }
               });

        final AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBtn = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveBtn.setTextSize(30);
            }
        });
        alert.show();
    }

    private void showForgotPassAlertDialog() {
        LayoutInflater facoty = LayoutInflater.from(getActivity());

        final View textEntryView = facoty.inflate(R.layout.alert_dialog_forgotpass_layout, null);

        final EditText inputSecret = (EditText) textEntryView.findViewById(R.id.forgotPassEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MaterialDialogSheet);

        builder.setCancelable(false)
                .setView(textEntryView)
                .setPositiveButton(R.string.ok_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if (mUserDataWorker.isTrueSecret(mUserData, inputSecret.getText().toString())) {
                                viewPassList();
                                Toast.makeText(getActivity(), R.string.change_your_pin, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), R.string.your_secret_incorrect, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        final AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBtn = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveBtn.setTextSize(30);
            }
        });

        alert.show();
    }

    private void showHelpAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MaterialDialogSheet);

        builder.setCancelable(false)
                .setTitle(R.string.menu_help)
                .setMessage(R.string.cant_help)
                .setPositiveButton(R.string.ok_btn, null);

        final AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBtn = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveBtn.setTextSize(30);
            }
        });

        alert.show();
    }

    private void viewPassList() {
        Intent intent = new Intent(getActivity(), PassListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().finish();
    }
}
