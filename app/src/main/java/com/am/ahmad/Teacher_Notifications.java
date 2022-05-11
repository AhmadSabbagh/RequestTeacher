package com.am.ahmad;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.am.ahmad.Basics_class.AttolSharedPreference;
import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterNotificationTeacher;
import com.am.ahmad.Basics_class.MyCustomerAdapterTeacherList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.leolin.shortcutbadger.ShortcutBadger;

public class Teacher_Notifications extends AppCompatActivity {
ListView listView;
String result_reg_teacher;
    String [] date,type,phone,name;
    int [] app_id,stu_id;
    ArrayList<String> student_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__notifications);
        listView=(ListView)findViewById(R.id.notif_list);
        AttolSharedPreference attolSharedPreference= new AttolSharedPreference(this);
        getTeachers();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Teacher_Notifications.this,Teacher_Agree_Or_Dis.class);
                intent.putExtra("app_id",app_id[position]);
                intent.putExtra("name",name[position]);
                intent.putExtra("date",date[position]);
                intent.putExtra("type",type[position]);
                intent.putExtra("phone",phone[position]);

                startActivity(intent);
            }
        });
        ShortcutBadger.applyCount(this, 0); //for 1.1.4+
        attolSharedPreference.setBudge1("budge",0);

    }

    public void getTeachers() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_Notifications.this);
                    result_reg_teacher = http.getStatus1("http://62.212.88.104/dal/API.asmx/getdata_all_appointment?"
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
                            stu_id=new int [JA.length()];
                            name= new String[JA.length()];
                            date = new String[JA.length()];
                            type= new String[JA.length()];
                            phone= new String[JA.length()];
                            student_name=new ArrayList<String>();


                            for (int i = 0; i < JA.length(); i++) {
                                JSONObject JO = (JSONObject) JA.get(i);

                                name[i] = (String) JO.get("student_name");
                                date[i] = (String) JO.get("date");
                                type[i] = (String) JO.get("type");
                                phone[i] = (String) JO.get("phone_father");

                                app_id[i] = (int) JO.get("appointment_id"); // add link
                                stu_id[i] = (int) JO.get("teacher_id");
                                student_name.add(name[i]);




                            }

                            MyCustomerAdapterNotificationTeacher myCustomerAdapterNotificationTeacher= new MyCustomerAdapterNotificationTeacher(
                                    student_name,type,date,Teacher_Notifications.this,stu_id
                            );
                            listView.setAdapter(myCustomerAdapterNotificationTeacher);

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
