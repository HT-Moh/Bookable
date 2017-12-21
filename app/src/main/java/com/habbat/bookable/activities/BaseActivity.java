package com.habbat.bookable.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.habbat.bookable.R;

/**
 * Created by hackolos on 21.12.17.
 */

public class BaseActivity extends Activity {

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.from_left_out, R.anim.from_left_in);
    }
    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Icepick.saveInstanceState(this, outState);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_right_out, R.anim.from_right_in);
    }
}

