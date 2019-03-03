package com.example.martin.prac02.tasks;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.martin.prac02.Quotation;
import com.example.martin.prac02.QuotationActivity;
import com.example.martin.prac02.databases.QuotationDatabase;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(quotationActivityWeakReference.get().getApplicationContext());
        String idioma = sharedPreferences.getString("language_field", "en");
        String httpAccess = sharedPreferences.getString("request_field","");
        Uri.Builder builder = new Uri.Builder();
        //https://api.forismatic.com/api/1.0/?mehod=getQuote&format=json&lang=<en/ru>
        builder.scheme("https");
        builder.authority("api.forismatic.com");
        builder.encodedPath("api/1.0/");

        Quotation quotation = new Quotation();

        if(httpAccess.equals("GET")){
            builder.appendQueryParameter("method", "getQuote");
            builder.appendQueryParameter("format", "json");
            builder.appendQueryParameter("lang", idioma);

            Log.d("tag1", builder.build().toString());
            try {
                URL url = new URL(builder.build().toString());
                Log.d("tag1", url.toString());
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod(httpAccess);
                connection.setDoInput(true);
                Log.d("tag1", "" + connection.getResponseCode());
                if(connection.getResponseCode() == HttpsURLConnection.HTTP_OK){
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
        }else{
            String body = "?language="+idioma+"&format=json&method=getQuote";
            try {
                Log.d("tag1", builder.build().toString());
                URL url = new URL(builder.build().toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(body);
                writer.flush();
                writer.close();
                if(connection.getResponseCode() ==HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    Gson gson = new Gson();
                    Log.d("HTTP_CORRECT_POST", "POST correct");
                    quotation = gson.fromJson(reader, Quotation.class);
                    reader.close();
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
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