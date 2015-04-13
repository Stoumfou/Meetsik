package com.itparis.b3.meetsik.mains;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itparis.b3.meetsik.R;
import com.itparis.b3.meetsik.beans.Annonce;

import java.io.Serializable;

/**
 * Created by Bouveti on 02/04/2015.
 */
public class DetailFragment extends Fragment {

    TextView nom, prix, date, auteur, categorie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle arg = getArguments();
        Serializable annonce = arg.getSerializable("annonce");
        View view = inflater.inflate(R.layout.detail_fragment,null);

        nom = (TextView) view.findViewById(R.id.textNom);
        nom.setText(((Annonce)annonce).getNom());

        prix = (TextView) view.findViewById(R.id.textPrix);
        prix.setText(""+Integer.valueOf(((Annonce)annonce).getPrix())+"€");

        date = (TextView) view.findViewById(R.id.textDate);
        date.setText(((Annonce)annonce).getDate().toString());

        auteur = (TextView) view.findViewById(R.id.textAuteur);
        auteur.setText(((Annonce)annonce).geteMail());

        categorie = (TextView) view.findViewById(R.id.textCategorie);
        categorie.setText(((Annonce)annonce).getCategorie());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
