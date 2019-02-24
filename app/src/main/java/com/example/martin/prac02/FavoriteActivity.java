package com.example.martin.prac02;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.prac02.databases.QuotationDatabase;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    QuotationAdapter quotationAdapter;
    QuotationDatabase quotationDatabase = QuotationDatabase.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        quotationAdapter = new QuotationAdapter(this, R.layout.quotation_list_row, quotationDatabase.getQuotations());
        ListView quotationList = findViewById(R.id.quotation_list);
        quotationList.setAdapter(quotationAdapter);
        quotationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                TextView authorTextView = view.findViewById(R.id.author_quotation_label);
                String authorText = "" + authorTextView.getText();
                if(!authorText.equals(""))
                {
                    intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + authorText));
                    if(intent.resolveActivity(getPackageManager())!=null){
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(FavoriteActivity.this, R.string.authorNotFound, Toast.LENGTH_LONG).show();
                }
            }
        });

        quotationList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteActivity.this);
                builder.setIcon(android.R.drawable.stat_sys_warning);
                builder.setTitle(R.string.deleteTitle);
                builder.setMessage(R.string.deleteMessage);
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Quotation item = quotationAdapter.getItem(position);
                        quotationDatabase.deleteQuotations(item);
                        quotationAdapter.remove(item);
                        quotationAdapter.notifyDataSetChanged();
                        Toast.makeText(FavoriteActivity.this, R.string.deteled, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite_activity_menu, menu);
        if(quotationAdapter.isEmpty()){
            menu.findItem(R.id.menu_clear).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_clear:
                AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteActivity.this);
                builder.setIcon(android.R.drawable.stat_sys_warning);
                builder.setTitle(R.string.clearAllAlertTitle);
                builder.setMessage(R.string.clearAllAlertMessage);
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quotationDatabase.deleteAllQuotations();
                        quotationAdapter.clear();
                        quotationAdapter.notifyDataSetChanged();
                        item.setVisible(false);
                    }
                });
                builder.create().show();

                break;
        }
        return true;
    }

    public ArrayList<Quotation> getMockQuotations(){
        ArrayList<Quotation> quotationList = new ArrayList<>();

        for(int i = 0; i < 20 ; i ++){
            Quotation aux;
            if(i == 0){
                aux = new Quotation("ESTO ES UN TEST" + i, "");
            }else {
                aux = new Quotation("ESTO ES UN TEST" + i, "Nikola_Tesla");
            }
            quotationList.add(aux);
        }
        return quotationList;
    }

}
