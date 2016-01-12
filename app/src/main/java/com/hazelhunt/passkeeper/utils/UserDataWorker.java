package com.hazelhunt.passkeeper.utils;

import android.content.SharedPreferences;

public class UserDataWorker implements IUserDataWorker {

    private static final String TAG = "UserDataWorker";

    private final String APP_PREFERENCES_PINCODE = "pincode";
    private final String APP_PREFERENCES_SECRET= "secret";
    public final String APP_PREFERENCES = "user_data";

    public  boolean login(SharedPreferences userData, String inputPincode) {
        return inputPincode.equals(loadUserData(userData, APP_PREFERENCES_PINCODE));
    }

    public void createUser(SharedPreferences userData, String pincode, String secret) {
        SharedPreferences.Editor editor = userData.edit();
        editor.putString(APP_PREFERENCES_PINCODE, pincode);
        editor.putString(APP_PREFERENCES_SECRET, secret);
        editor.apply();
    }

     public String loadUserData(SharedPreferences userData, String dataTag) {
        return userData.contains(dataTag) ?
                userData.getString(dataTag, "")
                : "APP PREFERENCES NOT FOUND";
    }

    public void changePin(SharedPreferences userData, String newPin) {
            SharedPreferences.Editor editor = userData.edit();
            editor.putString(APP_PREFERENCES_PINCODE, newPin);
            editor.apply();
    }

    public void changeSecret(SharedPreferences userData, String newSecret) {
            SharedPreferences.Editor editor = userData.edit();
            editor.putString(APP_PREFERENCES_SECRET, newSecret);
            editor.apply();
    }

    public boolean isTrueSecret(SharedPreferences userData, String secret) {
        return loadUserData(userData, APP_PREFERENCES_SECRET).equals(secret);
    }
}
