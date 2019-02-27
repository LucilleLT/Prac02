package com.example.martin.prac02.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.martin.prac02.Quotation;

@Database(entities = {Quotation.class}, version = 1)
public abstract class QuotationRoom extends RoomDatabase {

    private static QuotationRoom quotationRoom;

    public synchronized static QuotationRoom getInstance(Context context) {
        if (quotationRoom == null) {

            quotationRoom = Room.databaseBuilder(context, QuotationRoom.class, "quotation_database").allowMainThreadQueries().build();
        }
        return quotationRoom;
    }

    public abstract QuotationDao quotationDao();



}

