package com.hazelhunt.passkeeper.utils;

import android.content.SharedPreferences;

public interface IUserDataWorker {

    boolean login(SharedPreferences userData, String inputPincode);
    void createUser(SharedPreferences userData, String pincode, String secret);
    String loadUserData(SharedPreferences userData, String dataTag);
    boolean changePin(SharedPreferences userData, String newPin, String secret);
    boolean changeSecret(SharedPreferences userData, String oldSecret, String newSecret);
    boolean isTrueSecret(SharedPreferences userData, String secret);
}
