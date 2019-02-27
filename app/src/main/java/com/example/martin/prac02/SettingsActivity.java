package com.example.martin.prac02;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;

public class SettingsActivity extends AppCompatPreferenceActivity {


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_settings);
            ListPreference language = (ListPreference) findPreference("language_field");

            if(language.getValue() == null){
                language.setValueIndex(1); //set to index of your deafult value
            }

            ListPreference httpMethod = (ListPreference) findPreference("request_field");

            if(httpMethod.getValue() == null){
                httpMethod.setValueIndex(1); //set to index of your deafult value
            }

            ListPreference databaseAccess = (ListPreference) findPreference("database_access");

            if(databaseAccess.getValue() == null){
                databaseAccess.setValueIndex(1); //set to index of your deafult value
            }
        }


}
