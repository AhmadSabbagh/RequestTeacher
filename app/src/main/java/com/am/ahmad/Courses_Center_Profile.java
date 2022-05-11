package com.am.ahmad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class Courses_Center_Profile extends AppCompatActivity {
EditText name,phone;
     int center_id;
     String result_reg_face1,result_reg_face;
     String [] teacher_name,course,start_date,end_date,start,end,day;
    int [] course_id;
    int tableChose=-1;
    String[][] spaceProbes;
    static String[] spaceProbeHeaders={"يوم","وقت","التاريخ","مادة","معلم"};
    TableView<String[]> tableView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses__center__profile);
        name=(EditText)findViewById(R.id.nameR);
        phone=(EditText)findViewById(R.id.phoneR);
        tableView = (TableView<String[]>) findViewById(R.id.tableView);

        //SET PROP
        tableView.setHeaderBackgroundColor(Color.parseColor("#161AE5"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,spaceProbeHeaders));
        tableView.setColumnCount(5);
        Intent intent=getIntent();
        center_id=intent.getIntExtra("center_id",0);
        getTeachers_Table();
    tableView.addDataClickListener(new TableDataClickListener() {
        @Override
        public void onDataClicked(int rowIndex, Object clickedData) {
           // Toast.makeText(Courses_Center_Profile.this, ((String[])clickedData)[1], Toast.LENGTH_SHORT).show();
            AlertDialog.Builder a_builder = new AlertDialog.Builder(Courses_Center_Profile.this);
            a_builder.setMessage("  وقت الدرس في : "+((String[])clickedData)[1] +"\n" +"  التاريخ : "+ start_date[rowIndex] +"\n"+"                                         To "+end_date[rowIndex] )
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("شكرا ");
            alert.show();

            tableChose=rowIndex;
        }
    });


    }
    public void getTeachers_Table() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Courses_Center_Profile.this);
                    result_reg_face1 = http.SEND_get_Teachers_center("http://62.212.88.104/dal/API.asmx/getdata_table_course_center?",center_id);
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
                    if (result_reg_face1.contains("name")) {

                        try {
                            JSONArray JA ;
                            JA=new JSONArray(result_reg_face1);
                            teacher_name  =new String[JA.length()];
                            day = new String[JA.length()];
                            course_id= new int[JA.length()];
                            spaceProbes = new String [JA.length()][spaceProbeHeaders.length];
                            course  =new String[JA.length()];
                            start = new String[JA.length()];
                            end = new String[JA.length()];

                            start_date = new String[JA.length()];
                            end_date = new String[JA.length()];



                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);
                                day[i] = (String) JO.get("day");
                                spaceProbes [i][0]=day[i];

                                start[i] = (String) JO.get("start_time");
                                end[i] = (String) JO.get("end_time");
                                spaceProbes [i][1]=start[i]+"To"+end[i];

                                start_date[i] = (String) JO.get("start_date");
                                end_date[i] = (String) JO.get("end_date");
                                spaceProbes [i][2]=start_date[i]+"To"+end_date[i];


                                course[i] = (String) JO.get("course");
                                spaceProbes [i][3]=course[i];



                                teacher_name[i] = (String) JO.get("teacher_name");
                                spaceProbes [i][4]=teacher_name[i];


                                course_id[i] = (int) JO.get("course_id");

                            }
                            tableView.setDataAdapter(new SimpleTableDataAdapter(Courses_Center_Profile.this, spaceProbes));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void regi(View view) {
        if(!name.getText().toString().equals("")&& !phone.getText().toString().equals("")) {
          //  register();
            if(tableChose==-1)
            {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
                a_builder.setMessage("من فضلك اختار جدول ")
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
            else   if ( phone.getText().toString().length() > 9) {
                phone.setError("رقم الهاتف يزيد عن 9 ارقام");


            }
            else
            {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
                a_builder.setMessage("    لقد قمت باختيار المادة   " + course[tableChose])
                        .setCancelable(false)
                        .setNegativeButton("الغاء",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            register();
                            }

                        });

                AlertDialog alert = a_builder.create();
                alert.setTitle("شكرا ");
                alert.show();



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

                    HttpRequestSender http = new HttpRequestSender(Courses_Center_Profile.this);
                    result_reg_face = http.SEND_rigesetINCoursesCenter("http://62.212.88.104/dal/API.asmx/rigester_student_in_coursescenter?",
                            center_id,course_id[tableChose],phone.getText().toString()
                            ,name.getText().toString());
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
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Courses_Center_Profile.this);
                        a_builder.setMessage("شكرا لتسجيلك سيتم التواصل معك قريبا ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("شكرا ");
                        alert.show();



                    }
                    else     if (result_reg_face.contains("تأكد")) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Courses_Center_Profile.this);
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
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Courses_Center_Profile.this);
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
