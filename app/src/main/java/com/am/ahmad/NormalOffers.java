package com.am.ahmad;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.am.ahmad.Basics_class.AttolSharedPreference;
import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterJobSearch;
import com.am.ahmad.Basics_class.MyCustomerAdapterNormalOffer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.leolin.shortcutbadger.ShortcutBadger;

public class NormalOffers extends AppCompatActivity {
String result_reg_face,result_reg_face1;
ListView offerList;
    int [] offer_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_offers);
        offerList= (ListView)findViewById(R.id.Normal_list) ;
        AttolSharedPreference attolSharedPreference = new AttolSharedPreference(this);

        getNormalOffer();
        offerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              getDetails(offer_id[position])  ;
            }
        });

        ShortcutBadger.applyCount(this, 0); //for 1.1.4+
        attolSharedPreference.setBudge1("budge",0);

    }
    public void getNormalOffer() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(NormalOffers.this);
                    result_reg_face = http.getOffersPics1("http://62.212.88.104/dal/API.asmx/getdata_offers");
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
                    if (result_reg_face.contains("offer_id")) {


                        try {
                            JSONArray JA;
                            JA=new JSONArray(result_reg_face);
                            String [] url = new String [JA.length()];
                            String [] desc = new String [JA.length()];
                             offer_id = new int [JA.length()];
                            ArrayList<String> offerPic = new ArrayList<String>();
                            ArrayList<String> offerDesc = new ArrayList<String>();
                            String [] video = new String [JA.length()];


                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                               // url[i] = "http://37.48.72.231/picture/"+(String) JO.get("url");
                                offer_id[i] = (int) JO.get("offer_id");
                                desc[i] = (String) JO.get("offer_name");
                               // video[i]="http://37.48.72.231/vidio/"+JO.getString("vidio");
                               offerPic.add(desc[i]);
                                offerDesc.add(desc[i]);
                            }
                            MyCustomerAdapterJobSearch myCustomerAdapterNormalOffer= new MyCustomerAdapterJobSearch(offerDesc,offerDesc,offer_id
                            ,NormalOffers.this);
                            offerList.setAdapter(myCustomerAdapterNormalOffer);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void getDetails(final  int id) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(NormalOffers.this);
                    result_reg_face1 = http.getOfferDetails("http://62.212.88.104/dal/API.asmx/getdata_offers_details",id);
                    Log.d("String", result_reg_face1);
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
                if(result_reg_face1!=null) {
                    if (result_reg_face1.contains("offer_name")) {


                        try {

                            String  url ;
                            String desc ;
                            String  offer_name ;

                            String  video;



                                JSONObject JO = new JSONObject(result_reg_face1);

                                url = "http://62.212.88.104/picture/"+(String) JO.get("picture");
                                offer_name = (String) JO.get("offer_name");
                                desc = (String) JO.get("descr");
                                 video="http://62.212.88.104/vidio/"+JO.getString("vidio");
                            Intent intent=new Intent(NormalOffers.this,NormalOfferDetails.class);
                            intent .putExtra("pic",url);
                            intent.putExtra("video",video);
                            intent .putExtra("offer_name",offer_name);
                            intent.putExtra("desc",desc);
startActivity(intent);
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
