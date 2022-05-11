package com.am.ahmad.Basics_class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.am.ahmad.Libraries;
import com.am.ahmad.R;

import java.util.ArrayList;

/**
 * Created by ahmad on 6/4/2018.
 */

public class MyCustomerAdapterLib extends BaseAdapter implements ListAdapter {
    private ArrayList<String> name = new ArrayList<String>();//offer pic
    int [] id ;
    Context context;
    String [] desc,address;
    String type;
    public MyCustomerAdapterLib(ArrayList<String> nameA, String[] desc, String[] address, int[] id, Context context) {
        this.name=nameA;
        this.desc=desc;
        this.address=address;
        this.id=id;
        this.context=context;

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
            view = inflater.inflate(R.layout.clubs_list, null);
        }
        TextView nameT = (TextView) view.findViewById(R.id.name);
        TextView adressT = (TextView) view.findViewById(R.id.adress);
        TextView descT = (TextView) view.findViewById(R.id.desc);

        nameT.setText(name.get(position));
        adressT.setText(address[position]);
        descT.setText(desc[position]);





        return view;
    }

}
