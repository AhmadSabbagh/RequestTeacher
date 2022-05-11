package com.am.ahmad;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterTeacherJobs;
import com.am.ahmad.Basics_class.MyCustomerAdapterTeacherNOWJobs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Now extends AppCompatActivity {
ListView listView;
String result_reg_teacher,result_reg_teacher1,result_reg_teacher2;
    String result_reg_face;
    int [] app_id ,teacher_id,student_id;
    String [] name , date,type ;
    ArrayList <String >student_name;
    int [] star;
    boolean [] flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now);
        listView=(ListView)findViewById(R.id.list);
        getJob();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                    Intent intent = new Intent(Teacher_Job.this, Teacher_Evaluation.class);
//                    intent.putExtra("student_id", student_id[position]);
//                    intent.putExtra("app_id", app_id[position]);
//
//                    startActivity(intent);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Now.this);
                View mView = getLayoutInflater().inflate(R.layout.start_end_dialog, null);
           ;

                Button mLogin = (Button) mView.findViewById(R.id.start_end_bu);
                if(flag[position])
                {
                    mLogin.setText("إنهاء الدرس");
                }
                else
                {
                    mLogin.setText("ابدأ الدرس");

                }
                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(flag[position])
                        {
                            //flag is true this lesson is started
                            finishLesson(app_id[position]);
                        }
                        else
                        {
                            boolean state=false;
                           for (int i=0;i<flag.length;i++)
                           {
                               if(flag[i])
                               {
                                   state=true;
                               }
                           }
                           if(state)
                           {
                               Toast.makeText(getBaseContext(), "لقد بدأت درس بالفعل", Toast.LENGTH_SHORT).show();

                           }
                           else
                           {
                               StartLesson(app_id[position]);
                           }


                        }


                    }
                });
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });
    }

    public void getJob() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Now.this);
                    result_reg_teacher = http.SEND_get_job("http://http://62.212.88.104/dal/API.asmx/appointment?"
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
                            student_id=new int [JA.length()];
                            name= new String[JA.length()];
                            date = new String[JA.length()];
                            type= new String[JA.length()];
                            star=new int[JA.length()];
                            flag=new boolean[JA.length()];

                            student_name=new ArrayList<String>();


                            for (int i = 0; i < JA.length(); i++) {
                                JSONObject JO = (JSONObject) JA.get(i);

                                name[i] = (String) JO.get("student_name");
                                student_id[i] = (int) JO.get("student_id");
                                date[i] = (String) JO.get("date");
                                type[i] = (String) JO.get("type");
                                app_id[i] = (int) JO.get("appointment_id"); // add link
                                star[i] = (int) JO.get("star");
                                flag[i]= (boolean) JO.get("flag");
                                student_name.add(name[i]);



                            }

                            MyCustomerAdapterTeacherNOWJobs myCustomerAdapterTeacherNOWJobs= new MyCustomerAdapterTeacherNOWJobs(
                                    student_name,type,date,Now.this,flag
                            );
                            listView.setAdapter(myCustomerAdapterTeacherNOWJobs);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void finishLesson(final int appp_id) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Now.this);
                    result_reg_teacher1 = http.SEND_end_lesson("http://http://62.212.88.104/dal/API.asmx/end_lesson?"
                    ,appp_id);//here same fun of majority
                    Log.d("String", result_reg_teacher1);
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
                if(result_reg_teacher1!=null) {
                    if (result_reg_teacher1.contains("نهاية")) {
                        //  x.cancel();
                         Toast.makeText(getBaseContext(), "تم إنهاء الدرس شكرا", Toast.LENGTH_SHORT).show();
                         finish();
                         startActivity(new Intent(Now.this,Now.class));



                    }


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void StartLesson(final int appp_id) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Now.this);
                    result_reg_teacher2 = http.SEND_end_lesson("http://62.212.88.104/dal/API.asmx/Start_lesson?"
                            ,appp_id);//here same fun of majority
                    Log.d("String", result_reg_teacher2);
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
                if(result_reg_teacher2!=null) {
                    if (result_reg_teacher2.contains("بداية")) {
                        //  x.cancel();
                          Toast.makeText(getBaseContext(), "تم بدء الدرس ", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(Now.this,Now.class));


                    }


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }


}
