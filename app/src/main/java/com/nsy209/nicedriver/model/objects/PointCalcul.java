package com.nsy209.nicedriver.model.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import java.util.Date;

/**
 * Created by Sébastien on 17/08/2017.
 */
@Entity(tableName = "PointsCalcul", primaryKeys = {"latitude", "longitude", "date", "type"})
public class PointCalcul {
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "valeur")
    private double valeur;
    @ColumnInfo(name = "couleur")
    private String couleur;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
