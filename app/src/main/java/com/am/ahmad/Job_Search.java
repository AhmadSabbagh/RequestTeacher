package com.am.ahmad;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterJobSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Job_Search extends AppCompatActivity {
ListView jobList;
String result_Un;
    String [] name,descr,experience,picture,url1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job__search);
        jobList = (ListView)findViewById(R.id.jobList);
        jobList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(Job_Search.this,JobSerachDetails.class);
                intent.putExtra("title",name[position]);
                intent.putExtra("desc",descr[position]);
                intent.putExtra("url",url1[position]);

                startActivity(intent);

            }
        });
       getJobs();
    }
    public void getJobs() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(Job_Search.this);
                    result_Un = http.SEND_POST_jobSearch("http://62.212.88.104/dal/API.asmx/getdata_jops");
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_Un);


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
            protected void onPostExecute(Void aVoid) {//i stopped here
            if(result_Un!=null) {
                if (result_Un.contains("name")) {
                    try {
                        JSONArray JA = new JSONArray(result_Un);

                        int [] job_id ;
                        double [] longitude,latitude;


                        name= new String[JA.length()];
                        url1= new String[JA.length()];

                        job_id=new int [JA.length()];
                        descr = new String[JA.length()];
                        ArrayList<String> namelist = new ArrayList<String>();
                        ArrayList<String>desclist  = new ArrayList<String>();



                        for (int i = 0; i < JA.length(); i++) {
                            JSONObject JO = (JSONObject) JA.get(i);

                            name[i] = (String) JO.get("name");
                            job_id[i] = (int) JO.get("jop_id");
                            descr[i] = (String) JO.get("descr");
                            url1[i] = "http://62.212.88.104/picture/" + (String) JO.get ("picture");

                            namelist.add(name[i]);
                            desclist.add(descr[i]);





                        }

                        MyCustomerAdapterJobSearch adapter = new MyCustomerAdapterJobSearch(namelist, desclist,job_id,Job_Search.this,url1);
                        jobList.setAdapter(adapter);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    // Toast.makeText(context,"Error try again",Toast.LENGTH_SHORT).show();

                }
            }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
