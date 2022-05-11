package com.am.ahmad;

import android.content.DialogInterface;
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

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterRequestStudent;
import com.am.ahmad.Basics_class.MyCustomerAdapterTeacherJobs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Teacher_Job extends AppCompatActivity {
ListView listView;
    String result_reg_teacher,result_reg_face;
    int [] app_id ,teacher_id,student_id;
    String [] name , date,type ;
    int [] star;
    ArrayList <String >student_name;
    String noteString="جيد";
    String x;
     EditText note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__job);
        listView=(ListView)findViewById(R.id.list_f);
        getJob();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                    Intent intent = new Intent(Teacher_Job.this, Teacher_Evaluation.class);
//                    intent.putExtra("student_id", student_id[position]);
//                    intent.putExtra("app_id", app_id[position]);
//
//                    startActivity(intent);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Teacher_Job.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
                final RatingBar ratingBar= (RatingBar)mView.findViewById(R.id.ratingBarC) ;
               final EditText note= (EditText)mView.findViewById(R.id.noteJ) ;

                Button mLogin = (Button) mView.findViewById(R.id.rateC);
                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                              if(note.getText().toString().equals("")) {
                                  rateTeacher(noteString, student_id[position], (int) ratingBar.getRating(), app_id[position]);
                              }
                              else
                              {
                                  rateTeacher(note.getText().toString(), student_id[position], (int) ratingBar.getRating(), app_id[position]);

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

                    HttpRequestSender http = new HttpRequestSender(Teacher_Job.this);
                    result_reg_teacher = http.SEND_get_job("http://62.212.88.104/dal/API.asmx/getdata_appointment_approved?"
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
                            student_name=new ArrayList<String>();


                            for (int i = 0; i < JA.length(); i++) {
                                JSONObject JO = (JSONObject) JA.get(i);

                                name[i] = (String) JO.get("student_name");
                                student_id[i] = (int) JO.get("student_id");
                                date[i] = (String) JO.get("date");
                                type[i] = (String) JO.get("type");
                                app_id[i] = (int) JO.get("appointment_id"); // add link
                                star[i] = (int) JO.get("star");
                                student_name.add(name[i]);



                            }

                            MyCustomerAdapterTeacherJobs myCustomerAdapterTeacherJobs= new MyCustomerAdapterTeacherJobs(
                                    student_name,type,date,Teacher_Job.this,star
                            );
                            listView.setAdapter(myCustomerAdapterTeacherJobs);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void rateTeacher(final String change, final int student_id, final int rate, final int app_id ){

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_Job.this);
                    result_reg_face = http.setRate1("http://62.212.88.104/dal/API.asmx/evaluation?",change,student_id,true
                            ,rate,app_id);
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

                        android.app.AlertDialog.Builder a_builder = new android.app.AlertDialog.Builder(Teacher_Job.this);
                        a_builder.setMessage("شكرا لتقيمك ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                        finish();
                                    }
                                });
                        android.app.AlertDialog alert = a_builder.create();
                        alert.setTitle("شكرا ");
                        alert.show();


                    }
                    else
                    {
                        android.app.AlertDialog.Builder a_builder = new android.app.AlertDialog.Builder(Teacher_Job.this);
                        a_builder.setMessage("حصل خطا ما من فضلك حاول لاحقا ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                        finish();
                                    }
                                });
                        android.app.AlertDialog alert = a_builder.create();
                        alert.setTitle("نعتذر ");
                        alert.show();
                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

}
