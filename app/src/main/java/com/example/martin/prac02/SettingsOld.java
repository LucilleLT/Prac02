package com.example.martin.prac02;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Set;

public class SettingsOld extends AppCompatActivity {

    TextView nameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_old);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        nameField = findViewById(R.id.edit_name);
        nameField.setText(prefs.getString("name", ""));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        if(nameField.getText().equals("")){
            editor.remove("name");
        }else{
            editor.putString("name", "" + nameField.getText());
        }
        editor.apply();
    }
}
