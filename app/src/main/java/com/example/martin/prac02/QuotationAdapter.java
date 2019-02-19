package com.example.martin.prac02;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class QuotationAdapter extends ArrayAdapter<Quotation> {

    private int theInt;

    public QuotationAdapter(Context context, int resource, List<Quotation> objects) {
        super(context, resource, objects);
        theInt = resource;
    }

    private class ViewHolder{
        TextView author;
        TextView quotation;
    }

    @Override
    public View getView(final int position,View v, ViewGroup p){
        View view = v;
        ViewHolder vh;
        Log.d("tag1", "getMockQuotations: " + position);
       if(view == null) {
           LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
           view = inflater.inflate(this.theInt, null);
           vh = new ViewHolder();
           vh.author = view.findViewById(R.id.author_quotation_label);
           vh.quotation = view.findViewById(R.id.quotation_label);
           view.setTag(vh);
       }
       vh = (ViewHolder) view.getTag();


       Quotation item = getItem(position);
       vh.author.setText(item.getAuthor());
       vh.quotation.setText(item.getText());

       return view;
    }
}
