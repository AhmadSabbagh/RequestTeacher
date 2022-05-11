package com.am.ahmad;

import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Teacher_Status extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
Switch aSwitch;
String result_reg_face;
boolean state;
String change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__status);
        aSwitch = (Switch)findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(this);
getTeacherState();


        }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (aSwitch.isChecked()) {
          //  Toast.makeText(this, aSwitch.getTextOn(), Toast.LENGTH_LONG).show();
            change="true";
            changeStatus(change);
        }
        else {
           // Toast.makeText(this, aSwitch.getTextOff(), Toast.LENGTH_LONG).show();
            change="false";
            changeStatus(change);

        }
    }
    public void getTeacherState() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_Status.this);
                    result_reg_face = http.getStatus("http://62.212.88.104/dal/API.asmx/getdata_state");
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
                    if (result_reg_face.contains("state")) {

                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        //  sendRequest2(user.getPhoneNumber().toString());
                        try {




                                JSONObject JO = new JSONObject(result_reg_face);

                                state= (boolean) JO.get("state");
                                if(state)
                                {
                                    aSwitch.setChecked(true);
                                }
                                else
                                {
                                    aSwitch.setChecked(false);

                                }





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void changeStatus(final String change) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_Status.this);
                    result_reg_face = http.setStatus("http://62.212.88.104/dal/API.asmx/update_teacher_state",change);
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


                      //  Toast.makeText(Teacher_Status.this, "Changed succ", Toast.LENGTH_LONG).show();


                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}



