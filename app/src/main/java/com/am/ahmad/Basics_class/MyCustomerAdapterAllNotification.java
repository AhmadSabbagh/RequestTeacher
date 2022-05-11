package com.am.ahmad.Basics_class;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.am.ahmad.R;

import java.util.ArrayList;

/**
 * Created by ahmad on 10/4/2018.
 */

public class MyCustomerAdapterAllNotification extends BaseAdapter implements ListAdapter {
    private ArrayList<String> name = new ArrayList<String>();//offer pic
    private ArrayList<String> desc = new ArrayList<String>();//offer pic
    int [] id ;

    int [] AgentId;
    Context context;
    String result_Un;






    public MyCustomerAdapterAllNotification(ArrayList<String> name,ArrayList<String>  desc ,int [] id,Context context) {
        this.name = name;
        this.desc=desc;
        this.context = context;
        this.id=id;


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
            view = inflater.inflate(R.layout.allnoti_list, null);
        }


        TextView nameText = (TextView) view.findViewById(R.id.job_name);
        nameText.setText(name.get(position));

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

