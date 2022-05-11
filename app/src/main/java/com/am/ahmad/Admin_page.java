package com.am.ahmad;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.am.ahmad.Basics_class.AttolSharedPreference;
import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterNotificationForAdmin;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.leolin.shortcutbadger.ShortcutBadger;

public class Admin_page extends AppCompatActivity {
ListView list ;
String result_reg_teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        list=(ListView)findViewById(R.id.adminList);
        getSupportActionBar().setTitle("اطلب استاذ"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // fo
        AttolSharedPreference attolSharedPreference=new AttolSharedPreference(this);
        attolSharedPreference.setKey("me","admin");
      FirebaseMessaging.getInstance().subscribeToTopic("admin");
        FirebaseMessaging.getInstance().unsubscribeFromTopic("teacher");
       FirebaseMessaging.getInstance().unsubscribeFromTopic("student");
//        FirebaseMessaging.getInstance().unsubscribeFromTopic("admin");
//        FirebaseMessaging.getInstance().unsubscribeFromTopic("student");
//        FirebaseMessaging.getInstance().unsubscribeFromTopic("teacher");
        getnotifications();
        ShortcutBadger.applyCount(this, 0); //for 1.1.4+
        attolSharedPreference.setBudge1("budge",0);

        //admin list noti
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AttolSharedPreference attolSharedPreference=new AttolSharedPreference(this);
            attolSharedPreference.setKey("id","0");
            attolSharedPreference.setKey("me","0");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("admin");
            startActivity(new Intent(Admin_page.this, Sign_In.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    public void getnotifications() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Admin_page.this);
                    result_reg_teacher = http.SEND_get_admin_noti("http://62.212.88.104/dal/API.asmx/getdata_admin_notfic");
                              //here same fun of majority
                    Log.d("String", result_reg_teacher);
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
                if(result_reg_teacher!=null) {
                    if (result_reg_teacher.contains("teacher_id")) {
                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray JA = new JSONArray(result_reg_teacher);
                            String [] name,experience,picture;
                            int [] teacher_id;



                            name= new String[JA.length()];
                            teacher_id=new int [JA.length()];
                            experience= new String[JA.length()];
                            picture= new String[JA.length()];
                            ArrayList<String> teacet_namee= new ArrayList<String >();
                            ArrayList<String> teacherPic= new ArrayList<String >();
                            ArrayList<String> TeacherExp= new ArrayList<String >();



                            for (int i = 0; i < JA.length(); i++) {
                                JSONObject JO = (JSONObject) JA.get(i);

                                name[i] = (String) JO.get("name");
                                teacher_id[i] = (int) JO.get("teacher_id");
                                experience[i] = (String) JO.get("experience");
                                picture[i] = "http://37.48.72.231/picture/"+(String) JO.get("picture");

                                teacet_namee.add(name[i]);
                                teacherPic.add(picture[i]);
                                TeacherExp.add(experience[i]);

                            }
                            MyCustomerAdapterNotificationForAdmin myCustomerAdapterNotificationForAdmin
                                    = new MyCustomerAdapterNotificationForAdmin(teacet_namee,teacherPic,TeacherExp,teacher_id,Admin_page.this);

                            list.setAdapter(myCustomerAdapterNotificationForAdmin);


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
