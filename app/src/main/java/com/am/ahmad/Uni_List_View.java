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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Uni_List_View extends AppCompatActivity {
String result_reg_face;
    ListView listView;
    int []center_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni__list__view);
        listView=(ListView)findViewById(R.id.UniList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Uni_List_View.this,Reg_In_Uni.class);
                intent.putExtra("uni_id",center_id[position]);
                startActivity(intent);
            }
        });

        getUni();
    }
    public void getUni() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Uni_List_View.this);
                    result_reg_face = http.SEND_get_Courses("http://62.212.88.104/dal/API.asmx/getdata_universities");
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
                    if (result_reg_face.contains("address")) {


                        try {
                            JSONArray JA ;
                            JA=new JSONArray(result_reg_face);
                            String []center_name  =new String[JA.length()];
                            String []center_address = new String[JA.length()];
                            center_id= new int[JA.length()];
                            ArrayList<String> namelist = new ArrayList<String>();
                            ArrayList<String>address  = new ArrayList<String>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);
                                center_name[i] = (String) JO.get("university_name");
                                center_id[i] = (int) JO.get("university_id");
                                center_address[i] = (String) JO.get("address");
                                namelist.add(center_name[i]);
                                address.add(center_address[i]);
                            }
                            MyCustomerAdapterCenters myCustomerAdapterCenters= new MyCustomerAdapterCenters(namelist,address,center_id,Uni_List_View.this,"uni");
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
