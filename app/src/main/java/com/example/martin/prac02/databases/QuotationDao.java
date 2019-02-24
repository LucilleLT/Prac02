package com.example.martin.prac02.databases;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.example.martin.prac02.Quotation;

import java.util.List;

@Dao
public interface QuotationDao {
    @Query("SELECT * FROM quotation_table")
    List<Quotation> getQuotations();
    @Query("SELECT * FROM quotation_table WHERE quote = :name")
    Quotation getQuotation(String name);
    @Query("DELETE FROM quotation_table")
    void deleteAllQuotations();
}
