package com.example.martin.prac02;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.martin.prac02.databases.QuotationRoom;

            public class MainActivity extends AppCompatActivity {

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main);

                    if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("first_run",true)) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                QuotationRoom.getInstance(getApplicationContext()).quotationDao().getQuotations();
                            }
                        }).start();
                        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("first_run",false).apply();

                    }
    }


    /*public void callback(android.view.View view){
     //   Intent intent = new Intent(context, ShowDefinition.class)
        // context.startActivity(intent);
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);

    }

    public void callbackGetQ(android.view.View view){
        Intent intent = new Intent(this, QuotationActivity.class);
        startActivity(intent);

    }

    public void callbackFav(android.view.View view){
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);

    }
    public void callbackSettings(android.view.View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

    }

    public void callbackAbout(android.view.View view){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);

    }*/

    public void callbackQuotation(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.GetQButton:
                intent = new Intent(this, QuotationActivity.class);
                startActivity(intent);
                break;
            case R.id.FavButton:
                intent = new Intent(this, FavoriteActivity.class);
                startActivity(intent);
                break;
            case R.id.SettingsButton:
                //intent = new Intent(this, SettingsActivity.class);
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.AboutButton:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
    }
}
