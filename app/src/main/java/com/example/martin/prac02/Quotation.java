package com.example.martin.prac02;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "quotation_table", indices = {@Index(value = {"quote"}, unique = true)})
public class Quotation {

    @NonNull
    @ColumnInfo(name = "quote")
    @SerializedName("quoteText")
    private String text;
    @ColumnInfo(name = "author")
    @SerializedName("quoteAuthor")
    private String author;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public Quotation(){

    }

    public Quotation(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
