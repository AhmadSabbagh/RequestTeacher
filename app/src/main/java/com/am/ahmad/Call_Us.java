package com.am.ahmad;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Call_Us extends AppCompatActivity {
    TextView text;
    double lat,lon;
    String  result_reg_face;
    String Facebook;
    String Google1;
    String Youtube1;
    String Text;
    String Twitter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__us);

        text=(TextView)findViewById(R.id.text);

        getInfo();
    }

    public void getLoc(View view) {
        Intent intent =new Intent(Call_Us.this,LocationMap.class);
        intent.putExtra("lat",lat);
        intent.putExtra("lon",lat);
        startActivity(intent);

    }

    public void getInfo() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Call_Us.this);
                    result_reg_face = http.getOffersPics1("http://62.212.88.104/dal/API.asmx/getdata_call_us?");
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
                    if (result_reg_face.contains("facebook")) {
                        try {
                            JSONObject JO=new JSONObject(result_reg_face);








                            Facebook = (String) JO.get("facebook");
                            Twitter = (String) JO.get("twiter");
                            Youtube1 = (String) JO.get("youtube");
                            Google1 = (String) JO.get("google");
                            Text = (String) JO.get("text");
                            lat = (double) JO.get("latitude");
                            lon = (double) JO.get("longitude");


                            text.setText(Text);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void getGoogle(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(Google1));
        startActivity(i);
    }

    public void getYou(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(Youtube1));
        startActivity(i);
    }

    public void gettwitter(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(Twitter));
        startActivity(i);
    }


    public void getFace(View view) {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(Facebook));
        startActivity(i);
    }
}
