package nkteam.com.passkeeper.settings;

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

import nkteam.com.passkeeper.R;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "SettingsFragment";

    private AppCompatSpinner spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings_layout, container, false);

        spinner = (AppCompatSpinner) v.findViewById(R.id.themeSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.themes,
                R.layout.item_spinner
        );

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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
