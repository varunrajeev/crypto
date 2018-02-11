package com.varun.android.cryptocurrencytracker;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Varun on 2/1/2018.
 */

@Entity(tableName = "crypto")
public class CryptoTable {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(String priceUSD) {
        this.priceUSD = priceUSD;
    }

    public String getCc1h() {
        return cc1h;
    }

    public void setCc1h(String cc1h) {
        this.cc1h = cc1h;
    }

    public String getCc24h() {
        return cc24h;
    }

    public void setCc24h(String cc24h) {
        this.cc24h = cc24h;
    }

    public String getCc7d() {
        return cc7d;
    }

    public void setCc7d(String cc7d) {
        this.cc7d = cc7d;
    }

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;

    @ColumnInfo
    private String symbol;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String priceUSD;

    @ColumnInfo
    private String cc1h;

    @ColumnInfo
    private String cc24h;

    @ColumnInfo
    private String cc7d;


}
