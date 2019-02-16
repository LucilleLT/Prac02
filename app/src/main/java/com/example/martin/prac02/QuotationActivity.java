package com.example.martin.prac02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class QuotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        ImageButton refreshQuotation = findViewById(R.id.refreshQuotationButton);
        final TextView quotationText = findViewById(R.id.quotationText);
        final TextView authorText = findViewById(R.id.authorText);
        quotationText.setText(String.format(getResources().getString(R.string.info_quotations), getResources().getString(R.string.username)));

        refreshQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quotationText.setText(getResources().getString(R.string.sample_q));
                authorText.setText(getResources().getString(R.string.sample_a));
            }
        });
    }
}
