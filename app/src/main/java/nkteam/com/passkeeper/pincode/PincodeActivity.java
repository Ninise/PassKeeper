package nkteam.com.passkeeper.pincode;

import android.app.Fragment;

public class PincodeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PincodeFragment();
    }
}
