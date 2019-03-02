package com.example.martin.prac02;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.example.martin.prac02.tasks.QuotationHttpAsyncTask;

public class QuotationActivity extends AppCompatActivity {

    TextView quotationText;
    TextView authorText;
    Menu quotationMenu;
    boolean addState;
    String using_room;
    Handler handler;

    QuotationDatabase quotationDatabase = QuotationDatabase.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_quotation);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                quotationText.setText(inputMessage.getData().getString("quotationText"));
                authorText.setText(inputMessage.getData().getString("authorText"));
                if(inputMessage.getData().getBoolean("exists")){
                    quotationMenu.findItem(R.id.menu_add).setVisible(false);
                }else{
                    quotationMenu.findItem(R.id.menu_add).setVisible(true);
                }
            }
        };
        using_room = prefs.getString("database_access","room");
        quotationText = findViewById(R.id.quotationText);
        authorText = findViewById(R.id.authorText);
        quotationText.setText(String.format(getResources().getString(R.string.info_quotations), prefs.getString("name","")));
        if(savedInstanceState != null){
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
                final Quotation quotationIns = new Quotation(quotationText.getText().toString(), authorText.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (using_room.equals("Room")) {
                            QuotationRoom.getInstance(getApplicationContext()).quotationDao().addQuotation(quotationIns);
                        } else {
                            quotationDatabase.insertQuotation(quotationIns);
                        }
                    }
                }).start();
                break;
            case R.id.menu_refresh:
                //final Quotation quotationGet = new Quotation(quotationText.getText().toString(), authorText.getText().toString());
                if(networkState()){
                    QuotationHttpAsyncTask quotationHttpAsyncTask = new QuotationHttpAsyncTask(this);
                    quotationHttpAsyncTask.execute();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void hideMenuItems(){
        quotationMenu.findItem(R.id.menu_refresh).setVisible(false);
        quotationMenu.findItem(R.id.menu_add).setVisible(false);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    public void showMenuItems(final Quotation quotation){
        quotationMenu.findItem(R.id.menu_refresh).setVisible(true);
        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain(handler);
                message.getData().putString("quotationText", quotation.getText());
                message.getData().putString("quotationAuthor", quotation.getAuthor());
                if(using_room.equals("Room")){
                    boolean exists = null != QuotationRoom.getInstance(getApplicationContext()).quotationDao().getQuotation(quotation.getText());
                    message.getData().putBoolean("exists", exists);
                } else {
                    message.getData().putBoolean("exists",quotationDatabase.exists(quotation));
                }
                Log.d("tag1", "run: " + message);
                message.sendToTarget();
            }
        }).start();
    }

    public boolean networkState(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null){
            return false;
        }else {
            return networkInfo.isConnected();
        }
    }
}
