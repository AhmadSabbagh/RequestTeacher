package com.am.ahmad;

import android.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;

public class Teacher_Agree_Or_Dis extends AppCompatActivity {
String nameS , date, type,phone,result_reg_face;
int app_id;
TextView nameT,dateT,typeT;
boolean flag;
Button callBu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__agree__or__dis);
        Intent intent =getIntent();
        nameS=intent.getStringExtra("name");
        date=intent.getStringExtra("date");
        app_id=intent.getIntExtra("app_id",0);
        type=intent.getStringExtra("type");
        phone=intent.getStringExtra("phone");
        nameT  = (TextView)findViewById(R.id.name2);
        dateT  = (TextView)findViewById(R.id.type);
        typeT  = (TextView)findViewById(R.id.date);
        callBu  = (Button) findViewById(R.id.callBU);

        callBu.setVisibility(View.INVISIBLE);

        nameT.setText(nameS);
        dateT.setText(date);
        typeT.setText(type);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {android.Manifest.permission.CALL_PHONE};

                requestPermissions(permissions, 1);

            }
        }


    }

    public void refuse(View view) {
        flag=false;
        accept();
    }
    public void call(View view) {
        Intent callIntent =new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" +phone));
        startActivity(callIntent);



    }
    public void accept(View view) {
        flag=true;
        accept();

    }

    public void accept() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_Agree_Or_Dis.this);
                    result_reg_face = http.SEND_Accept_reject("http://62.212.88.104/dal/API.asmx/OK_or_rejection_appointment?",flag,app_id);
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
                    if (result_reg_face.contains("succ")) {

                        if(flag)
                        {
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_Agree_Or_Dis.this);
                            a_builder.setMessage("تم قبول الموعد بامكانك التواصل مع الطالب")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            callBu.setVisibility(View.VISIBLE);
                                            callBu.setEnabled(true);


                                        }
                                    });
                            AlertDialog alert = a_builder.create();
                            alert.setTitle("شكرا ");
                            alert.show();
                        }
                        else
                        {
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_Agree_Or_Dis.this);
                            a_builder.setMessage("تم رفض الموعد ")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();

                                        }
                                    });
                            AlertDialog alert = a_builder.create();
                            alert.setTitle("شكرا  ");
                            alert.show();
                        }

                    }
                    else if (result_reg_face.contains("exist"))
                    {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_Agree_Or_Dis.this);
                        a_builder.setMessage("لقد قمت برفض / قبول الموعد من قبل لا يمكنك القبول او الرفض مرة أخرى ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();

                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("شكرا  ");
                        alert.show();
                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

}
