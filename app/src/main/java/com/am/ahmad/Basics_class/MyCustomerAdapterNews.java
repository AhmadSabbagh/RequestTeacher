package com.am.ahmad.Basics_class;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.am.ahmad.News_List_View;
import com.am.ahmad.News_details;
import com.am.ahmad.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ahmad on 4/15/2018.
 */

public class MyCustomerAdapterNews extends BaseAdapter implements ListAdapter {
    private ArrayList<String> namelist = new ArrayList<String>();//offer pic
    private ArrayList<String> pic = new ArrayList<String>();//offer pic
    int [] center_id ;
    Context context;
    String result_reg_face;
    String type;

    public MyCustomerAdapterNews(ArrayList<String> namelist, int[] center_id, Context context) {
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
            view = inflater.inflate(R.layout.news_list, null);
        }
        TextView newsText = (TextView) view.findViewById(R.id.newsText);
        newsText.setText(namelist.get(position));

        newsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetails(center_id[position]);
            }
        });




        return view;
    }

    public void getDetails(final int id) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender((Activity) context);
                    result_reg_face = http.SEND_get_News_details("http://62.212.88.104/dal/API.asmx/getdata_news_details",id);
                    Log.d("String", result_reg_face);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {


                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(result_reg_face!=null) {
                    if (result_reg_face.contains("txt_news")) {

                        try {
                            JSONObject JA ;
                            JA=new JSONObject(result_reg_face);
                            String subject =(String) JA.get("txt_news") ;
                            String pic = "http://62.212.88.104/picture/"+(String)JA.get("url");
                            Intent intent =new Intent(context,News_details.class);
                            intent.putExtra("text",subject);
                            intent.putExtra("pic",pic);
                            context.startActivity(intent);








                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

}

