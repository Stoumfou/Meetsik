package com.itparis.b3.meetsik.basics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.itparis.b3.meetsik.beans.Annonce;

import com.itparis.b3.meetsik.R;

import java.util.Collection;

/**
 * Created by Bouveti on 11/03/2015.
 */
public class AnnonceAdapter extends ArrayAdapter <Annonce> {

    public AnnonceAdapter(Context context, int resource) {
        super(context, 0);
    }


    public void setAll(Collection<Annonce> collection){
        setNotifyOnChange(false);
        clear();
        addAll(collection);
        setNotifyOnChange(true);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;

        if (result == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            result = li.inflate(R.layout.line_annonce, null);

        }

        Annonce annonce = getItem(position);


        ((TextView)result.
                findViewById(R.id.text_nom)).
                setText(annonce.getNom());
        ((TextView)result.
                findViewById(R.id.text_prix)).
                setText(String.valueOf(annonce.getPrix())+"€");


        return result;
    }
}
