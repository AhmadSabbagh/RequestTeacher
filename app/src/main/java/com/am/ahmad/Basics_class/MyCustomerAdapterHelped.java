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
 * Created by ahmad on 6/6/2018.
 */

public class MyCustomerAdapterHelped extends BaseAdapter implements ListAdapter {
    private ArrayList<String> help = new ArrayList<String>();//offer pic
    Context context;
    String  [] reHelp;

    public MyCustomerAdapterHelped(ArrayList<String> help, String [] reHelp, Context context) {
        this.help=help;
        this.context=context;
        this.reHelp=reHelp;
    }

    @Override
    public int getCount() {
        return help.size();
    }

    @Override
    public Object getItem(int pos) {
        return help.get(pos);
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
            view = inflater.inflate(R.layout.helped_list_view, null);
        }
        TextView helpedET = (TextView) view.findViewById(R.id.myqustion) ;
        TextView answer = (TextView) view.findViewById(R.id.yourA);


        helpedET.setText(help.get(position));
        answer.setText(reHelp[position]);




        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
