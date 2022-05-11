package com.am.ahmad;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class NewLogin_English extends AppCompatActivity
         {
    ImageView offers,teacher,student;
    String result_reg_face;
    String [] url;
    int [] offer_id;
    private Handler mHandler;
    JSONArray JA;
    int count=0;
    Button deirect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lan = Locale.getDefault().getDisplayLanguage();

            setContentView(R.layout.new_login_arabic);



      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        offers=(ImageView)findViewById(R.id.special_offer_bu);
        teacher =(ImageView)findViewById(R.id.teacher_reg_bu) ;
        student =(ImageView)findViewById(R.id.student_reg_bu) ;
        deirect =(Button)findViewById(R.id.directSign) ;


        teacher.setEnabled(false);
        student.setEnabled(false);

        deirect.setEnabled(false);


        getOffers();


        offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count>JA.length()-1)
                {
                    count=0;
                }
                if (url != null && !url[count].equals("")) {
                    if (!url[count].equals("http://62.212.88.104/picture/")) {
                        Picasso.with(NewLogin_English .this).load(url[count]).into(offers);
                    } else {
                        offers.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.special_offer));

                    }



                    // Teacher_page.this.mHandler.postDelayed(m_Runnable, 10000);
                }
            }
        });

    }
    public void getOffers() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(NewLogin_English.this);
                    result_reg_face = http.getOffersPics1("http://62.212.88.104/dal/API.asmx/getdata_special_offers?");
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
                    if (result_reg_face.contains("url")) {

                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        //  sendRequest2(user.getPhoneNumber().toString());
                        try {
                            JA=new JSONArray(result_reg_face);
                            url= new String[JA.length()];
                            offer_id= new int[JA.length()];
                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                url[i] = "http://62.212.88.104/picture/"+(String) JO.get("url");
                                offer_id[i] = (int) JO.get("offer_id");



                            }
                            if(!url[0].equals("http://62.212.88.104/picture/")) {
                                Picasso.with(NewLogin_English.this).load(url[0]).into(offers);
                            }
                            else
                            {
                                offers.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.special_offer));
                            }
                            NewLogin_English.this.mHandler = new Handler();
                            NewLogin_English.this.mHandler.postDelayed(m_Runnable,5000);
                            teacher.setEnabled(true);
                            student.setEnabled(true);

                            deirect.setEnabled(true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                    else
                    {
                        teacher.setEnabled(true);
                        student.setEnabled(true);
                        deirect.setEnabled(true);
                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            if(count>JA.length()-1)
            {
                count=0;
            }
            if(!url[count].equals("http://62.212.88.104/picture/")) {
                Picasso.with(NewLogin_English.this).load(url[count]).into(offers);
            }
            else
            {
                offers.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.special_offer));

            }
            count++;



            NewLogin_English.this.mHandler.postDelayed(m_Runnable, 10000);
        }

    };
  /*  @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/





    public void student_reg(View view) {
        startActivity(new Intent(NewLogin_English.this,Student_Reg.class));
       // NewLogin_English.this.mHandler.removeCallbacks(m_Runnable);
     //   FirebaseMessaging.getInstance().subscribeToTopic("student");

       // finish();
    }

    public void teached_reg(View view) {
        startActivity(new Intent(NewLogin_English.this,Teacher_Reg.class));
     //   NewLogin_English.this.mHandler.removeCallbacks(m_Runnable);
      //  FirebaseMessaging.getInstance().unsubscribeFromTopic("student");
///finish();


    }

    public void SignInDirect(View view) {
        startActivity(new Intent(NewLogin_English.this,Sign_In.class));
       // NewLogin_English.this.mHandler.removeCallbacks(m_Runnable);
//finish();

    }
}
