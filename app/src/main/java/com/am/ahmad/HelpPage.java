package com.am.ahmad;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterHelped;
import com.am.ahmad.Basics_class.MyCustomerAdapterNotificationTeacher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HelpPage extends AppCompatActivity {
TextView textView;
String result_reg_face,result_reg_face1;
    ProgressDialog x;
    String me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
    textView=(TextView)findViewById(R.id.textHelp) ;
         x = new ProgressDialog(this);
         Intent intent=getIntent();
         me=intent.getStringExtra("me");
getAnswer();
show();

    }
/*
    public void send(View view) {
        if(helpt.getText().toString().equals("") ) {

            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("من فضلك أملا كافة الحقول")
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
            sendyourHelp();
            show();
        }
    }
    public void sendyourHelp() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(HelpPage.this);
                    result_reg_face = http.SEND_help("http://37.48.72.231/dal/API.asmx/help",helpt.getText().toString());
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
                    if (result_reg_face.contains("بنجاح")) {
                        //  x.cancel();

                        Toast.makeText(getBaseContext(), "شكرا سيتم الاجابة على سؤالك", Toast.LENGTH_SHORT).show();
                        x.cancel();
                        finish();


                    }

                    else
                    {
                        x.cancel();
                        Toast.makeText(getBaseContext(), "تاكد من صحة البيانات", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    x.cancel();
                    Toast.makeText(getBaseContext(), "تاكد من صحة البيانات", Toast.LENGTH_SHORT).show();
                }


                super.onPostExecute(aVoid);

            }
        }.execute();

    }
*/
    public void getAnswer() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(HelpPage.this);
                    if(me.equals("2")) {
                        result_reg_face1 = http.get_help("http://62.212.88.104/dal/API.asmx/getdata_help");
                    }
                    else
                    {
                        result_reg_face1 = http.get_help("http://62.212.88.104/dal/API.asmx/getdata_help_teacher");

                    }
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
                    if (result_reg_face1.contains("help")) {
                        //  x.cancel();
                        try {
                            JSONObject JA = new JSONObject(result_reg_face1);
//[{"id":2,"helped":"Ø§Ø±ÙŠØ¯ Ø§Ù„Ù…Ø³Ø§Ø¹Ø¯Ø© Ø§Ø¨ ÙŠÙ…Ù†","re_help":"-Ù„Ø¨ÙŠÙƒ ÙˆØ³Ø¹Ø¯ÙŠÙƒ Ø§Ø¨Ø§ ØµØ¨Ø§Øº"}]


                           String textT;
                             textT=JA.getString("help");
                             textView.setText(textT);
                              x.cancel();




                        } catch (JSONException e) {
                            x.cancel();

                            e.printStackTrace();
                        }



                    }

                    else
                    {
                        x.cancel();
                        Toast.makeText(getBaseContext(), "المساعدة غير حاهزة حاليا", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    x.cancel();
                    Toast.makeText(getBaseContext(), "حدث خطا", Toast.LENGTH_SHORT).show();
                }


                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    private void show() {

        x.setMessage("جاري رفع سؤالك...");
        x.setCancelable(false);
        x.show();
    }

}
