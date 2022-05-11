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
 * Created by ahmad on 4/25/2018.
 */

public class MyCustomerAdapterRequestStudent extends BaseAdapter implements ListAdapter {
    private ArrayList<String> name = new ArrayList<String>();//offer pic
    boolean [] flag ;
    String [] type,date;

    int [] AgentId;
    Context context;
    String result_Un;






    public MyCustomerAdapterRequestStudent(ArrayList<String> name,String []  type ,String [] date,Context context ,boolean [] flag) {
        this.name = name;
        this.type=type;
        this.context = context;
        this.flag=flag;
        this.date=date;


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
            view = inflater.inflate(R.layout.student_request, null);
        }
        TextView nameS = (TextView) view.findViewById(R.id.nameS);
        nameS.setText(name.get(position));
        TextView dateS = (TextView) view.findViewById(R.id.date);
        dateS.setText(date[position]);
        TextView typeS = (TextView) view.findViewById(R.id.type);
        typeS.setText(type[position]);
        TextView flagT = (TextView) view.findViewById(R.id.result);
        if(flag[position]) {
            flagT.setText(" وافق المدرس");
        }
        else
        {
            flagT.setText("مرفوض");

        }


        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
