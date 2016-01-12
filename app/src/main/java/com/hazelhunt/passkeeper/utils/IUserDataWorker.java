package com.hazelhunt.passkeeper.utils;

import android.content.SharedPreferences;

public interface IUserDataWorker {

    boolean login(SharedPreferences userData, String inputPincode);
    void createUser(SharedPreferences userData, String pincode, String secret);
    String loadUserData(SharedPreferences userData, String dataTag);
    void changePin(SharedPreferences userData, String newPin);
    void changeSecret(SharedPreferences userData, String newSecret);
    boolean isTrueSecret(SharedPreferences userData, String secret);
}
