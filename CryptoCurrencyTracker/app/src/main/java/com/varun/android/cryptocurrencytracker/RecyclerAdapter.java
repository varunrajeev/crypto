package com.varun.android.cryptocurrencytracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Varun on 1/11/2018.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    public String TAG = "RecyclerAdapter";

    public int red;
    public int green;

    Context context = null;

    List<String> ccId;
    public List<String> ccSymbol;
    public List<String> ccName;
    public List<String> priceUSD;
    public List<String> cc1h;
    public List<String> cc24h;
    public List<String> cc7d;

    public RecyclerAdapter(int red, int green, Context context){
        this.ccId = new ArrayList<>();
        this.ccSymbol = new ArrayList<>();
        this.ccName = new ArrayList<>();
        this.priceUSD = new ArrayList<>();
        this.cc1h = new ArrayList<>();
        this.cc24h = new ArrayList<>();
        this.cc7d = new ArrayList<>();

        this.context = context;

        this.red = red;
        this.green = green;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_card,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return  recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        //holder.ccIdTextView.setText(ccId.get(position));
        //holder.ccSymbolTextView.setText(ccSymbol.get(position));
        holder.ccNameTextView.setText(ccName.get(position));
        holder.priceUSDTextView.setText("$" + priceUSD.get(position).substring(0,priceUSD.get(position).indexOf(".")));
        holder.cc1hTextView.setText(cc1h.get(position));
        holder.cc24hTextView.setText(cc24h.get(position));
        holder.cc7dTextView.setText(cc7d.get(position));

        String fileName = ccSymbol.get(position).toLowerCase() + "2x";
        //Log.d(TAG,"Filename is: "+fileName);

        holder.imageView.setImageResource(context.getResources().getIdentifier(fileName , "drawable", context.getPackageName()));

        if(cc1h.get(position).startsWith("+")){
            holder.cc1hTextView.setTextColor(green);
        }
        else holder.cc1hTextView.setTextColor(red);

        if(cc24h.get(position).startsWith("+")){
            holder.cc24hTextView.setTextColor(green);
        }
        else holder.cc24hTextView.setTextColor(red);

        if(cc7d.get(position).startsWith("+")){
            holder.cc7dTextView.setTextColor(green);
        }
        else holder.cc7dTextView.setTextColor(red);


    }

    @Override
    public int getItemCount() {
        return ccId.size();
    }

    public void swapDataSet(CurrencyPojo currencyPojo){
        this.ccId = currencyPojo.getCcId();
        this.ccName = currencyPojo.getCcName();
        this.ccSymbol = currencyPojo.getCcSymbol();
        this.priceUSD = currencyPojo.getPriceUSD();
        this.cc1h = currencyPojo.getCc1h();
        this.cc24h = currencyPojo.getCc24h();
        this.cc7d = currencyPojo.getCc7d();

        notifyDataSetChanged();

    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView ccIdTextView,
                ccNameTextView,
                ccSymbolTextView,
                priceUSDTextView,
                cc1hTextView,
                cc24hTextView,
                cc7dTextView;
        ImageView imageView;

        public RecyclerViewHolder(View view){
            super(view);
            //ccIdTextView = (TextView) view.findViewById(R.id.ccId);
            ccNameTextView = (TextView) view.findViewById(R.id.ccName);
            //ccSymbolTextView = (TextView) view.findViewById(R.id.ccSymbol);
            priceUSDTextView = (TextView) view.findViewById(R.id.ccPriceUSD);
            cc1hTextView = (TextView) view.findViewById(R.id.cc1h);
            cc24hTextView = (TextView) view.findViewById(R.id.cc24h);
            cc7dTextView = (TextView) view.findViewById(R.id.cc7d);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }

    }
}
