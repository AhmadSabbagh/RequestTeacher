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
import android.widget.RatingBar;
import android.widget.TextView;

import com.am.ahmad.Courses_Center_List_View;
import com.am.ahmad.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ahmad on 4/15/2018.
 */

public class MyCustomerAdapterCenters extends BaseAdapter implements ListAdapter {
    private ArrayList<String> namelist = new ArrayList<String>();//offer pic
    private ArrayList<String> address = new ArrayList<String>();//offer pic
    int [] center_id ;
    Context context;
    String type;

    public MyCustomerAdapterCenters(ArrayList<String> namelist, ArrayList<String> address, int[] center_id, Context context,String type) {
        this.address=address;
        this.namelist=namelist;
        this.center_id=center_id;
        this.context=context;
        this.type=type;
    }

    @Override
    public int getCount() {
        return namelist.size();
    }

    @Override
    public Object getItem(int pos) {
        return namelist.get(pos);
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
            view = inflater.inflate(R.layout.centers_gets_list, null);
        }
        TextView name = (TextView) view.findViewById(R.id.centerName) ;
        TextView addressTV = (TextView) view.findViewById(R.id.centerAdress);


        name.setText(namelist.get(position));
        addressTV.setText(address.get(position));




        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}

