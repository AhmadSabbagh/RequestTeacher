package com.am.ahmad;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Change_pass extends AppCompatActivity {
EditText old,newP,conP;
String result_reg_face;
int context;
ProgressDialog x;
boolean oldP=false;
    boolean NewP=false;
    boolean CNewP=false;
int oldInput,NewInput,ConInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        old=(EditText)findViewById(R.id.oldpass);
        newP=(EditText)findViewById(R.id.newpass);
        conP=(EditText)findViewById(R.id.confirmNewpass);
        x=new ProgressDialog(this);
        Intent intent=getIntent();

        context=intent.getIntExtra("context",0);

    }

    public void change(View view) {
        if(old.getText().toString().equals("") ||
                newP.getText().toString().equals("") ||conP.toString().equals("")
               ) {

            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("من فضلك أملا كافة الخانات ")
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
        else
        {
            if(newP.getText().toString().equals(conP.getText().toString())) {
                changePass();
                show();
            }
            else
            {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
                a_builder.setMessage("كلمة المرور الجديدة غير متطابقة ")
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
    }

    public void changePass() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Change_pass.this);
                    if(context==1)
                    {
                        result_reg_face = http.changePassS("http://62.212.88.104/dal/API.asmx/change_student_password?",old.getText().toString()
                        ,newP.getText().toString());

                    }
                    if(context==2) {
                        result_reg_face = http.changePassT("http://62.212.88.104/dal/API.asmx/change_teacher_password?",old.getText().toString()
                                ,newP.getText().toString());
                    }

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
                    if (result_reg_face.contains("Successfully")) {
                        x.cancel();
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Change_pass.this);
                        a_builder.setMessage("تم تغير كلمة المرور ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                            finish();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("شكرا :) ");
                        alert.show();

                    }
                    else if (result_reg_face.contains("correct"))
                    {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Change_pass.this);
                        a_builder.setMessage("كلمة المرور غير صحيحة ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                        finish();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("عفوا :) ");
                        alert.show();
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
    private void show() {

        x.setMessage("loading...");
        x.setCancelable(false);
        x.show();
    }

    public void Confirm(View view) {
        if(CNewP)
        {
            conP.setInputType(ConInput);
            CNewP=false;
        }
        else
        {   ConInput= newP.getInputType();
            conP.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            CNewP=true;

        }
    }

    public void NewPass(View view) {
        if(NewP)
        {
            newP.setInputType(NewInput);
            NewP=false;
        }
        else
        {
            NewInput= newP.getInputType();
            newP.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            NewP=true;
        }
    }

    public void oldPass(View view) {
        if(oldP)
        {
            old.setInputType(oldInput);
            oldP=false;


        }
        else
        {
           oldInput= old.getInputType();
           old.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
           oldP=true;
        }
    }
}
