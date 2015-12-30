package nkteam.com.passkeeper.passlist;

import android.app.Fragment;

import nkteam.com.passkeeper.SingleFragmentActivity;

public class PassListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new PassListFragment();
    }
}
