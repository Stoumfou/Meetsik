package com.itparis.b3.meetsik.beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bouveti on 11/03/2015.
 */
public class Annonce implements Serializable {

    private int id;
    private String nom;
    private int prix;
    private String date;
    private String categorie;
    private String eMail;

    public Annonce(){
        this.id = 0;
        this.nom ="";
        this.prix = 0;
        this.date = null;
        this.categorie = null;
        this.eMail = "";
    }

    public Annonce(int id, String nom,int prix, String date,String eMail) throws ParseException{
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String geteMail(){
        return this.eMail;
    }

    public void seteMail(String eMail){
        this.eMail = eMail;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
