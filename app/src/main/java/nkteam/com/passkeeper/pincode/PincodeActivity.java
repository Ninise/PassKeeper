package nkteam.com.passkeeper.pincode;

import android.app.Fragment;

import nkteam.com.passkeeper.SingleFragmentActivity;

public class PincodeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PincodeFragment();
    }
}
