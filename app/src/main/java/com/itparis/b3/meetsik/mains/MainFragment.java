package com.itparis.b3.meetsik.mains;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itparis.b3.meetsik.basics.AnnoncesDataSource;
import com.itparis.b3.meetsik.beans.Annonce;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.itparis.b3.meetsik.basics.AnnonceAdapter;
import com.itparis.b3.meetsik.R;
import com.itparis.b3.meetsik.services.GetAllAnnonceService;

/**
 * Created by Bouveti on 11/03/2015.
 */
public class MainFragment extends Fragment {

    ImageView logo;
    TextView date;
    ListView liste;
    AnnonceAdapter adapter;
    //GetAllAnnonceService service;
    AnnoncesDataSource dataBase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, null);
       dataBase = new AnnoncesDataSource(getActivity());

        //service = new GetAllAnnonceService();*/
        adapter = new AnnonceAdapter(getActivity(),0);

        Date today = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        String dateToday = f.format(today);

        /*Intent intent = new Intent(getActivity(), GetAllAnnonceService.class);
        getActivity().startService(intent);*/


        logo = (ImageView) view.findViewById(R.id.logo);
        date = (TextView) view.findViewById(R.id.date);

        date.setText(dateToday);
        logo.setImageResource(R.drawable.logo);

        liste = (ListView) view.findViewById(R.id.listAnnonce);



        ArrayList<Annonce> allAnnonces = new ArrayList<Annonce>();

        dataBase.open();
        allAnnonces = (ArrayList<Annonce>) dataBase.getAllAnnonces();
        adapter.setAll(allAnnonces);

        dataBase.close();

        if(adapter != null)liste.setAdapter(adapter);

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object annonce = liste.getItemAtPosition(position);

                DetailFragment secondFrag = new DetailFragment();
                Bundle arg = new Bundle();
                arg.putSerializable("annonce", (Annonce) annonce);
                secondFrag.setArguments(arg);

                ((MainActivity) getActivity()).showFragment(secondFrag);


            }
        });

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
}
