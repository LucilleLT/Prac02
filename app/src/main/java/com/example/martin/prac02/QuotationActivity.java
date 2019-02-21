package com.example.martin.prac02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuotationActivity extends AppCompatActivity {

    TextView quotationText;
    TextView authorText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        ImageButton refreshQuotation = findViewById(R.id.refreshQuotationButton);
        quotationText = findViewById(R.id.quotationText);
        authorText = findViewById(R.id.authorText);
        quotationText.setText(String.format(getResources().getString(R.string.info_quotations), getResources().getString(R.string.username)));

        refreshQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quotationText.setText(getResources().getString(R.string.sample_q));
                authorText.setText(getResources().getString(R.string.sample_a));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quotation_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                return true;
            case R.id.menu_refresh:
                quotationText.setText(getResources().getString(R.string.sample_q));
                authorText.setText(getResources().getString(R.string.sample_a));
                break;

        }
        return true;
    }

}
