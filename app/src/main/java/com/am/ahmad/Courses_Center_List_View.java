package com.am.ahmad;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterCenters;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Courses_Center_List_View extends AppCompatActivity {
String result_reg_face;
ListView listView;
    int []center_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses__center__list__view);
        listView=(ListView)findViewById(R.id.Courses_list_v) ;
        getCourses();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Courses_Center_List_View.this,Courses_Center_Profile.class);
                intent.putExtra("center_id",center_id[position]);
                startActivity(intent);
            }
        });

    }
    public void getCourses() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Courses_Center_List_View.this);
                    result_reg_face = http.SEND_get_Courses("http://62.212.88.104/dal/API.asmx/getdata_courses_center");

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
                    if (result_reg_face.contains("center_name")) {

                        try {
                            JSONArray JA ;
                            JA=new JSONArray(result_reg_face);
                            String []center_name  =new String[JA.length()];
                            String []center_address = new String[JA.length()];
                            String []center_desc = new String[JA.length()];
                            center_id= new int[JA.length()];
                            ArrayList<String> namelist = new ArrayList<String>();
                            ArrayList<String>address  = new ArrayList<String>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);
                                center_name[i] = (String) JO.get("center_name");
                                center_id[i] = (int) JO.get("center_id");
                                center_address[i] = (String) JO.get("address");
                                center_desc[i] = (String) JO.get("descr");

                                namelist.add(center_name[i]);
                                address.add(center_address[i]);
                            }
                           MyCustomerAdapterCenters myCustomerAdapterCenters= new MyCustomerAdapterCenters(namelist,address,center_id,Courses_Center_List_View.this,"course_center");
                            listView.setAdapter(myCustomerAdapterCenters);;

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
