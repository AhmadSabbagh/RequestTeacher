package com.am.ahmad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.am.ahmad.Basics_class.AttolSharedPreference;
import com.am.ahmad.Basics_class.HttpRequestSender;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPass extends AppCompatActivity {
String type="";
EditText usename;
String result_reg_face;
CountryCodePicker sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        usename=(EditText)findViewById(R.id.usernameF);
        sign=(CountryCodePicker) findViewById(R.id.spinner5) ;
        sign.setAutoDetectedCountry(true);

    }

    public void clickB(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.student:
                if (checked)
                {
                    type="student";
                }
                break;
            case R.id.teacher:
                if (checked)
                {
                    type="teacher";

                }
                break;

        }
    }

    public void send(View view) {
        if(usename.getText().toString().equals("") ||type.equals("")
                ) {

            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("من فضلك ادخل كافة البيانات")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("خطأ :( ");
            alert.show();
        }
        else
        {
           // show();
            sign_in();
        }
    }

    public void sign_in() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(ForgetPass.this);


                        if (type.equals("student")) {
                            result_reg_face = http.SEND_Forget("http://62.212.88.104/dal/API.asmx/forget_password_student?", sign.getFullNumberWithPlus()+usename.getText().toString()
                            );
                        } else if (type.equals("teacher")) {
                            result_reg_face = http.SEND_Forget("http://62.212.88.104/dal/API.asmx/forget_password_teacher?", sign.getFullNumberWithPlus()+usename.getText().toString());

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
                    if (result_reg_face.contains("message")) {
                        try {
                            JSONObject jsonObject = new JSONObject(result_reg_face);
                            int id = (int) jsonObject.get("new_pass");
                            if(id==0)
                            {
                                Toast.makeText(ForgetPass.this, "رقم هاتف غير صحيح", Toast.LENGTH_LONG).show();

                            }
                            else {

                                Toast.makeText(ForgetPass.this, "سيتم التواصل معك من قبل الادارة لاسترداد حسابك", Toast.LENGTH_LONG).show();
                            finish();
                            }
                        }
                        catch (JSONException e) {
                            Toast.makeText(ForgetPass.this, "حصل خطا", Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        }


                    }
                    else
                    {
                        Toast.makeText(ForgetPass.this, "حصل خطا", Toast.LENGTH_LONG).show();

                    }
                }


                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
