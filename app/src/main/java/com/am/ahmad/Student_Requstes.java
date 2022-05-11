package com.am.ahmad;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.am.ahmad.Basics_class.AttolSharedPreference;
import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterNotificationTeacher;
import com.am.ahmad.Basics_class.MyCustomerAdapterRequestStudent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.leolin.shortcutbadger.ShortcutBadger;

public class Student_Requstes extends AppCompatActivity {
ListView listView;
String result_reg_teacher;
int [] app_id ,teacher_id;
String [] name , date,type ;
boolean[] flag;
    ArrayList<String> teacher_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__requstes);
        listView=(ListView)findViewById(R.id.listId);
        getReq();

        AttolSharedPreference attolSharedPreference= new AttolSharedPreference(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(flag[position]) {
                    Intent intent = new Intent(Student_Requstes.this, Student_Evaluation.class);
                    intent.putExtra("teacher_id", teacher_id[position]);
                    intent.putExtra("app_id", app_id[position]);

                    startActivity(intent);
                }
                else
                {

                }
            }
        });
        ShortcutBadger.applyCount(this, 0); //for 1.1.4+
        attolSharedPreference.setBudge1("budge",0);

    }
    public void getReq() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Student_Requstes.this);
                    result_reg_teacher = http.SEND_get_req("http://62.212.88.104/dal/API.asmx/getdata_all_appointment_to_student?"
                    );//here same fun of majority
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
                    if (result_reg_teacher.contains("appointment_id")) {
                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();

                        try {
                            JSONArray JA = new JSONArray(result_reg_teacher);
                            app_id=new int [JA.length()];
                            teacher_id=new int [JA.length()];
                            name= new String[JA.length()];
                            date = new String[JA.length()];
                            type= new String[JA.length()];
                            flag=new boolean[JA.length()];
                            teacher_name=new ArrayList<String>();


                            for (int i = 0; i < JA.length(); i++) {
                                JSONObject JO = (JSONObject) JA.get(i);

                                name[i] = (String) JO.get("teacher_name");
                                teacher_id[i] = (int) JO.get("teacher_id");
                                date[i] = (String) JO.get("date");
                                type[i] = (String) JO.get("type");
                                app_id[i] = (int) JO.get("appointment_id"); // add link
                                flag[i] = (boolean) JO.get("flag");
                                teacher_name.add(name[i]);



                            }

                            MyCustomerAdapterRequestStudent myCustomerAdapterRequestStudent= new MyCustomerAdapterRequestStudent(
                                    teacher_name,type,date,Student_Requstes.this,flag
                            );
                            listView.setAdapter(myCustomerAdapterRequestStudent);

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
