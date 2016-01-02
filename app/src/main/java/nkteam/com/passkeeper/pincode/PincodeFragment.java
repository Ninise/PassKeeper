package nkteam.com.passkeeper.pincode;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import nkteam.com.passkeeper.R;
import nkteam.com.passkeeper.passlist.PassListActivity;

public class PincodeFragment extends Fragment {

    private static final String TAG = "PincodeFragment";

    public static String PINCODE = "";
    public static String SECRET = "";
    private static final String APP_PREFERENCES = "user_data";

    public static String INPUT_PINCODE = "";

    private TextView mPinCodeTextView;

    private SharedPreferences mUserData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mUserData = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pincode_layout, container, false);

        mPinCodeTextView = (TextView) v.findViewById(R.id.code_textView);
        mPinCodeTextView.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mPinCodeTextView.setTransformationMethod(PasswordTransformationMethod.getInstance());

        // if the user first in the program
        boolean hasVisited = mUserData.getBoolean("hasVisited", false);

        if (!hasVisited) {
            showDialog();
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

                Log.d(TAG, mPinCodeTextView.getText().toString());
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
                if (PincodeUserWorker.login(mUserData, INPUT_PINCODE)) {
                    Intent intent = new Intent(getActivity(), PassListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Login failed",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pincode_menu, menu);
    }

    public void showDialog() {
        LayoutInflater factory = LayoutInflater.from(getActivity());

        final View textEntryView = factory.inflate(R.layout.alert_dialog_pin_layout, null);

        final EditText inputPin = (EditText) textEntryView.findViewById(R.id.pincodeEditText);
        final EditText inputSecret = (EditText) textEntryView.findViewById(R.id.secretEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("CREATE USER")
                .setCancelable(true)
                .setView(textEntryView)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PINCODE = String.valueOf(inputPin.getText());
                        SECRET = String.valueOf(inputSecret.getText());
                        PincodeUserWorker.createUser(mUserData, PINCODE, SECRET);
                        Log.d(TAG, PINCODE + " " + SECRET);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().finish();
    }
}
