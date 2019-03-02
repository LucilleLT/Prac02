package com.example.martin.prac02.tasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.martin.prac02.Quotation;
import com.example.martin.prac02.QuotationActivity;
import com.example.martin.prac02.databases.QuotationDatabase;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class QuotationHttpAsyncTask extends AsyncTask<Void,Void, Quotation> {

    private WeakReference<QuotationActivity> quotationActivityWeakReference;
    private QuotationDatabase quotationDatabase;

    public QuotationHttpAsyncTask(QuotationActivity quotationActivityWeakReference) {
        this.quotationActivityWeakReference =  new WeakReference<>(quotationActivityWeakReference);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        quotationActivityWeakReference.get().hideMenuItems();
    }

    @Override
    protected Quotation doInBackground(Void... voids) {
        Uri.Builder builder = new Uri.Builder();
        //https://api.forismatic.com/api/1.0/?mehod=getQuote&format=json&lang=<en/ru>
        builder.scheme("https");
        builder.authority("api.forismatic.com");
        builder.appendPath("api");
        builder.appendPath("1.0/");
        builder.appendQueryParameter("mehod", "etQuote");
        builder.appendQueryParameter("format", "json");
        builder.appendQueryParameter("lang", "en");

        Log.d("tag1", builder.build().toString());
        Quotation quotation = new Quotation();
        try {
            Log.d("tag1", "daaaaaaaaaaaaaaaaaaaaaaaaaa");
            URL url = new URL("https://api.forismatic.com/api/1.0/?mehod=etQuote&format=json&lang=en");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            Log.d("tag1", "" + connection.getResponseCode());
            if(connection.getResponseCode() == HttpsURLConnection.HTTP_OK){
                Log.d("tag1", "bbbbbbbbbbbbbbbbbbbbb");
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                Gson gson = new Gson();
                quotation = gson.fromJson(reader, Quotation.class);
                Log.d("tag1", "doInBackground: " + quotation.getText());
                Log.d("tag1", "doInBackground: " + quotation.getAuthor());
                reader.close();
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quotation;
    }

    @Override
    protected void onPostExecute(Quotation quotation) {
        super.onPostExecute(quotation);
        final Quotation quotationGet = new Quotation("cita prueba 1", "autor1");
        quotationActivityWeakReference.get().showMenuItems(quotation);
    }

}