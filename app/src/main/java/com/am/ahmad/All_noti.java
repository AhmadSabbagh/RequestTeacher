package com.am.ahmad;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterAllNotification;
import com.am.ahmad.Basics_class.MyCustomerAdapterJobSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class All_noti extends AppCompatActivity {
ListView listView;
String result_reg_face4;
ProgressDialog x;
    String [] nameN ;
    String [] descN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_noti);
        listView=(ListView)findViewById(R.id.list);
        x=new ProgressDialog(this);
        getAllnoti();
        show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(All_noti.this,All_noti_details.class);
                intent.putExtra("name",nameN[position]);
                intent.putExtra("desc",descN[position]);
                startActivity(intent);
            }
        });
    }
    public  void show()
    {
        x.show();
        x.setCancelable(false);
        x.setMessage("جاري التحميل");


    }
    public void getAllnoti() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(All_noti.this);
                    result_reg_face4 = http.getAll("http://62.212.88.104/dal/API.asmx/getdata_notfication?");
                    Log.d("String", result_reg_face4);
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
                if(result_reg_face4!=null) {
                    if (result_reg_face4.contains("id")) {

x.cancel();
                        try {
                            JSONArray JA;
                            JA=new JSONArray(result_reg_face4);
                            nameN = new String [JA.length()];
                             descN = new String [JA.length()];

                            int [] offer_id = new int [JA.length()];
                            ArrayList<String> name = new ArrayList<String>();
                            ArrayList<String> offerDesc = new ArrayList<String>();
                            String [] video = new String [JA.length()];


                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                // url[i] = "http://37.48.72.231/picture/"+(String) JO.get("url");
                                nameN[i] = (String) JO.get("offer_name");
                                descN[i] = (String) JO.get("offer_descr");
                                offer_id[i] = (int) JO.get("id");

                                // video[i]="http://37.48.72.231/vidio/"+JO.getString("vidio");
                                name.add(nameN[i]);
                                offerDesc.add(descN[i]);
                            }
                            MyCustomerAdapterAllNotification myCustomerAdapterNormalOffer= new MyCustomerAdapterAllNotification(name,offerDesc,offer_id
                                    ,All_noti.this);
                            listView.setAdapter(myCustomerAdapterNormalOffer);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        x.cancel();
                        Toast.makeText(All_noti.this,"لا توجد اشعارات حاليا"  ,Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    x.cancel();
                    Toast.makeText(All_noti.this,"حصل خطأ"  ,Toast.LENGTH_LONG).show();

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

}
