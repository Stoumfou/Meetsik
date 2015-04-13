package com.itparis.b3.meetsik.services;

/**
 * Created by Alexis on 13/04/2015.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.itparis.b3.meetsik.basics.AnnoncesDataSource;
import com.itparis.b3.meetsik.beans.Annonce;

public class BackTask extends AsyncTask<Void, Integer, Void> {

    Context context;
    private ArrayList<Annonce> allAnnonces;
    private boolean isDataLoaded;
    static JSONArray jArray = null;
    private AnnoncesDataSource datasource;

    private static final String TAG_ANNONCES = "annonce";
    private static final String TAG_ID = "id";
    private static final String TAG_NOM = "nom";
    private static final String TAG_DATE = "date";
    private static final String TAG_PRIX = "prix";
    private static final String TAG_EMAIL = "email";




    public void setDataLoaded(boolean dataLoaded){
        isDataLoaded = dataLoaded;
    }

    public boolean isDataLoaded(){
        return isDataLoaded;
    }


    public BackTask(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, "Début du traitement asynchrone", Toast.LENGTH_SHORT).show();
    }

    public JSONArray parseAnnonce(String annonce){
        try {
            jArray = new JSONArray(annonce);

        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jArray;
    }
    @Override
    protected Void doInBackground(Void... params) {
        String textAnnonce = readAnnonceFeed();
        JSONArray jsonAnnonce = parseAnnonce(textAnnonce);
        Boolean result = recAnnonce(jsonAnnonce);

        return null;
    }

        public String readAnnonceFeed() {

            String result = "";
            HttpURLConnection urlConnect = null;
        URL url = null;

        InputStream in = null;

        try {
            url = new URL("http://10.0.2.2/Meetsik/web/app_dev.php/annoncepriceall");
            urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.setRequestMethod("GET");
            urlConnect.connect();
            Log.e("Response", "" + urlConnect.getResponseCode());
            if (urlConnect.getResponseCode() == 200) {
                in = urlConnect.getInputStream();
                String json = IOUtils.toString(in, "UTF-8");
                Log.e("json", json);
                //loadFromJSON(json);
                result = json;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (ParseException e) {
            e.printStackTrace();
        }*/ finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                urlConnect.disconnect();
                e.printStackTrace();
            }
        }
            return result;
    }

    /*public void loadFromJSON(String json) throws ParseException{
        JSONArray annonces;

        try {
            annonces = new JSONArray(json);
            allAnnonces = new ArrayList<Annonce>();
            for (int i=0;i<annonces.length();i++) {

                JSONObject jsonAnnonce = annonces.getJSONObject(i);
                Annonce annonce = new Annonce(jsonAnnonce.getInt("id"),jsonAnnonce.getString("nom"),jsonAnnonce.getInt("prix"),jsonAnnonce.getString("date"),jsonAnnonce.getString("email"));
                annonce.setCategorie("Vente");
                allAnnonces.add(annonce);

            }
            Log.e("DataLoad1", ""+isDataLoaded);
            setDataLoaded(true);
            Log.e("DataLoad2",""+isDataLoaded);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }*/
 // CHANGER annonces par jsonAnnonce !!!
    public Boolean recAnnonce(JSONArray jsonAnnonce) {
        datasource = new AnnoncesDataSource(context);
        datasource.open();

        try {

            for(int i = 0; i < jsonAnnonce.length(); i++){
                Annonce annonce = null;
                JSONObject c = jsonAnnonce.getJSONObject(i);

                int id = c.getInt(TAG_ID);
                String nom = c.getString(TAG_NOM);
                String email = c.getString(TAG_EMAIL);
                String date = c.getString(TAG_DATE);
                int prix = c.getInt(TAG_PRIX);

                annonce = datasource.createAnnonce(nom, prix, date, "Vente", email, id);
            }
            datasource.close();
            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            datasource.close();
            return false;
        }

    }
    @Override
    protected void onProgressUpdate(Integer... values) {
    }

    @Override
    protected void onPostExecute(Void result) {
        Toast.makeText(context, "Le traitement asynchrone est terminé", Toast.LENGTH_SHORT).show();
    }
}