package com.hazelhunt.passkeeper.mvp.utils.preferences;


import android.content.Context;
import android.content.SharedPreferences;

import com.hazelhunt.passkeeper.mvp.utils.Constants;

public class UserAccountPreferences {

    private static UserAccountPreferences mInstance = null;

    private SharedPreferences mPreferences;

    private UserAccountPreferences(Context context) {
        mPreferences = context.getSharedPreferences(
                Constants.USER_ACCOUNT_PREFERENCES,
                Context.MODE_PRIVATE);
    }

    public static UserAccountPreferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UserAccountPreferences(context);
        }

        return mInstance;
    }

    public boolean register(boolean flag) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(Constants.REGISTERED, flag);
        return editor.commit();
    }

    public boolean isRegistered() {
        return mPreferences.contains(Constants.REGISTERED) &&
                mPreferences.getBoolean(Constants.REGISTERED, true);
    }

    public boolean savePin(String pin) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Constants.USER_PINCODE, pin);
        return editor.commit();
    }

    public String getPin() {
        return mPreferences.contains(Constants.USER_PINCODE) ?
                mPreferences.getString(Constants.USER_PINCODE, "") : "";
    }

}
