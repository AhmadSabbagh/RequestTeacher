package com.am.ahmad;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Policy extends AppCompatActivity {

ImageView img;
TextView text;
String result_reg_face3,result_reg_face4;
ProgressDialog x;

    CheckBox agree;
    TextView agreeText;
    int teacer_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        img=(ImageView)findViewById(R.id.policy_img);
        text=(TextView) findViewById(R.id.policy_text);
        x=new ProgressDialog(this);
        Intent intent=getIntent();
        teacer_id=Integer.parseInt(intent.getStringExtra("teacher_id"));
        getPolicyText();
        show();
        agree=(CheckBox)findViewById(R.id.agree);
        agreeText=(TextView) findViewById(R.id.agreeText);
    }
    public void getPolicyText() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Policy.this);
                    result_reg_face3 = http.getcode("http://62.212.88.104/dal/API.asmx/getdata_policy?");
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
                    if (result_reg_face3.contains("text")) {


                        try {
                            String Image;
                            String Text;
                            JSONObject jsonObject = new JSONObject(result_reg_face3);
                            Text= (String ) jsonObject.get("text");
                           Image= "http://62.212.88.104/picture/"+(String) jsonObject.get("picture");
                            Picasso.with(Policy.this).load(Image).into(img);
                            text.setText(Text);
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
                else
                {
                    x.cancel();
                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public  void show()
    {
        x.show();
        x.setCancelable(false);
        x.setMessage("جاري التحميل");


    }


    public void reg(View view) {

        if(agree.isChecked()) {
             startActivity(new Intent(Policy.this,Sign_In.class));
             finish ();
            Toast.makeText(Policy.this,"سيتم التواصل معك قريبا من قبل الادارة",Toast.LENGTH_LONG).show();
        }
        else
        {
            android.app.AlertDialog.Builder a_builder = new android.app.AlertDialog.Builder(this);
            a_builder.setMessage("ان عدم موافقتك على الشروط والاحكام سيؤدي الى إنهاء عملية التسجيل ")
                    .setCancelable(false)
                    .setPositiveButton("أوافق ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                            agree.setChecked(true);
                        }
                    }).setNegativeButton("لا أوافق-إنهاء عملية التسجيل", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteAccout();
                }
            })
            ;
            android.app.AlertDialog alert = a_builder.create();
            alert.setTitle("تنبيه :( ");
            alert.show();
        }

    }

    public void deleteAccout() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Policy.this);
                    result_reg_face4 = http.deleteaccount("http://62.212.88.104/dal/API.asmx/delete_teacher?",teacer_id);
                    Log.d("String", result_reg_face4);
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
                if(result_reg_face4!=null) {
                    if (result_reg_face4.contains("succ")) {
                        android.app.AlertDialog.Builder a_builder = new android.app.AlertDialog.Builder(Policy.this);
                        a_builder.setMessage("نعتذر قد تم حذف حسابك ")
                                .setCancelable(false)
                                .setPositiveButton("الخروج", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                        finish();
                                    }
                                })
                        ;
                        android.app.AlertDialog alert = a_builder.create();
                        alert.setTitle("نعتذر :( ");
                        alert.show();

                    }
                    else
                    {
                        x.cancel();
                    }

                }
                else
                {
                    x.cancel();
                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

}

