package com.itparis.b3.meetsik.basics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.itparis.b3.meetsik.beans.Annonce;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bouveti on 06/04/2015.
 */
public class AnnoncesDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NOM,
            MySQLiteHelper.COLUMN_PRIX,
            MySQLiteHelper.COLUMN_DATE,
            MySQLiteHelper.COLUMN_CATEGORIE,
            MySQLiteHelper.COLUMN_EMAIL };

    public AnnoncesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Annonce createAnnonce(String nom, int prix, String date, String categorie, String eMail,int id) {
        Boolean exist = existAnnonceWithId(id);

        if(exist == true){
            Annonce existAnnonce = getAnnonceWithId(id);
            Annonce updatedAnnonce = updateAnnonce(id, existAnnonce);
            return updatedAnnonce;
        }
        else {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_NOM, nom);
            values.put(MySQLiteHelper.COLUMN_PRIX, prix);
            values.put(MySQLiteHelper.COLUMN_DATE, date);
            values.put(MySQLiteHelper.COLUMN_CATEGORIE, categorie);
            values.put(MySQLiteHelper.COLUMN_EMAIL, eMail);
            values.put(MySQLiteHelper.COLUMN_ID, id);
            long insertId = database.insert(MySQLiteHelper.TABLE_ANNONCE, null,
                    values);
            Cursor cursor = database.query(MySQLiteHelper.TABLE_ANNONCE,
                    allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            Annonce newContact = cursorToAnnonce(cursor);
            cursor.close();
            return newContact;
        }
    }

    public Annonce updateAnnonce(int id, Annonce annonce){
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLUMN_NOM, annonce.getNom());
        values.put(MySQLiteHelper.COLUMN_PRIX, annonce.getPrix());
        values.put(MySQLiteHelper.COLUMN_DATE, annonce.getDate());
        values.put(MySQLiteHelper.COLUMN_CATEGORIE, annonce.getCategorie());
        values.put(MySQLiteHelper.COLUMN_EMAIL, annonce.geteMail());

        database.update(MySQLiteHelper.TABLE_ANNONCE, values, MySQLiteHelper.COLUMN_ID + " = " +annonce.getId(), null);

        return getAnnonceWithId(annonce.getId());
    }

    public void deleteAnnonce(Annonce annonce) {
        int id = annonce.getId();
        System.out.println("Annonce deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_ANNONCE, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public Annonce getAnnonceWithId(int id){
        Cursor c = database.query(MySQLiteHelper.TABLE_ANNONCE, allColumns, MySQLiteHelper.COLUMN_ID + " = \"" + id +"\"", null, null, null, null);
        c.moveToFirst();
        Annonce annonce = cursorToAnnonce(c);
        c.close();
        return annonce;
    }

    public List<Annonce> getAllAnnonces() {
        List<Annonce> annonces = new ArrayList<Annonce>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ANNONCE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Annonce contact = cursorToAnnonce(cursor);
            annonces.add(contact);
            cursor.moveToNext();
        }

        cursor.close();
        return annonces;
    }

    public Boolean existAnnonceWithId(int id){
        Cursor c = database.query(MySQLiteHelper.TABLE_ANNONCE, allColumns, MySQLiteHelper.COLUMN_ID + " = \"" + id +"\"", null, null, null, null);
        if(c.getCount()>0){
            c.close();
            return true;
        }
        else {
            c.close();
            return false;
        }
    }

    private Annonce cursorToAnnonce(Cursor cursor) {
        Annonce annonce = new Annonce();
        annonce.setId(cursor.getInt(0));
        annonce.setNom(cursor.getString(1));
        annonce.setPrix(cursor.getInt(2));
        annonce.setDate(cursor.getString(3));
        annonce.setCategorie(cursor.getString(4));
        annonce.seteMail(cursor.getString(5));
        return annonce;
    }
}
