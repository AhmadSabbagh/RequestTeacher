package com.am.ahmad.Basics_class;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.am.ahmad.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ahmad on 4/5/2018.
 */

public class MyCustomerAdapterNotificationForAdmin extends BaseAdapter implements ListAdapter {
    private ArrayList<String> name = new ArrayList<String>();//offer pic
    private ArrayList<String> pic = new ArrayList<String>();//offer pic
    private ArrayList<String> exp = new ArrayList<String>();//offer pic
    int[] id;
    Context context;


    public MyCustomerAdapterNotificationForAdmin(ArrayList<String> name, ArrayList<String> pic, ArrayList<String> exp, int[] id, Context context) {
        this.name = name;
        this.pic = pic;
        this.exp = exp;
        this.context = context;
        this.id = id;


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
            view = inflater.inflate(R.layout.admin_list_noti, null);
        }
        ImageView img = (ImageView)view.findViewById(R.id.agentImage);
        TextView nameText = (TextView) view.findViewById(R.id.agent_name);
        TextView agentText = (TextView) view.findViewById(R.id.agent_text);
        nameText.setText(name.get(position));
        agentText.setText(exp.get(position));
        Picasso.with(context).load(pic.get(position)).into(img);




        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}