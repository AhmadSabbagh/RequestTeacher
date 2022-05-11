package com.am.ahmad.Basics_class;

/**
 * Created by ahmad on 4/11/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.am.ahmad.NormalOffers;
import com.am.ahmad.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyCustomerAdapterJobSearch extends BaseAdapter implements ListAdapter {
    private ArrayList<String> name = new ArrayList<String>();//offer pic
    private ArrayList<String> desc = new ArrayList<String>();//offer pic
    int [] id ;
String [] url;
    int [] AgentId;
    Context context;
    String result_Un;






    public MyCustomerAdapterJobSearch(ArrayList<String> name,ArrayList<String>  desc ,int [] id,Context context,String [] url) {
        this.name = name;
        this.desc=desc;
        this.context = context;
        this.id=id;
        this.url=url;


    }

    public MyCustomerAdapterJobSearch(ArrayList<String> offerDesc, ArrayList<String> offerDesc1, int[] offer_id, Context normalOffers) {
        this.name = offerDesc;
        this.desc=offerDesc1;
        this.context = normalOffers;
        this.id=offer_id;
    }


    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int pos) {
        return name.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.job_search_list, null);
        }


        TextView nameText = (TextView) view.findViewById(R.id.job_name);
        nameText.setText(name.get(position));
        ImageView image=(ImageView)view.findViewById(R.id.img);
        if(url!=null ) {
            Picasso.with(context).load(url[position]).into(image);
        }
        TextView descText = (TextView) view.findViewById(R.id.job_desc);
       // descText.setText(desc.get(position));


        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
