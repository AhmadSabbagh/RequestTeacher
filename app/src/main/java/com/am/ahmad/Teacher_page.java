package com.am.ahmad;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import com.am.ahmad.Basics_class.FcmMessage;
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
import java.util.Locale;
import java.util.Set;

import me.leolin.shortcutbadger.ShortcutBadger;

public class Teacher_page extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener {
ImageView next,prev,offers1,nextbar,prevbar;
String result_reg_face,result_reg_face1,result_reg_face3,result_reg_face4;
int count=0;
int count1=0;
JSONArray JA,JA1;
LocationManager locationManager;
LocationListener listener;
String []url,url1 , descr,descr1;
    private Handler mHandler,mHandler1;
int [] offer_id;
String teacher_id;
    ProgressDialog x;
    int Code=0;
    int ApiCode=0;
    SliderLayout offers;
    HashMap<String,String> url_maps = new HashMap<String, String>();
    TextSliderView textSliderView;
    TextView more;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lan = Locale.getDefault().getDisplayLanguage();
//        if (lan.equals("العربية"))
//        {
//            setContentView(R.layout.new_teacher_page_arabic);
//
//        }
//
          setContentView(R.layout.activity_teacher_page);
        x = new ProgressDialog(this);
        offers=(SliderLayout)findViewById(R.id.special_offer_bu);

        //////slider 2///////
        offers1=(ImageView)findViewById(R.id.special_offer_bu1);
        more = (TextView) findViewById (R.id.more);


        show();
        getOffers();
        getOffers1();


        offers.setPresetTransformer(SliderLayout.Transformer.Accordion);
        offers.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        offers.setCustomAnimation(new DescriptionAnimation());
        offers.setDuration(4000);

        offers.addOnPageChangeListener(this);
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
            Code=info.versionCode;
            getVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//        Toast.makeText(this,
//                "PackageName = " + info.packageName + "\nVersionCode = "
//                        + info.versionCode + "\nVersionName = "
//                        + info.versionName + "\nPermissions = " + info.permissions, Toast.LENGTH_SHORT).show();

        AttolSharedPreference attolSharedPreference= new AttolSharedPreference(this);
       teacher_id=attolSharedPreference.getKey("id");
        attolSharedPreference.setKey("me","teacher");


        FirebaseMessaging.getInstance().subscribeToTopic("teacher");
        FirebaseMessaging.getInstance().subscribeToTopic(teacher_id+"_"+"teacher");
      //  FirebaseMessaging.getInstance().unsubscribeFromTopic("29"+"_"+"student_id");

        FirebaseMessaging.getInstance().subscribeToTopic(Integer.parseInt(teacher_id)+"_"+"teacher_id");


        ShortcutBadger.applyCount(this, 0); //for 1.1.4+
        attolSharedPreference.setBudge1("budge",0);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                double mylat=location.getLatitude();
                double myLon=location.getLongitude();
                updateLocation(mylat,myLon);



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



        offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Teacher_page.this,SpecialOffersDetails.class);
                intent.putExtra("img",url[count1]);
                intent.putExtra("txt",descr[count1]);
                startActivity(intent);

            }
        });

    }
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
                    Picasso.with(Teacher_page.this).load(url1[count1]).into(offers1);
                } else {
                    offers1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.special_offer));

                }



                Teacher_page.this.mHandler.postDelayed(m_Runnable, 5000);
            }
        }

    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.teacher_menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings12) {
            AttolSharedPreference attolSharedPreference=new AttolSharedPreference(this);
            attolSharedPreference.setKey("id","0");
            attolSharedPreference.setKey("me","0");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("teacher");
            FirebaseMessaging.getInstance().unsubscribeFromTopic(teacher_id+"_"+"teacher");
            FirebaseMessaging.getInstance().unsubscribeFromTopic(teacher_id+"_"+"teacher_id");

            startActivity(new Intent(Teacher_page.this, Sign_In.class));
            finish();
            return true;
        }
        if (id == R.id.action_settings1) {


            startActivity(new Intent(Teacher_page.this, Teacher_Job.class));

            return true;
        }

        if (id == R.id.all) {


            startActivity(new Intent(Teacher_page.this, All_noti.class));

            return true;
        }

        if (id == R.id.action_settings123) {

            Intent intent =new Intent(Teacher_page.this, Teacher_Add_Marahel.class);
            intent.putExtra("context",1);
            startActivity(intent);

            return true;
        }
        if (id == R.id.action_settings1234) {

            Intent intent =new Intent(Teacher_page.this, Now.class);
            startActivity(intent);

            return true;
        }
        if (id == R.id.help) {
            //  startActivity(new Intent(student_page.this,Other.class));
            Intent intent = new Intent(Teacher_page.this,HelpPage.class);
           intent.putExtra("me","1");
            startActivity(intent);
            return true;
        }
        if (id == R.id.callus) {
            startActivity(new Intent(Teacher_page.this,Call_Us.class));
            return true;
        }
        if (id == R.id.change) {
            //  startActivity(new Intent(student_page.this,Other.class));
            Intent intent = new Intent(Teacher_page.this,Change_pass.class);
intent.putExtra("context",2);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    public void getVersionCode() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_page.this);
                    result_reg_face3 = http.getcode("http://62.212.88.104/dal/API.asmx/getdata_virsion?");
                    Log.d("String", result_reg_face3);
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
                if(result_reg_face3!=null) {
                    if (result_reg_face3.contains("virsion")) {
                        try {
                            JSONObject jsonObject = new JSONObject(result_reg_face3);
                            ApiCode= (int) jsonObject.get("virsion");
                            if(ApiCode>Code && Code>0)
                            {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Teacher_page.this);
                                alertDialogBuilder.setMessage("يتوفر اصدار احدث في المتجر هل تريد التحديث الآن؟ ")
                                        .setCancelable(false)
                                        .setPositiveButton("نعم",
                                                new DialogInterface.OnClickListener(){
                                                    public void onClick(DialogInterface dialog, int id){
                                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                                        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.am.ahmad"));
                                                        startActivity(i);
                                                    }
                                                });
                                alertDialogBuilder.setNegativeButton("لاحقا",
                                        new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int id){
                                                dialog.cancel();

                                            }
                                        });
                                AlertDialog alert = alertDialogBuilder.create();
                                alert.show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            x.cancel();
                        }
                    }
                    else
                    {
                        x.cancel();
                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void updateLocation(final double myLat, final double myLon) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_page.this);
                    result_reg_face1 = http.sendLocation2("http://62.212.88.104/dal/API.asmx/update_teacher_location",myLat,myLon);
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
                    if (result_reg_face1.contains("url")) {





                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void getOffers() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_page.this);
                    result_reg_face = http.getOffersPics1("http://62.212.88.104/dal/API.asmx/getdata_special_offers?");
///                      Log.d("String", result_reg_face);
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
                            descr= new String[JA.length()];

                            offer_id= new int[JA.length()];
                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                url[i] = "http://62.212.88.104/picture/"+(String) JO.get("url");
                                offer_id[i] = (int) JO.get("offer_id");
                                descr[i] =(String) JO.get("descr");
                                url_maps.put(""+i, url[i]);

                            }
                            for(String name : url_maps.keySet()){
                                textSliderView = new TextSliderView(Teacher_page.this);
                                // initialize a SliderLayout
                                textSliderView
                                        .image(url_maps.get(name))

                                        .setScaleType(BaseSliderView.ScaleType.Fit)

                                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                            @Override
                                            public void onSliderClick(BaseSliderView slider) {
                                                Intent intent = new Intent (Teacher_page.this, SpecialOffersDetails.class);
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
                            x.cancel();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            x.cancel();
                        }



                    }
                    else
                    {
                        x.cancel();
                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    private final Runnable m_Runnable1 = new Runnable()
    {
        public void run()

        {
            //   checkStatus();
            //  checkRequest();
            configure_button();
            //  Toast.makeText(First_Page.this,"in runnable",Toast.LENGTH_SHORT).show();

            Teacher_page.this.mHandler1.postDelayed(m_Runnable1, 5000);
        }

    };
    private void show() {

        x.setMessage("Loging...");
        x.setCancelable(false);
        x.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.


        //noinspection MissingPermission
        locationManager.requestLocationUpdates("gps", 5000, 0, listener);


    }

    public void aboutUs(View view) {
        startActivity(new Intent(Teacher_page.this,about_us.class));
     //   this.mHandler.removeCallbacks(m_Runnable);
    }

    public void AddFile(View view) {
        startActivity(new Intent(Teacher_page.this,Teacher_upload_files.class));
   //     this.mHandler.removeCallbacks(m_Runnable);
    }

    public void EditProfile(View view) {
        startActivity(new Intent(Teacher_page.this,EditProfile.class));
   //     this.mHandler.removeCallbacks(m_Runnable);
    }

    public void StatusChange(View view) {
        startActivity(new Intent(Teacher_page.this,Teacher_Status.class));
      //  this.mHandler.removeCallbacks(m_Runnable);
    }

    public void getJob(View view) {
        startActivity(new Intent(Teacher_page.this,Job_Search.class));
    //    this.mHandler.removeCallbacks(m_Runnable);
    }

    public void getNormalOffers(View view) {
        startActivity(new Intent(Teacher_page.this,NormalOffers.class));
    //    this.mHandler.removeCallbacks(m_Runnable);
    }

    public void getNoti(View view) {
        startActivity(new Intent(Teacher_page.this,Teacher_Notifications.class));
     //   this.mHandler.removeCallbacks(m_Runnable);
    }
    public void getOffers1() {

        new AsyncTask<Void, Void, Void> ( ) {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender (Teacher_page.this);
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
                            Picasso.with (Teacher_page.this).load (url1[0]).into (offers1);

                            Teacher_page.this.mHandler = new Handler ( );
                            Teacher_page.this.mHandler.postDelayed (m_Runnable, 5000);
                            Teacher_page.this.mHandler1 = new Handler ( );
                            //  this.mHandler.postDelayed(m_Runnable,5000);
                            Teacher_page.this.mHandler1.postDelayed (m_Runnable1, 5000);
                            x.cancel ( );


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
        Set<String> x= url_maps.keySet();
        List<String> list = new ArrayList<String>(x);

        //count=x[position];
//        String []x1= (String[]) x.toArray();
        count= Integer.parseInt(list.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
