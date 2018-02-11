package com.varun.android.cryptocurrencytracker;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Varun on 2/5/2018.
 */


public class GetData extends AsyncTask<String, String, String> {

    StringBuilder result = new StringBuilder();
    String resultString = "";
    HttpURLConnection urlConnection;
    AppDatabase database;

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    Boolean isConnected;

    CurrencyPojo currencyPojo = new CurrencyPojo();

    public List<String> ccId = new ArrayList<String>();
    public List<String> ccSymbol = new ArrayList<String>();
    public List<String> ccName = new ArrayList<String>();
    public List<String> priceUSD = new ArrayList<String>();
    public List<String> cc1h = new ArrayList<String>();
    public List<String> cc24h = new ArrayList<String>();
    public List<String> cc7d = new ArrayList<String>();

    public static final String TAG = "GetDataTask";

    GetData(AppDatabase appDatabase, RecyclerAdapter recyclerAdapter, RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout, Boolean isConnected){
        this.database = appDatabase;
        this.recyclerAdapter = recyclerAdapter;
        this.recyclerView = recyclerView;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.isConnected = isConnected;
    }

    @Override
    protected String doInBackground(String... args) {

        if(isConnected){
            //Log.d(TAG,"Starting API call");

            try {
                URL url = new URL("https://api.coinmarketcap.com/v1/ticker/");
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            }catch( Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }
            resultString = result.toString();
        }
        else {
            DatabaseThreadTask databaseThreadTask = new DatabaseThreadTask(database,makeCurrencyPojo(result.toString()),"retrieve",recyclerAdapter,recyclerView,swipeRefreshLayout);
            databaseThreadTask.execute();
        }
            return resultString;
        }


    public String checkAndSetSign(String pChange){
        if(!pChange.startsWith("-")){
            pChange = "+"+pChange;
        }
        return pChange;
    }

    public CurrencyPojo makeCurrencyPojo(String result){
        CurrencyPojo currencyPojo = new CurrencyPojo();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ccId.add(jsonObject.get("id").toString());
                ccName.add(jsonObject.get("name").toString());
                ccSymbol.add(jsonObject.get("symbol").toString());
                priceUSD.add(jsonObject.get("price_usd").toString());
                //Format properly with +/-
                String pChange = "";
                pChange = jsonObject.get("percent_change_1h").toString();
                pChange = checkAndSetSign(pChange);
                cc1h.add(pChange);
                pChange = jsonObject.get("percent_change_24h").toString();
                pChange = checkAndSetSign(pChange);
                cc24h.add(pChange);
                pChange = jsonObject.get("percent_change_7d").toString();
                pChange = checkAndSetSign(pChange);
                cc7d.add(pChange);

            }

            currencyPojo.setCcId(ccId);
            currencyPojo.setCcSymbol(ccSymbol);
            currencyPojo.setCcName(ccName);
            currencyPojo.setPriceUSD(priceUSD);
            currencyPojo.setCc1h(cc1h);
            currencyPojo.setCc24h(cc24h);
            currencyPojo.setCc7d(cc7d);

        } catch (JSONException ex) {
            //Log.d(TAG,"Parsing result to JSON failed. "+ex);
        }
        return currencyPojo;
    }



    @Override
    protected void onPostExecute(String result) {
        if(isConnected) {
            PostDataRetrieval postDataRetrieval = new PostDataRetrieval(database, makeCurrencyPojo(result), recyclerAdapter, recyclerView, swipeRefreshLayout);
            postDataRetrieval.execute();
        }
    }

}