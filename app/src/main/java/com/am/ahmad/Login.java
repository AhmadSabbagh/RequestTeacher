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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        offers=(ImageView)findViewById(R.id.special_offer_bu);
         teacher =(ImageView)findViewById(R.id.teacher_reg_bu) ;
        student =(ImageView)findViewById(R.id.student_reg_bu) ;
        deirect =(Button)findViewById(R.id.directSign) ;
        View view=getWindow().getDecorView().getRootView();

        ViewCompat.setLayoutDirection(view,3);

        teacher.setEnabled(false);
        student.setEnabled(false);

        deirect.setEnabled(false);


        getOffers();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void getOffers() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Login.this);
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

                                url[i] = "http://37.48.72.231/picture/"+(String) JO.get("url");
                                offer_id[i] = (int) JO.get("offer_id");



                            }
                            if(!url[0].equals("http://37.48.72.231/picture/")) {
                                Picasso.with(Login.this).load(url[0]).into(offers);
                            }
                            else
                            {
                                offers.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.special_offer));
                            }
                            Login.this.mHandler = new Handler();
                            Login.this.mHandler.postDelayed(m_Runnable,5000);
                            teacher.setEnabled(true);
                            student.setEnabled(true);

                            deirect.setEnabled(true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



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
            if(!url[count].equals("http://37.48.72.231/picture/")) {
                Picasso.with(Login.this).load(url[count]).into(offers);
            }
            else
            {
                offers.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.special_offer));

            }
            count++;



            Login.this.mHandler.postDelayed(m_Runnable, 10000);
        }

    };
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void student_reg(View view) {
        startActivity(new Intent(Login.this,Student_Reg.class));
       // Login.this.mHandler.removeCallbacks(m_Runnable);
        //finish();

    }

    public void teached_reg(View view) {
        startActivity(new Intent(Login.this,Teacher_Reg.class));
     //   Login.this.mHandler.removeCallbacks(m_Runnable);
        //finish();

    }

    public void SignInDirect(View view) {
        startActivity(new Intent(Login.this,Sign_In.class));
    //    Login.this.mHandler.removeCallbacks(m_Runnable);
      //  finish();


    }
}
