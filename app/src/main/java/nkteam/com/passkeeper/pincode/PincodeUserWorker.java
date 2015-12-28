package nkteam.com.passkeeper.pincode;

import android.content.SharedPreferences;
import android.util.Log;

public class PincodeUserWorker {

    private static final String TAG = "PincodeUserWorker";

    private static final String APP_PREFERENCES_PINCODE = "pincode";
    private static final String APP_PREFERENCES_SECRET= "secret";


    public static boolean login(SharedPreferences userData, String inputPincode) {
        if (inputPincode.equals(loadUser(userData))) {
            Log.d(TAG, "LOGIN SUCCESSFUL");
        } else {
            Log.d(TAG, "LOGIN FAILED");
        }
        return inputPincode.equals(loadUser(userData));
    }

    public static void createUser(SharedPreferences userData, String pincode, String secret) {
        saveUser(userData, pincode, secret);
    }

    private static void saveUser(SharedPreferences userData, String pincode, String secret) {
        SharedPreferences.Editor editor = userData.edit();
        editor.putString(APP_PREFERENCES_PINCODE, pincode);
        editor.putString(APP_PREFERENCES_SECRET, secret);
        editor.apply();
        Log.d(TAG, "USER DATA SAVED");
    }

    private static String loadUser(SharedPreferences userData) {
        Log.d(TAG, "USER DATA LOADED");
        return userData.contains(APP_PREFERENCES_PINCODE) ?
                userData.getString(APP_PREFERENCES_PINCODE, "")
                : "APP PREFERENCES NOT FOUND";
    }
}
