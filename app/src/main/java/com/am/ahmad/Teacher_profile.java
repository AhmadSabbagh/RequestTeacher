package com.am.ahmad;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.am.ahmad.Basics_class.AttolSharedPreference;
import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterTeacherList;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class Teacher_profile extends AppCompatActivity {
ImageView Status,Profile;
int teacher_id;
TextView desc,price,priceAll,name;
String result_reg_teacher,result_reg_face1,result_reg_face2;
    TableView<String[]> tableView;
    String[][] spaceProbes;
    static String[] spaceProbeHeaders={"تحميل","اسم","PDF"};
    double  longitude,latitudes;
    String [] files;
    int tableChose;
    int flag;
    String formattedDate;
 String myDate,result_reg_face3;
    java.util.Calendar myCalendar;
     DatePickerDialog.OnDateSetListener date;
    private SimpleDateFormat dateFormatter;
    String  phone_number;
    int s;
    Button regBu;
boolean state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        Status=(ImageView)findViewById(R.id.status_te);
        Profile=(ImageView)findViewById(R.id.te_pic);
       regBu = (Button)findViewById(R.id.regBuInteacher) ;
        name=(TextView)findViewById(R.id.te_name);

        desc=(TextView)findViewById(R.id.desc_te);
        price=(TextView)findViewById(R.id.price_te);
        priceAll=(TextView)findViewById(R.id.price_te_all);
AttolSharedPreference attolSharedPreference= new AttolSharedPreference(this);
phone_number=attolSharedPreference.getKey("p_phone");

        tableView = (TableView<String[]>) findViewById(R.id.tableView);
        myCalendar = java.util.Calendar.getInstance();
        getCustomerRequestStatus();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(java.util.Calendar.YEAR, year);
                myCalendar.set(java.util.Calendar.MONTH, month);
                myCalendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            };


            //SET PROP
        tableView.setHeaderBackgroundColor(Color.parseColor("#161AE5"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,spaceProbeHeaders));
        tableView.setColumnCount(3);

        // Status.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.presence_online));
       // Status.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.presence_invisible));
        Intent intent=getIntent();
        teacher_id=intent.getIntExtra("teacher_id",0);
        s=intent.getIntExtra("s",3);
        if(s==0)
        {
          regBu.setVisibility(View.INVISIBLE);
        }
        else if(s==1)
        {
            regBu.setVisibility(View.VISIBLE);
        }
        else
        {

        }
        getTeacherInfo();
        getFiles();

        tableView.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(final int rowIndex, Object clickedData) {
               // Toast.makeText(Teacher_profile.this, ((String[])clickedData)[1], Toast.LENGTH_SHORT).show();
                tableChose=rowIndex;

                AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_profile.this);
                a_builder.setMessage(" هل تريد تنزيل الملف ؟ ")
                        .setCancelable(false)
                        .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(files[rowIndex]));
                                startActivity(browserIntent);
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("شكرا :) ");
                alert.show();

            }
        });

    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        myDate=sdf.format((myCalendar.getTime()));
        if(state) {
            getAppointment();
        }
        else
        {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_profile.this);
            a_builder.setMessage(" لا يمكنك الطلب لحين الرد عليك من قبل المعلم ")
                    .setCancelable(false)

                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("شكرا :) ");
            alert.show();
        }
       // fromDateEtxt.setText(sdf.format(myCalendar.getTime()));
    }
    public void getTeacherInfo() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_profile.this);
                    result_reg_teacher = http.SEND_get_teacherInfo("http://62.212.88.104/dal/API.asmx/getdata_infoteacher_selected?",
                            teacher_id  );//here same fun of majority
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
                    if (result_reg_teacher.contains("teacher_name")) {
                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject JO = new JSONObject(result_reg_teacher);
                            String  experience,image_url;




                          String  teacher_name;
                             double  lesson_price;
                            double  season_price;

                          boolean     state;





                                teacher_name = (String) JO.get("teacher_name");
                                lesson_price = (double) JO.get("lesson_price");
                                season_price = (double) JO.get("season_price");
                                experience = (String) JO.get("experience");
                                image_url = "http://62.212.88.104/picture/"+(String) JO.get("image_url"); // add link
                                latitudes = (Double) JO.get("latitudes");
                                longitude = (Double) JO.get("longitude");
                                state = (Boolean) JO.get("state");
                                if(state)
                                {
                                     Status.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.presence_online));
                                }
                                else
                                {
                                    Status.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.presence_invisible));

                                }
                            Picasso.with(Teacher_profile.this).load(image_url).into(Profile);
                                name.setText(teacher_name);
                                price.setText(String.valueOf(lesson_price));
                               priceAll.setText(String.valueOf(season_price));
                               desc.setText(experience);







                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void getFiles() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_profile.this);
                    result_reg_face1 = http.SEND_get_teacherInfo("http://62.212.88.104/dal/API.asmx/getdata_file?",
                            teacher_id  );//here same fun of majority
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
                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray JA ;
                            JA=new JSONArray(result_reg_face1);
                            files  =new String[JA.length()];
                            spaceProbes = new String [JA.length()][spaceProbeHeaders.length];






                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);
                                files[i] = "http://62.212.88.104/file/"+(String) JO.get("name");
                                spaceProbes [i][0]="تنزيل";

                                spaceProbes [i][1]=(String) JO.get("name");

                                spaceProbes [i][2]="PDF";




                            }
                            tableView.setDataAdapter(new SimpleTableDataAdapter(Teacher_profile.this, spaceProbes));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void regAllSeason(View view) {
        flag=2;
        myDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());
            if(state) {
                getAppointment();
            }
            else
            {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_profile.this);
                a_builder.setMessage(" لا يمكنك الطلب لحين الرد عليك من قبل المعلم ")
                        .setCancelable(false)

                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("شكرا :) ");
                alert.show();
            }


    }
    public void regWithDate(View view) {
        flag=3;
        new DatePickerDialog(Teacher_profile.this, date, myCalendar
                .get(java.util.Calendar.YEAR), myCalendar.get(java.util.Calendar.MONTH),
                myCalendar.get(java.util.Calendar.DAY_OF_MONTH)).show();


    }
    public void regNow(View view) {
        flag=1;
         myDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());

        if(state) {
            getAppointment();
        }
        else
        {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_profile.this);
            a_builder.setMessage(" لا يمكنك الطلب لحين الرد عليك من قبل المعلم ")
                    .setCancelable(false)

                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("شكرا :) ");
            alert.show();
        }

    }
    public void getAppointment() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {


                    HttpRequestSender http = new HttpRequestSender(Teacher_profile.this);
                    result_reg_face2 = http.SEND_get_appointment("http://62.212.88.104/dal/API.asmx/add_appointment?",
                            flag,teacher_id,   myDate,phone_number);//here same fun of majority
                    Log.d("String", result_reg_face2);
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
                if(result_reg_face2!=null) {
                    if (result_reg_face2.contains("succ")) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_profile.this);
                        a_builder.setMessage("شكرا لطلب موعد سيتم التواصل معك قريبا  ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("شكرا :) ");
                        alert.show();


                    }
                    else if(result_reg_face2.contains("لم يوافق بعد على الموعد السابق"))

                    {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_profile.this);
                        a_builder.setMessage("لم يوافق المدرس على الموعد بعد ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("عفوا :) ");
                        alert.show();

                    }
                    else
                    {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_profile.this);
                        a_builder.setMessage("حاول لاحقا")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("عفوا :) ");
                        alert.show();
                    }



                }
                else
                {
                    Toast.makeText(getApplicationContext(),"حدث خطأ",Toast.LENGTH_LONG).show();
                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    private static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for(int i=0;i<number.length();i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    public void getCustomerRequestStatus() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {


                    HttpRequestSender http = new HttpRequestSender(Teacher_profile.this);
                    result_reg_face3 = http.SEND_get_News("http://62.212.88.104/dal/API.asmx/get_student_request_status?"
                          );//here same fun of majority
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
                    if (result_reg_face3.contains("Can")) {

                 state=true;

                    }
                    else if(result_reg_face3.contains("Not"))

                    {
state=false;
                    }
                    else
                    {

                    }



                }
                else
                {
                    Toast.makeText(getApplicationContext(),"حدث خطأ",Toast.LENGTH_LONG).show();
                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

}
