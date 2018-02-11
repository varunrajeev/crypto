package com.varun.android.cryptocurrencytracker;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Varun on 2/11/2018.
 */

public class PostDataRetrieval {

    public static final String TAG = "PostDataRetrieval";

    CurrencyPojo  currencyPojo;
    AppDatabase database;
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;


    PostDataRetrieval(AppDatabase database, CurrencyPojo currencyPojo, RecyclerAdapter recyclerAdapter, RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout){
        this.database = database;
        this.currencyPojo = currencyPojo;
        this.recyclerAdapter = recyclerAdapter;
        this.recyclerView = recyclerView;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public void execute(){

            recyclerAdapter.swapDataSet(currencyPojo);
            recyclerView.setAdapter(recyclerAdapter);
            Log.d(TAG,"Completed setting adapter.");

            swipeRefreshLayout.setRefreshing(false);


    }


}
