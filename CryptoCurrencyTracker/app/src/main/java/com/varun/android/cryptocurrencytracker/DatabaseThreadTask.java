package com.varun.android.cryptocurrencytracker;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Varun on 2/3/2018.
 */

public class DatabaseThreadTask extends AsyncTask {

    private static final String TAG = "DatabaseThreadTask";

    AppDatabase database;
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    CurrencyPojo currencyPojo;
    String task = "";
    List<CryptoTable> cryptoTables;

    public List<String> ccId = new ArrayList<String>();
    public List<String> ccSymbol = new ArrayList<String>();
    public List<String> ccName = new ArrayList<String>();
    public List<String> priceUSD = new ArrayList<String>();
    public List<String> cc1h = new ArrayList<String>();
    public List<String> cc24h = new ArrayList<String>();
    public List<String> cc7d = new ArrayList<String>();

    /*
    task values = update/retrieve
     - update => deleteAll and then insertAll
     - retrieve => getAll
     */

    DatabaseThreadTask(AppDatabase database, CurrencyPojo currencyPojo, String task, RecyclerAdapter recyclerAdapter, RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout){
        this.database = database;
        this.currencyPojo = currencyPojo;
        this.task = task;
        this.recyclerAdapter = recyclerAdapter;
        this.recyclerView = recyclerView;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public List<CryptoTable> getAll(){
        cryptoTables = database.cryptoDao().getAll();
        Log.d(TAG,"Calling retrieve operation.");
        //set adapter

        return cryptoTables;
    }

    public List<CryptoTable> insertAll(){
        cryptoTables = makeDataForDb(currencyPojo);
        Log.i(TAG,"Calling insert operation.");
        database.cryptoDao().insertAll(cryptoTables);
        return cryptoTables;
    }

    public List<CryptoTable> deleteAll(){
        cryptoTables = makeDataForDb(currencyPojo);
        Log.i(TAG,"Calling delete operation.");
        database.cryptoDao().deleteAll(cryptoTables);
        return cryptoTables;
    }

    public List<CryptoTable> makeDataForDb(CurrencyPojo currencyPojo){
        List<CryptoTable> cryptoTables = new ArrayList<CryptoTable>();
        for (int i=0;i<currencyPojo.getCcId().size();i++){
            CryptoTable cryptoTable = new CryptoTable();
            cryptoTable.setId(currencyPojo.getCcId().get(i));
            cryptoTable.setName(currencyPojo.getCcName().get(i));
            cryptoTable.setSymbol(currencyPojo.getCcSymbol().get(i));
            cryptoTable.setPriceUSD(currencyPojo.getPriceUSD().get(i));
            cryptoTable.setCc1h(currencyPojo.getCc1h().get(i));
            cryptoTable.setCc24h(currencyPojo.getCc24h().get(i));
            cryptoTable.setCc7d(currencyPojo.getCc7d().get(i));
            cryptoTables.add(i,cryptoTable);
        }
        return cryptoTables;
    }

    public CurrencyPojo makeCurrencyPojo(List<CryptoTable> cryptoTables){
        CurrencyPojo currencyPojo = new CurrencyPojo();
        for(int i=0;i<cryptoTables.size();i++){
            ccId.add(cryptoTables.get(i).getId());
            ccSymbol.add(cryptoTables.get(i).getSymbol());
            ccName.add(cryptoTables.get(i).getName());
            priceUSD.add(cryptoTables.get(i).getPriceUSD());
            cc1h.add(cryptoTables.get(i).getCc1h());
            cc24h.add(cryptoTables.get(i).getCc24h());
            cc7d.add(cryptoTables.get(i).getCc7d());
        }
        currencyPojo.setCcId(ccId);
        currencyPojo.setCcSymbol(ccSymbol);
        currencyPojo.setCcName(ccName);
        currencyPojo.setPriceUSD(priceUSD);
        currencyPojo.setCc1h(cc1h);
        currencyPojo.setCc24h(cc24h);
        currencyPojo.setCc7d(cc7d);
        return currencyPojo;
    }

    @Override
    protected List<CryptoTable> doInBackground(Object[] objects) {

        if(task.equals("update")){
            cryptoTables = insertAll();
            return cryptoTables;
        }
        else if(task.equals("retrieve")){
            cryptoTables = getAll();
            currencyPojo = makeCurrencyPojo(cryptoTables);
            PostDataRetrieval postDataRetrieval = new PostDataRetrieval(database,currencyPojo,recyclerAdapter,recyclerView,swipeRefreshLayout);
            postDataRetrieval.execute();
            return cryptoTables;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
