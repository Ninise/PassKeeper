package com.hazelhunt.passkeeper.passlist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.database.DatabaseHandler;
import com.hazelhunt.passkeeper.database.PKDataModel;

public class PassDataFragment extends Fragment {

    private static final String TAG = "PassDataFragment";

    private EditText urlEditText;
    private EditText emailEditText;
    private EditText loginEditText;
    private EditText passEditText;
    private EditText extraEditText;

    private static int ID;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_new_acc_layout, container, false);

        urlEditText = (EditText) v.findViewById(R.id.urlEditText);
        emailEditText = (EditText) v.findViewById(R.id.emailEditText);
        loginEditText = (EditText) v.findViewById(R.id.loginEditText);
        passEditText = (EditText) v.findViewById(R.id.passEditText);
        extraEditText = (EditText) v.findViewById(R.id.extraEditText);

        if (isDataExists()) {
            getData();
        }

        Button saveBtn = (Button) v.findViewById(R.id.saveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urlEditText.getText().toString().equals("") || loginEditText.getText().toString().equals("") ||
                        passEditText.getText().toString().equals("") || emailEditText.getText().toString().equals("")) {

                    showAlertDialog();
                }
                    DatabaseHandler db = new DatabaseHandler(getActivity());

                if (!isDataExists()) {
                    db.addDataPass(setData());
                } else {
                    db.updateDataPass(setData());
                }
                    backToList();
            }

        });

        return v;
    }

    private void backToList() {
        ContentFragment fragment = new ContentFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().
                getSupportFragmentManager().
                beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        fragmentTransaction.replace(R.id.frameSettings, fragment);
        fragmentTransaction.commit();
    }

    private void showAlertDialog() {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View passDataAlertView = factory.inflate(R.layout.alert_dialog_passdata_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MaterialDialogSheet)
                .setView(passDataAlertView)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
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

    private void getData() {
        ID = getArguments().getInt(ContentFragment.EXTRA_ID);
        urlEditText.setText(getArguments().getString(ContentFragment.EXTRA_URL));
        loginEditText.setText(getArguments().getString(ContentFragment.EXTRA_LOGIN));
        passEditText.setText(getArguments().getString(ContentFragment.EXTRA_PASS));
        emailEditText.setText(getArguments().getString(ContentFragment.EXTRA_EMAIL));
        extraEditText.setText(getArguments().getString(ContentFragment.EXTRA_EXTRA));
    }

    private PKDataModel setData() {
          return new PKDataModel(
                                 ID,
                                 urlEditText.getText().toString().trim(),
                                 loginEditText.getText().toString().trim(),
                                 passEditText.getText().toString().trim(),
                                 emailEditText.getText().toString().trim(),
                                 extraEditText.getText().toString().trim()
          );
    }

    private boolean isDataExists() {
        return getArguments() != null;
    }
}
