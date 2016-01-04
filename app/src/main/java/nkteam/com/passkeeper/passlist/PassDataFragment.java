package nkteam.com.passkeeper.passlist;

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

import nkteam.com.passkeeper.R;
import nkteam.com.passkeeper.database.DatabaseHandler;
import nkteam.com.passkeeper.database.PKDataModel;

public class PassDataFragment extends Fragment {

    private static final String TAG = "PassDataFragment";

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_acc_layout, container, false);

        final EditText urlEditText = (EditText) v.findViewById(R.id.urlEditText);
        final EditText emailEditText = (EditText) v.findViewById(R.id.emailEditText);
        final EditText loginEditText = (EditText) v.findViewById(R.id.loginEditText);
        final EditText passEditText = (EditText) v.findViewById(R.id.passEditText);
        final EditText extraEditText = (EditText) v.findViewById(R.id.extraEditText);

        Button saveBtn = (Button) v.findViewById(R.id.saveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (urlEditText.getText().toString().equals("") || loginEditText.getText().toString().equals("") ||
                        passEditText.getText().toString().equals("") || emailEditText.getText().toString().equals("")) {

                    showAlertDialog();

                } else {
                    DatabaseHandler db = new DatabaseHandler(getActivity());
                    db.addDataPass(new PKDataModel(
                            urlEditText.getText().toString().trim(),
                            loginEditText.getText().toString().trim(),
                            passEditText.getText().toString().trim(),
                            emailEditText.getText().toString().trim(),
                            extraEditText.getText().toString().trim()));
                    backToList();
                }
            }
        });

        return v;
    }

    private void backToList() {
        ContentFragment fragment = new ContentFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().
                getSupportFragmentManager().
                beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    private void showAlertDialog() {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View passDataAlertView = factory.inflate(R.layout.alert_dialog_passdata, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MaterialDialogSheet)
                .setView(passDataAlertView)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
}
