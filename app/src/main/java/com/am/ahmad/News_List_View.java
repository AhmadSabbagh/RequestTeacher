package com.am.ahmad;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class News_List_View extends AppCompatActivity {
String result_reg_face;
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__list__view);
        listView=(ListView)findViewById(R.id.News_List) ;
        getDataNews();
    }
    public void getDataNews() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(News_List_View.this);
                    result_reg_face = http.SEND_get_News("http://62.212.88.104/dal/API.asmx/getdata_news?");
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
                    if (result_reg_face.contains("subject")) {

                        try {
                            JSONArray JA ;
                            JA=new JSONArray(result_reg_face);
                           String[] subject =new String[JA.length()];
                           int [] id= new int[JA.length()];
                            ArrayList<String> subList = new ArrayList<String>();
                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);
                                subject[i] = (String) JO.get("subject");
                                id[i] = (int) JO.get("id");
                                subList.add(subject[i]);
                            }
                            MyCustomerAdapterNews myCustomerAdapterNews= new MyCustomerAdapterNews(subList,id,News_List_View.this);
                            listView.setAdapter(myCustomerAdapterNews);


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
