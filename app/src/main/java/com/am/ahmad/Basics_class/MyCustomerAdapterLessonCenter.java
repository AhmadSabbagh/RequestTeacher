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
 * Created by ahmad on 4/15/2018.
 */

public class MyCustomerAdapterLessonCenter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> namelist = new ArrayList<String>();//offer pic
    private ArrayList<String> pic = new ArrayList<String>();//offer pic
    int [] center_id ;
    Context context;
    String type;

    public MyCustomerAdapterLessonCenter(ArrayList<String> namelist, ArrayList<String> pic, int[] center_id, Context context) {
        this.pic=pic;
        this.namelist=namelist;
        this.center_id=center_id;
        this.context=context;
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
            view = inflater.inflate(R.layout.lesson_center_list, null);
        }
        ImageView picture = (ImageView) view.findViewById(R.id.agentImage1) ;
        TextView addressTV = (TextView) view.findViewById(R.id.agent_name1);

        Picasso.with(context).load(pic.get(position)).into(picture);


        addressTV.setText(namelist.get(position));




        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}

