package com.example.martin.prac02;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.prac02.databases.QuotationDatabase;
import com.example.martin.prac02.databases.QuotationRoom;

public class QuotationActivity extends AppCompatActivity {

    int quotationsCount = 0;
    TextView quotationText;
    TextView authorText;
    Menu quotationMenu;
    boolean addState;
    String using_room;
    QuotationDatabase quotationDatabase = QuotationDatabase.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_quotation);
        using_room = prefs.getString("database_access","room");
        quotationText = findViewById(R.id.quotationText);
        authorText = findViewById(R.id.authorText);
        quotationText.setText(String.format(getResources().getString(R.string.info_quotations), prefs.getString("name","")));
        if(savedInstanceState != null){
            quotationsCount = savedInstanceState.getInt("quotationsCount");
            quotationText.setText(savedInstanceState.getString("quotation"));
            authorText.setText(savedInstanceState.getString("author"));
            addState = savedInstanceState.getBoolean("addState");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("quotation", quotationText.getText().toString());
        bundle.putString("author", authorText.getText().toString());
        bundle.putInt("quotationsCount", quotationsCount);
        bundle.putBoolean("addState", quotationMenu.findItem(R.id.menu_add).isVisible());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        quotationMenu = menu;
        getMenuInflater().inflate(R.menu.quotation_activity_menu, menu);
        menu.findItem(R.id.menu_add).setVisible(addState);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add:
                quotationMenu.findItem(R.id.menu_add).setVisible(false);
                Quotation quotationIns = new Quotation(quotationText.getText().toString(), authorText.getText().toString());
                if (using_room.equals("Room")) {
                    QuotationRoom.getInstance(this).quotationDao().addQuotation(quotationIns);
                } else {
                    quotationDatabase.insertQuotation(quotationIns);
                }
                break;
            case R.id.menu_refresh:
                quotationsCount++;
                quotationText.setText(String.format(getResources().getString(R.string.sample_q), quotationsCount));
                authorText.setText(String.format(getResources().getString(R.string.sample_a), quotationsCount));
                Quotation quotationGet = new Quotation(quotationText.getText().toString(), authorText.getText().toString());
                Log.d("tag1", using_room);
                if(using_room.equals("Room")){
                    Log.d("tag1", "HELLO THERE: ");
                    boolean exist = null != QuotationRoom.getInstance(this).quotationDao().getQuotation(quotationGet.getText());
                    quotationMenu.findItem(R.id.menu_add).setVisible(!exist);
                } else {
                    Log.d("tag1", "HELLO THERE33333: ");
                    quotationMenu.findItem(R.id.menu_add).setVisible(!quotationDatabase.exists(quotationGet));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
