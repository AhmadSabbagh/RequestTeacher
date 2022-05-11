package com.am.ahmad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.am.ahmad.Basics_class.AndroidFileBrowserExampleActivity;
import com.am.ahmad.Basics_class.AttolSharedPreference;
import com.am.ahmad.Basics_class.ViewPager1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Welcome extends AppCompatActivity {
    private Handler handler = new Handler();
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar3) ;
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("MM-dd-yyyy").format(cDate);
        ViewPager1 viewPager1 = new ViewPager1(this);
        int datee=cDate.getDate();
        int time=cDate.getHours();
        int time2=cDate.getMinutes();
        Date currentTime = Calendar.getInstance().getTime();
        String mydaye=fDate+"-"+time+":"+time2;
        String idT= FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().unsubscribeFromTopic("null"+"_"+"student");
        FirebaseMessaging.getInstance().unsubscribeFromTopic("null"+"_"+"student_id");
        FirebaseMessaging.getInstance().unsubscribeFromTopic("null"+"_"+"teacher");
        FirebaseMessaging.getInstance().unsubscribeFromTopic("null"+"_"+"teacher_id");
        startActivity(new Intent(Welcome.this
        , AndroidFileBrowserExampleActivity.class));
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("MainActivity: ", "Key: " + key + " Value: " + value);
              //  Toast.makeText(Welcome.this,"Key: " + key + " Value: " + value,Toast.LENGTH_LONG).show();
            }
        }



        AttolSharedPreference attolSharedPreference = new AttolSharedPreference(this);
       // attolSharedPreference.setKey("me","teacher");
      //  attolSharedPreference.setKey("id","0");
        String id = attolSharedPreference.getKey("id");
        String me = attolSharedPreference.getKey("me");
        if (id==null)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (count<5)
                    {
                        count++;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(count);
                            }
                        });
                        try {
                            Thread.sleep(500);
                        }
                        catch (InterruptedException i)
                        {

                        }
                        if(count>=5) {
                          //  startActivity(new Intent(getApplicationContext(),NewLogin_English.class));
                            finish();
                        }
                    }
                }
            }).start();

        }
        else if (id.equals("0"))
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (count<5)
                    {
                        count++;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(count);
                            }
                        });
                        try {
                            Thread.sleep(500);
                        }
                        catch (InterruptedException i)
                        {

                        }
                        if(count>=5) {
                         //   startActivity(new Intent(getApplicationContext(),NewLogin_English.class));
                            finish();
                        }
                    }
                }
            }).start();        }
        else
        {
            if(me ==null)
            {

            }
            else if(me.equals("0"))
            {
//                startActivity(new Intent(Welcome.this, NewLogin_English.class));
//                finish();
            }
            else if(me.equals("admin"))
            {
              //  startActivity(new Intent(Welcome.this, Admin_page.class));
                finish();
            }
            else if(me.equals("teacher"))
            {
             //   startActivity(new Intent(Welcome.this, Teacher_page.class));
                finish();

            }
            else if(me.equals("student"))
            {
            //    startActivity(new Intent(Welcome.this, student_page.class));
                finish();
            }
            else {
                //TIMER

            }
        }
    }

    public void click(View view) {
       // startActivity(new Intent(Welcome.this,NewLogin_English.class));
    }
}
