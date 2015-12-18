package nkteam.com.passkeeper;

import android.app.Fragment;

public class PincodeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PincodeFragment();
    }
}
