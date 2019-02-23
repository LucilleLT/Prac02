package com.example.martin.prac02.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.martin.prac02.Quotation;

import java.util.ArrayList;
import java.util.List;

public class QuotationDatabase extends SQLiteOpenHelper {

    static private QuotationDatabase quotationDatabase;

    private QuotationDatabase(Context context){
        super(context, "quotation_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE quotation_table (id INTEGER PRIMARY KEY AUTOINCREMENT, quote TEXT NOT NULL, author TEXT, UNIQUE(quote));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static synchronized QuotationDatabase getInstance(Context context){
        if(quotationDatabase == null){
            quotationDatabase = new QuotationDatabase(context);
        }
        return quotationDatabase;
    }

    public List<Quotation> getQuotations(){
        SQLiteDatabase db = getReadableDatabase();
        List<Quotation> quotationList = new ArrayList<>();
        Cursor cursor = db.query("quotation_table", new String[]{"quote", "author"}, null, null, null, null, null);
        while(!cursor.isLast()){
            quotationList.add(new Quotation(cursor.getString(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        quotationList.add(new Quotation(cursor.getString(0), cursor.getString(1)));
        cursor.close();
        db.close();
        return quotationList;
    }

    public boolean exists(Quotation quotation){
        boolean exists = false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("quotation_table", null, "quote=?", new String[]{quotation.getText()}, null, null, null, null);

        if(cursor.getCount() > 0){
            exists = true;
        }
        cursor.close();
        db.close();

        return exists;
    }

}
