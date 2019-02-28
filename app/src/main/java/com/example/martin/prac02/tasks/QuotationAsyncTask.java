package com.example.martin.prac02.tasks;

import android.os.AsyncTask;

import com.example.martin.prac02.FavoriteActivity;
import com.example.martin.prac02.Quotation;
import com.example.martin.prac02.databases.QuotationDatabase;
import com.example.martin.prac02.databases.QuotationRoom;

import java.lang.ref.WeakReference;
import java.util.List;

public class QuotationAsyncTask extends AsyncTask<Boolean,Void, List<Quotation>> {

    private WeakReference<FavoriteActivity> favouriteActivityWeakReference;
    private QuotationDatabase quotationDatabase;

    public QuotationAsyncTask(FavoriteActivity favouriteActivityWeakReference) {
        this.favouriteActivityWeakReference = new WeakReference<>(favouriteActivityWeakReference);
        quotationDatabase = QuotationDatabase.getInstance(favouriteActivityWeakReference.getApplicationContext());
    }

    @Override
    protected List<Quotation> doInBackground(Boolean... booleans) {
        List<Quotation> list;
        if(booleans[0]){
            list = QuotationRoom.getInstance(favouriteActivityWeakReference.get().getApplicationContext()).quotationDao().getQuotations();
        } else {
            list = quotationDatabase.getQuotations();
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<Quotation> quotations) {
        super.onPostExecute(quotations);
        favouriteActivityWeakReference.get().setQuotationAdapter(quotations);
    }

}
