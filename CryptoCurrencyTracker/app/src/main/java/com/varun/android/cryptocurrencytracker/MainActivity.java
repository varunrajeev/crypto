package com.varun.android.cryptocurrencytracker;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.varun.MESSAGE";
    private static final String TAG = "MyActivity";
    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    Timer timerObj = new Timer();

    public List<String> ccId = new ArrayList<>();
    public List<String> ccSymbol = new ArrayList<>();
    public List<String> ccName = new ArrayList<>();
    public List<String> priceUSD = new ArrayList<>();
    public List<String> cc1h = new ArrayList<>();
    public List<String> cc24h = new ArrayList<>();
    public List<String> cc7d = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String resultString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int red = getResources().getColor(R.color.Red);
        int green = getResources().getColor(R.color.Green);

        final AppDatabase database = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"cryptodb").build();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter =  new RecyclerAdapter(red,green,getApplicationContext());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        GetData getDataTask = new GetData(database,recyclerAdapter,recyclerView,swipeRefreshLayout,checkConnectivity());
        getDataTask.execute();

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(TAG, "onRefresh called from SwipeRefreshLayout");
                        if(!checkConnectivity()){
                            swipeRefreshLayout.setRefreshing(false);
                            GetData getDataTask = new GetData(database,recyclerAdapter,recyclerView,swipeRefreshLayout,false);
                            getDataTask.execute();
                            //start timer here
                            return;
                        }
                        else {
                            swipeRefreshLayout.setRefreshing(true);
                            GetData getDataTask = new GetData(database,recyclerAdapter,recyclerView,swipeRefreshLayout,true);
                            getDataTask.execute();
                        }
                    }
                }

        );

    }

    private boolean checkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(!(activeNetworkInfo != null && activeNetworkInfo.isConnected())) {
            Toast toast = Toast.makeText(getApplicationContext(),"Cannot connect to internet",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

    private void stopTimer() {
        timerObj.cancel();
    }

    private void startTimer() {
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Log.d(TAG,"Timer running an iteration");
                //GetData getDataTask = new GetData();
                //getDataTask.execute();
            }
        };
        timerObj.schedule(timerTask, 0, 3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //stopTimer();
    }




}
