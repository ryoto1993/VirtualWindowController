package jp.ac.doshisha.projectn.virtualwindowcontroller;

import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SettingActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MainPreferenceFragment())
                .commit();
    }

    /********** PreferenceFragment **********/

    public static class MainPreferenceFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {

        public MainPreferenceFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

        @Override
        public void onResume() {
            super.onResume();
            SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
            sp.registerOnSharedPreferenceChangeListener(this);
            setSummaries(sp);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

        private void setSummaries(final SharedPreferences sp) {
            final String ip = sp.getString("pref_ip", "");
            findPreference("pref_ip").setSummary(ip);

            final String port = sp.getString("pref_port", "");
            findPreference("pref_port").setSummary(port);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            setSummaries(sharedPreferences);
            SocketConnection.setIpAddress(sharedPreferences.getString("pref_ip", ""));
            SocketConnection.setPORT(sharedPreferences.getString("pref_port", ""));
        }
    }
}
