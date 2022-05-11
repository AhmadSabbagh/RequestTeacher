package com.am.ahmad;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.am.ahmad.Basics_class.AttolSharedPreference;
import com.am.ahmad.Basics_class.HttpRequestSender;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import me.leolin.shortcutbadger.ShortcutBadger;

public class student_page extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener {
    TextView myImageViewText, myImageViewTexttwo;
    ImageView  next, prev, offers1;
    String result_reg_face, result_reg_face1, result_reg_face3, result_reg_face4;
    String[] url, descr, url1, descr1;
    int[] offer_id;
    private Handler mHandler;
    private Handler mHandler1;
    String student_id;
    JSONArray JA, JA1;
    int count = 0;
    int count1 = 0;
    ProgressDialog x;
    AttolSharedPreference attolSharedPreference;
    private LocationManager locationManager;
    private LocationListener listener;
    Location myLocation;
    double mylat = 0;
    double myLon = 0;
    int Code = 0;
    int ApiCode = 0;
    Advertisment advArr[];
    SliderLayout offers;
    HashMap<String,String> url_maps = new HashMap<String, String>();
     TextSliderView textSliderView;
    TextView more;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_student_page);
        offers = (SliderLayout) findViewById (R.id.special_offer_bu);

////// slider two//////
        offers1 = (ImageView) findViewById (R.id.advert);

        more = (TextView) findViewById (R.id.more);

        x = new ProgressDialog (this);
        show ( );
        getOffers ( );
        getOffers1 ( );



        offers.setPresetTransformer(SliderLayout.Transformer.Accordion);
        offers.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        offers.setCustomAnimation(new DescriptionAnimation());
        offers.setDuration(4000);

        offers.addOnPageChangeListener(this);

        PackageManager manager = this.getPackageManager ( );
        PackageInfo info = null;
        attolSharedPreference = new AttolSharedPreference (this);

        try {
            info = manager.getPackageInfo (this.getPackageName ( ), 0);
            Code = info.versionCode;
            getVersionCode ( );
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace ( );
        }
       /* Toast.makeText(this,
                "PackageName = " + info.packageName + "\nVersionCode = "
                        + info.versionCode + "\nVersionName = "
                        + info.versionName + "\nPermissions = " + info.permissions, Toast.LENGTH_SHORT).show();*/

        ShortcutBadger.applyCount(this, 0); //for 1.1.4+
        attolSharedPreference.setBudge1("budge",0);

        locationManager = (LocationManager) getSystemService (LOCATION_SERVICE);

        if (locationManager.isProviderEnabled (LocationManager.GPS_PROVIDER)) {
            //Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        } else {

            showGPSDisabledAlertToUser ( );

        }

        student_id = attolSharedPreference.getKey ("id");

        attolSharedPreference.setKey ("me", "student");

        FirebaseMessaging.getInstance ( ).subscribeToTopic (student_id + "_" + "student");

        FirebaseMessaging.getInstance ( ).subscribeToTopic ("student");


        if (ActivityCompat.checkSelfPermission (student_page.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (student_page.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions (student_page.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {


            LocationManager lm = (LocationManager) getSystemService (Context.LOCATION_SERVICE);
            myLocation = lm.getLastKnownLocation (LocationManager.PASSIVE_PROVIDER);

            if (myLocation == null) {
                Criteria criteria = new Criteria ( );
                criteria.setAccuracy (Criteria.ACCURACY_COARSE);
                String provider = lm.getBestProvider (criteria, true);
                myLocation = lm.getLastKnownLocation (provider);
            }

            if (myLocation != null) {
                mylat = myLocation.getLatitude ( );
                myLon = myLocation.getLongitude ( );

            }
        }

        // this.mHandler = new Handler();
        this.mHandler1 = new Handler ( );
        //  this.mHandler.postDelayed(m_Runnable,5000);
        this.mHandler1.postDelayed (m_Runnable1, 5000);
        listener = new LocationListener ( ) {
            @Override
            public void onLocationChanged(Location location) {

                mylat = location.getLatitude ( );
                myLon = location.getLongitude ( );
                updateLocation (mylat, myLon);


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

//                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(i);
            }

        };
//
//        next.setOnClickListener (new View.OnClickListener ( ) {
//            @Override
//            public void onClick(View v) {
//                count++;
//                if (count > JA.length ( ) - 1) {
//                    count = 0;
//                }
//                if (url != null && !url[count].equals ("")) {
//                    if (!url[count].equals ("http://62.212.88.104/picture/")) {
//                        Picasso.with (student_page.this).load (url[count]).into (offers);
//                    } else {
//                        offers.setImageDrawable (ContextCompat.getDrawable (getApplicationContext ( ), R.drawable.special_offer));
//
//                    }
//
//
//                    // Teacher_page.this.mHandler.postDelayed(m_Runnable, 10000);
//                }
//            }
//        });




//        prev.setOnClickListener (new View.OnClickListener ( ) {
//            @Override
//            public void onClick(View v) {
//
//                count--;
//                if (count < 0) {
//                    count = JA.length ( ) - 1;
//                }
//
//                if (url != null && !url[count].equals ("")) {
//                    if (!url[count].equals ("http://62.212.88.104/picture/")) {
//                        Picasso.with (student_page.this).load (url[count]).into (offers);
//                    } else {
//                        offers.setImageDrawable (ContextCompat.getDrawable (getApplicationContext ( ), R.drawable.special_offer));
//
//                    }
//
//
//                    // Teacher_page.this.mHandler.postDelayed(m_Runnable, 10000);
//                }
//            }
//        });
    }


//    private final Runnable m_Runnable = new Runnable ( ) {
//        public void run()
//
//        {
//            count++;
//
//            if (count > JA.length ( ) - 1) {
//                count = 0;
//            }
//            if (url != null && !url[count].equals ("")) {
//                if (!url[count].equals ("http://62.212.88.104/picture/")) {
//                    Picasso.with (student_page.this).load (url[count]).into (offers);
//                } else {
//                    offers.setImageDrawable (ContextCompat.getDrawable (getApplicationContext ( ), R.drawable.special_offer));
//
//                }
//
//
//                student_page.this.mHandler.postDelayed (m_Runnable, 10000);
//            }
//        }
//
//    };

    private void show() {

        x.setMessage ("loading...");
        x.setCancelable (false);
        x.show ( );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater ( );
        inflater.inflate (R.menu.student_menu, menu); //your file name
        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ( );

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings12) {
            // show();
            attolSharedPreference.setKey ("id", "0");
            attolSharedPreference.setKey ("me", "0");
            FirebaseMessaging.getInstance ( ).unsubscribeFromTopic ("student");

            FirebaseMessaging.getInstance ( ).unsubscribeFromTopic (student_id + "_" + "student");
//            for(int i =1 ; i<=100;i++)
//            {
//                FirebaseMessaging.getInstance().unsubscribeFromTopic(i+"_"+"student");
//                FirebaseMessaging.getInstance().unsubscribeFromTopic(i+"_"+"teacher");
//
//
//            }
            //  x.cancel();


            startActivity (new Intent (student_page.this, Sign_In.class));
            finish ( );
            return true;
        }
        if (id == R.id.action_settings1) {
            startActivity (new Intent (student_page.this, Student_Requstes.class));
            return true;
        }
        if (id == R.id.callus) {
            startActivity (new Intent (student_page.this, Call_Us.class));
            return true;
        }
        if (id == R.id.libraries) {
            // startActivity(new Intent(student_page.this,Other.class));
            Intent intent = new Intent (student_page.this, Libraries.class);
            intent.putExtra ("x", 1);
            startActivity (intent);
            return true;
        }
        if (id == R.id.all) {


            startActivity(new Intent(student_page.this, All_noti.class));

            return true;
        }
        if (id == R.id.summerclub) {
            //startActivity(new Intent(student_page.this,Other.class));
            Intent intent = new Intent (student_page.this, Libraries.class);
            intent.putExtra ("x", 2);
            startActivity (intent);
            return true;
        }
        if (id == R.id.sport) {
            //  startActivity(new Intent(student_page.this,Other.class));
            Intent intent = new Intent (student_page.this, Libraries.class);
            intent.putExtra ("x", 3);
            startActivity (intent);
            return true;
        }
        if (id == R.id.quran) {
            //  startActivity(new Intent(student_page.this,Other.class));
            Intent intent = new Intent (student_page.this, Libraries.class);
            intent.putExtra ("x", 4);
            startActivity (intent);
            return true;
        }
        if (id == R.id.help) {
            //  startActivity(new Intent(student_page.this,Other.class));
            Intent intent = new Intent (student_page.this, HelpPage.class);
            intent.putExtra ("me", "2");
            startActivity (intent);
            return true;
        }
        if (id == R.id.change) {
            //  startActivity(new Intent(student_page.this,Other.class));
            Intent intent = new Intent (student_page.this, Change_pass.class);
            intent.putExtra ("context", 1);

            startActivity (intent);
            return true;
        }
        return super.onOptionsItemSelected (item);

    }

    public void getOffers() {

        new AsyncTask<Void, Void, Void> ( ) {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender (student_page.this);
                    result_reg_face = http.getOffersPics1 ("http://62.212.88.104/dal/API.asmx/getdata_special_offers?");
                    Log.d ("String", result_reg_face);
                } catch (Exception e) {
                    e.printStackTrace ( );
                }
                return null;
            }

            @Override
            protected void onPreExecute() {


                super.onPreExecute ( );
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (result_reg_face != null) {
                    if (result_reg_face.contains ("url")) {
                        try {
                            x.cancel();
                            JA = new JSONArray (result_reg_face);
                            url = new String[JA.length ( )];
                            descr = new String[JA.length ( )];

                            offer_id = new int[JA.length ( )];
                            for (int i = 0; i < JA.length ( ); i++) {

                                JSONObject JO = (JSONObject) JA.get (i);

                                url[i] = "http://62.212.88.104/picture/" + (String) JO.get ("url");
                                descr[i] = (String) JO.get ("descr");

                                offer_id[i] = (int) JO.get ("offer_id");
                                url_maps.put(""+i, url[i]);

                            }
                            for(String name : url_maps.keySet()){
                                 textSliderView = new TextSliderView(student_page.this);
                                // initialize a SliderLayout
                                textSliderView
                                        .image(url_maps.get(name))

                                        .setScaleType(BaseSliderView.ScaleType.Fit)

                                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                    @Override
                                    public void onSliderClick(BaseSliderView slider) {
                                        Intent intent = new Intent (student_page.this, SpecialOffersDetails.class);
                                        intent.putExtra ("img", url[count]);
                                        intent.putExtra ("txt", descr[count]);
                                        startActivity (intent);
//

                                    }
                                });


                                //add your extra information
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle()
                                        .putString("extra",name);

                                offers.addSlider(textSliderView);
                            }


//                            if (!url[0].equals ("http://62.212.88.104/picture/")) {
//                                Picasso.with (student_page.this).load (url[0]).into (offers);
//                            } else {
//                                offers.setImageDrawable (ContextCompat.getDrawable (getApplicationContext ( ), R.drawable.special_offer));
//                            }
//                            student_page.this.mHandler = new Handler ( );
//                            student_page.this.mHandler.postDelayed (m_Runnable, 5000);
//                            student_page.this.mHandler1 = new Handler ( );
//                            //  this.mHandler.postDelayed(m_Runnable,5000);
//                            student_page.this.mHandler1.postDelayed (m_Runnable1, 5000);
//                            x.cancel ( );

                        } catch (JSONException e) {
                            e.printStackTrace ( );
                            x.cancel ( );
                        }
                    } else {
                        x.cancel ( );
                    }

                }

                super.onPostExecute (aVoid);

            }
        }.execute ( );

    }

    public void getVersionCode() {

        new AsyncTask<Void, Void, Void> ( ) {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender (student_page.this);
                    result_reg_face3 = http.getcode ("http://62.212.88.104/dal/API.asmx/getdata_virsion?");
                    Log.d ("String", result_reg_face3);
                } catch (Exception e) {
                    e.printStackTrace ( );
                }
                return null;
            }

            @Override
            protected void onPreExecute() {


                super.onPreExecute ( );
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (result_reg_face3 != null) {
                    if (result_reg_face3.contains ("virsion")) {
                        try {
                            JSONObject jsonObject = new JSONObject (result_reg_face3);
                            ApiCode = (int) jsonObject.get ("virsion");
                            if (ApiCode > Code && Code > 0) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder (student_page.this);
                                alertDialogBuilder.setMessage ("يتوفر اصدار احدث في المتجر هل تريد التحديث الآن؟ ")
                                        .setCancelable (false)
                                        .setPositiveButton ("نعم",
                                                new DialogInterface.OnClickListener ( ) {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent i = new Intent (Intent.ACTION_VIEW);
                                                        i.setData (Uri.parse ("https://play.google.com/store/apps/details?id=com.am.ahmad"));
                                                        startActivity (i);
                                                    }
                                                });
                                alertDialogBuilder.setNegativeButton ("لاحقا",
                                        new DialogInterface.OnClickListener ( ) {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel ( );

                                            }
                                        });
                                AlertDialog alert = alertDialogBuilder.create ( );
                                alert.show ( );
                            }


                        } catch (JSONException e) {
                            e.printStackTrace ( );
                            x.cancel ( );
                        }
                    } else {
                        x.cancel ( );
                    }

                }

                super.onPostExecute (aVoid);

            }
        }.execute ( );

    }


    public void updateLocation(final double myLat, final double myLon) {

        new AsyncTask<Void, Void, Void> ( ) {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender (student_page.this);
                    result_reg_face1 = http.sendLocation ("http://62.212.88.104/dal/API.asmx/update_student_location", myLat, myLon);
                    Log.d ("String", result_reg_face1);
                } catch (Exception e) {
                    e.printStackTrace ( );
                }
                return null;
            }

            @Override
            protected void onPreExecute() {


                super.onPreExecute ( );
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (result_reg_face1 != null) {
                    if (result_reg_face1.contains ("url")) {

                        try {
                            JA = new JSONArray (result_reg_face1);
                            url = new String[JA.length ( )];
                            offer_id = new int[JA.length ( )];
                            for (int i = 0; i < JA.length ( ); i++) {

                                JSONObject JO = (JSONObject) JA.get (i);

                                url[i] = (String) JO.get ("url");
                                offer_id[i] = (int) JO.get ("offer_id");


                            }
                            if (url != null && !url[0].equals ("")) {
                              //  Picasso.with (student_page.this).load (url[0]).into (offers);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace ( );
                        }


                    }

                }

                super.onPostExecute (aVoid);

            }
        }.execute ( );

    }

    public void searchForTeacher(View view) {
        Intent intent = new Intent (student_page.this, Teacher_list.class);
        intent.putExtra ("lat", mylat);
        intent.putExtra ("lon", myLon);
        startActivity (intent);
        // this.mHandler.removeCallbacks(m_Runnable);

    }

    public void aboutUs(View view) {
        startActivity (new Intent (student_page.this, about_us.class));
        //  this.mHandler.removeCallbacks(m_Runnable);
    }

    public void courses_center_get(View view) {
        startActivity (new Intent (student_page.this, Courses_Center_List_View.class));
        //  this.mHandler.removeCallbacks(m_Runnable);

    }

    public void get_schools(View view) {
        startActivity (new Intent (student_page.this, Schools_List_View.class));
        //  this.mHandler.removeCallbacks(m_Runnable);


    }

    public void get_univers(View view) {
        startActivity (new Intent (student_page.this, Uni_List_View.class));
        //  this.mHandler.removeCallbacks(m_Runnable);


    }

    public void get_data_news(View view) {
        startActivity (new Intent (student_page.this, News_List_View.class));
        ///   this.mHandler.removeCallbacks(m_Runnable);


    }

    private final Runnable m_Runnable1 = new Runnable ( ) {
        public void run()

        {
            //   checkStatus();
            //  checkRequest();
            configure_button ( );
            //  Toast.makeText(First_Page.this,"in runnable",Toast.LENGTH_SHORT).show();

            student_page.this.mHandler1.postDelayed (m_Runnable1, 10000);
        }

    };

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            count1++;
            if(count1>JA1.length()-1)
            {
                count1=0;
            }
            if (url1 != null && !url1[count1].equals("")) {
                if (!url[count1].equals("http://62.212.88.104/picture/")) {
                    Picasso.with(student_page.this).load(url1[count1]).into(offers1);
                } else {
                    offers1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.special_offer));

                }



                student_page.this.mHandler.postDelayed(m_Runnable, 5000);
            }
        }

    };
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button ( );
                break;
            default:
                break;
        }
    }

    void configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission (this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions (new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.


        //noinspection MissingPermission
        locationManager.requestLocationUpdates ("gps", 5000, 0, listener);


    }

    public void getOffersNotmal(View view) {
        startActivity (new Intent (student_page.this, NormalOffers.class));
        // this.mHandler.removeCallbacks(m_Runnable);

    }

    public void getRawda(View view) {
        startActivity (new Intent (student_page.this, Rawda.class));
        //  this.mHandler.removeCallbacks(m_Runnable);

    }

    public void getlesson(View view) {
        startActivity (new Intent (student_page.this, LessonCenter.class));
        //    this.mHandler.removeCallbacks(m_Runnable);

    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder (this);
        alertDialogBuilder.setMessage ("الموقع حاليا غير فعال من فضلك قم بتفعيله لنتمكن من تحديد موقعك ")
                .setCancelable (false)
                .setPositiveButton ("شغل الموقع",
                        new DialogInterface.OnClickListener ( ) {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent (
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity (callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton ("لاحقا",
                new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel ( );

                    }
                });
        AlertDialog alert = alertDialogBuilder.create ( );
        alert.show ( );
    }

    public void getOffers1() {

        new AsyncTask<Void, Void, Void> ( ) {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender (student_page.this);
                    result_reg_face4 = http.getOffersPics1 ("http://62.212.88.104/dal/API.asmx/getdata_advert?");
                    Log.d ("String", result_reg_face4);
                } catch (Exception e) {
                    e.printStackTrace ( );
                }
                return null;
            }

            @Override
            protected void onPreExecute() {


                super.onPreExecute ( );
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (result_reg_face4 != null) {
                    if (result_reg_face4.contains ("url")) {
                        try {
                            JA1 = new JSONArray (result_reg_face4);
                            url1 = new String[JA.length ( )];
                            descr1 = new String[JA.length ( )];


                            for (int i = 0; i < JA1.length ( ); i++) {

                                JSONObject JO = (JSONObject) JA1.get (i);

                                url1[i] = "http://62.212.88.104/picture/" + (String) JO.get ("picture");
                                descr1[i] = (String) JO.get ("url");


                            }
                            Picasso.with (student_page.this).load (url1[0]).into (offers1);

                            student_page.this.mHandler = new Handler ( );
                            student_page.this.mHandler.postDelayed (m_Runnable, 5000);
//                            student_page.this.mHandler1 = new Handler ( );
//                            //  this.mHandler.postDelayed(m_Runnable,5000);
//                            student_page.this.mHandler1.postDelayed (m_Runnable1, 5000);
//                            x.cancel ( );


                        } catch (JSONException e) {
                            e.printStackTrace ( );
                            x.cancel ( );
                        }
                    } else {
                        x.cancel ( );
                    }

                }

                super.onPostExecute (aVoid);

            }
        }.execute ( );







        offers1.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + descr1[count1]));
                startActivity(browserIntent);

            }
        });


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        Toast.makeText(this,position,Toast.LENGTH_LONG).show();
//
//            String x= textSliderView.des
//            count=Integer.parseInt(x);
        Set<String> x= url_maps.keySet();
        List<String> list = new ArrayList<String>(x);

        //count=x[position];
//        String []x1= (String[]) x.toArray();
        count= Integer.parseInt(list.get(position));



    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


//    public void goToUrl(View view) {
//      // Advertisment adv = advArr[0];
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + descr1[0]));
//        startActivity(browserIntent);
//    }
}




