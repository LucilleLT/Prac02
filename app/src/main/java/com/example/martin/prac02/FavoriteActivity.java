package com.example.martin.prac02;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


    }

    public void author_quote(android.view.View view){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search="+getString(R.string.authorName)));

        if(intent.resolveActivity(getPackageManager())!=null){
             startActivity(intent);
        }
    }

}
