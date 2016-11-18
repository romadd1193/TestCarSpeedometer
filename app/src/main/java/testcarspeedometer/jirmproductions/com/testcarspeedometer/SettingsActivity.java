package testcarspeedometer.jirmproductions.com.testcarspeedometer;

import android.preference.PreferenceActivity;
import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Josh on 11/17/2016.
 */

public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_xml);
    }
}