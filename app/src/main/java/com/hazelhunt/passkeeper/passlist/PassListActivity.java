package com.hazelhunt.passkeeper.passlist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hazelhunt.passkeeper.R;
import com.hazelhunt.passkeeper.about.AboutActivity;
import com.hazelhunt.passkeeper.database.DatabaseHandler;
import com.hazelhunt.passkeeper.database.PKDataModel;
import com.hazelhunt.passkeeper.settings.SettingsActivity;
import com.hazelhunt.passkeeper.utils.HttpRequest;
import com.hazelhunt.passkeeper.utils.JSONParser;

import org.json.JSONArray;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class PassListActivity extends AppCompatActivity {

    private static final String TAG = "PassListActivity";

    private boolean backPressedToExitOnce = false;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    public String EMAIL;
    public String PASSWORD;

    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_list_layout);

        db = new DatabaseHandler(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                return menuSelect(menuItem);
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,toolbar, R.string.openDrawer,
                R.string.closeDrawer);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        menuSelect(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (backPressedToExitOnce) {
            closeApp();
        }

        if (count == 0) {
            viewListFragment();
            backPressedToExitOnce = true;
        } else {
            getFragmentManager().popBackStack();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backPressedToExitOnce = false;
            }
        }, 2000);

    }

    private void viewListFragment() {
        ContentFragment fragment = new ContentFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameSettings, fragment);
        fragmentTransaction.commit();
    }

    private void viewPassDataFragment() {
        PassDataFragment fragment = new PassDataFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        fragmentTransaction.replace(R.id.frameSettings, fragment);
        fragmentTransaction.commit();
    }

    private void switchToSettings() {
        Intent intent = new Intent(PassListActivity.this, SettingsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void switchToAbout() {
        Intent intent = new Intent(PassListActivity.this, AboutActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void synchroWithCloud() {
        showAlertLoginToCloud();
    }

    private void showAlertLoginToCloud() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View passDataAlertView = factory.inflate(R.layout.alert_dialog_logintocloud_layout, null);

        final EditText emailEdit = (EditText) passDataAlertView.findViewById(R.id.cloudEmailEditText);
        final EditText passEdit = (EditText) passDataAlertView.findViewById(R.id.cloudPasswordEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MaterialDialogSheet)
                .setView(passDataAlertView)
                .setCancelable(true)
                .setPositiveButton(R.string.ok_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            EMAIL = emailEdit.getText().toString();
                            PASSWORD = stringToMD5(passEdit.getText().toString());

                            new PKItemsTask().execute();

                        } catch (NoSuchAlgorithmException e) {
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

    public static boolean hasConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private String stringToMD5(String str) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(str.getBytes(), 0, str.length());

        return new BigInteger(1, m.digest()).toString(16);
    }

    private boolean menuSelect(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.new_acc:
                viewPassDataFragment();
                return true;
            case R.id.settings:
                switchToSettings();
                return true;
            case R.id.synchronize:
                if (hasConnection(getApplicationContext())) {
                    synchroWithCloud();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.enable_internet, Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.about:
                switchToAbout();
                return true;
            case R.id.logout:
                closeApp();
                return true;
            default:
                Toast.makeText(this, "Somethings Wrong", Toast.LENGTH_SHORT).show();
                return true;
        }
    }

    private void closeApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewListFragment();
    }

    private class PKItemsTask extends AsyncTask<Void,Void, Void> {

        public String URL = "http://pk-hazelhunt.rhcloud.com/sync?user=" +
                EMAIL + "&pass=" + PASSWORD;


        @Override
        protected Void doInBackground(Void... params) {

            HttpRequest request = new HttpRequest();

            List<PKDataModel> mobileList = db.getAllDataPasses();
            List<PKDataModel> cloudList = new ArrayList<>();
            JSONArray postArray = new JSONArray();

            boolean flagGET = false;
            boolean flagPOST = false;

            try {

                String resultJson = request.getUrl(URL);

                if (request.isOKResponseCode()) {

                    JSONArray array = new JSONArray(resultJson);

                    for (int i = 0; i < array.length(); i++) {
                        cloudList.add(JSONParser.parseToPKDataModel(array, i));
                    }

                    if (mobileList.isEmpty() && !cloudList.isEmpty()) {
                        db.addDataPass(cloudList.get(0));
                        mobileList.add(cloudList.get(0));
                    }

                            /** GET */
                            for (int i = 0; i < cloudList.size(); i++) {

                                for (int j = 0; j < mobileList.size(); j++) {

                                    if (mobileList.get(j).getPass().equals(cloudList.get(i).getPass())) {
                                        flagGET = false;
                                        break;
                                    } else {
                                        flagGET = true;
                                    }

                                }

                                if (flagGET) {
                                    db.addDataPass(cloudList.get(i));
                                }
                            }

                            /** POST */
                            for (int j = 0; j < mobileList.size(); j++) {

                                for (int k = 0; k < cloudList.size(); k++) {

                                    if (cloudList.get(k).getPass().equals(mobileList.get(j).getPass())) {
                                        flagPOST = false;
                                        break;
                                    } else {
                                        flagPOST = true;
                                    }
                                }

                                if (flagPOST) {
                                    postArray.put(JSONParser.parseToJSON(mobileList.get(j)));
                                    cloudList.add(mobileList.get(j));
                                }

                            }
                        }

                    /** SEND POST */
                    if (postArray.length() != 0) {
                        request.postData(URL, postArray);
                    }

            } catch (Exception ioe) {
                Log.e(TAG, "PK-hazelhunt to fetch URL: ", ioe);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            HttpRequest request = new HttpRequest();

            if (request.isOKResponseCode()) {
                Toast.makeText(getApplicationContext(), R.string.synchronized_text, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.user_not_found, Toast.LENGTH_SHORT).show();
            }

            viewListFragment();
        }
    }

}