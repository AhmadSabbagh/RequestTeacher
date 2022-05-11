package com.am.ahmad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterLessonCenter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyView extends AppCompatActivity {
EditText name1,phone;
int table_id,center_id;
String result_reg_face;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);
        name1=(EditText)findViewById(R.id.nameReg) ;
        phone=(EditText)findViewById(R.id.phoneReg);


        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));


        Intent intent=getIntent();
         table_id =intent.getIntExtra("table_id",0);
        center_id =intent.getIntExtra("center_id",0);




        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int w = displayMetrics.widthPixels;
        int h=displayMetrics.heightPixels;
        getWindow().setLayout((int)(w*1),(int)(h*0.4));
    }

    public void reg(View view) {
        if(!name1.getText().toString().equals("")&& !phone.getText().toString().equals("")&& table_id!=0 && center_id!=0) {
            if ( phone.getText().toString().length() > 9) {
                phone.setError("رقم الهاتف يزيد عن 9 ارقام");


            }
            else {
                register();
            }
        }
        else
        {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("من فضلك حدد جميع الخانات ")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("خطأ :( ");
            alert.show();
        }
    }
    public void register() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(MyView.this);
                    result_reg_face = http.SEND_rigesetINLesson("http://62.212.88.104/dal/API.asmx/rigester_student_in_lessoncenter",center_id,table_id,phone.getText().toString()
                    ,name1.getText().toString());
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
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(MyView.this);
                        a_builder.setMessage("شكرا لتسجيلك سيتم التواصل معك قريبا ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                        finish();


                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("شكرا ");
                        alert.show();


                    }
                    else     if (result_reg_face.contains("تأكد")) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(MyView.this);
                        a_builder.setMessage("إما انك مسجل بالفعل او تأكد من صحة البيانات ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                        finish();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("عفوا ");
                        alert.show();


                    }
                    else     if (result_reg_face.contains("exists")) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(MyView.this);
                        a_builder.setMessage("انت بالفعل مسجل هنا ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                        finish();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("عفوا ");
                        alert.show();


                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
