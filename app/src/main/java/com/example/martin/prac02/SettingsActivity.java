package com.example.martin.prac02;

import android.content.SharedPreferences;
import android.os.Bundle;

public class SettingsActivity extends AppCompatPreferenceActivity {


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_settings);
        }


}
