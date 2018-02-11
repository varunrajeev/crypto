package com.varun.android.cryptocurrencytracker;

import java.util.List;

/**
 * Created by Varun on 1/13/2018.
 */

public class CurrencyPojo {

    public List<String> getCcId() {
        return ccId;
    }

    public void setCcId(List<String> ccId) {
        this.ccId = ccId;
    }

    public List<String> getCcSymbol() {
        return ccSymbol;
    }

    public void setCcSymbol(List<String> ccSymbol) {
        this.ccSymbol = ccSymbol;
    }

    public List<String> getCcName() {
        return ccName;
    }

    public void setCcName(List<String> ccName) {
        this.ccName = ccName;
    }

    public List<String> getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(List<String> priceUSD) {
        this.priceUSD = priceUSD;
    }

    public List<String> getCc1h() {
        return cc1h;
    }

    public void setCc1h(List<String> cc1h) {
        this.cc1h = cc1h;
    }

    public List<String> getCc24h() {
        return cc24h;
    }

    public void setCc24h(List<String> cc24h) {
        this.cc24h = cc24h;
    }

    public List<String> getCc7d() {
        return cc7d;
    }

    public void setCc7d(List<String> cc7d) {
        this.cc7d = cc7d;
    }

    private List<String> ccId;
    private List<String> ccSymbol;
    private List<String> ccName;
    private List<String> priceUSD;
    private List<String> cc1h;
    private List<String> cc24h;
    private List<String> cc7d;


}
