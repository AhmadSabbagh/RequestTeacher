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

public class Rawda extends AppCompatActivity {
ListView listView;
    String result_reg_face;
    int []center_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rawda);
        listView=(ListView)findViewById(R.id.rawdaList);
        getRawda();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Rawda.this,Reg_In_rawda.class);
                intent.putExtra("rawda_id",center_id[position]);
                startActivity(intent);
            }
        });
    }

    public void getRawda() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Rawda.this);
                    result_reg_face = http.SEND_get_Courses("http://62.212.88.104/dal/API.asmx/getdata_Kindergarten");
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
                                center_name[i] = (String) JO.get("kindergarten_name");
                                center_id[i] = (int) JO.get("kindergarten_id");
                                center_address[i] = (String) JO.get("address");
                                namelist.add(center_name[i]);
                                address.add(center_address[i]);
                            }
                            MyCustomerAdapterCenters myCustomerAdapterCenters= new MyCustomerAdapterCenters(namelist,address,center_id,Rawda.this,"rawda");
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
