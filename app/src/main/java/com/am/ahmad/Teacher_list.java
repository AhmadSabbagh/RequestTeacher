package com.am.ahmad;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterTeacherList;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Teacher_list extends AppCompatActivity {
    Spinner mar , submar,classesSp,subjects;
    String result_reg_face1,result_reg_face2,result_reg_face3,result_reg_face4,result_reg_teacher;
    List<String> marahelList,SUBmarahelList,CLASSES,SUBJECTS;
    ArrayAdapter<String> spinnerArrayAdapterMarahel,spinnerArrayAdapterSUBMARAHEL,spinnerArrayAdapterCLASSES,spinnerArrayAdapterSUBJECTS;
    String []marahel,mar_name,Submar_name,Submarahel,Class_name,SubClass,Course_name,SubCourses;
    int [] mar_id,Submar_id,Class_id,Course_id;
    // index for spinners :
    int mar_index , sub_mar_index, class_mar_index,subjects_mar_index;
    ListView teacher_list;
    int [] teacher_id,star ;
    double mylat,myLon;
    Location myLocation;
    String [] name,course_name,experience,picture;
    ArrayList<String> teacher_name,courseName,teacher_exp,teacher_pic;
String result_reg_teacher1;
    double [] longitude,latitude;
int [] newStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);
        mar = (Spinner)findViewById(R.id.marID);
        submar = (Spinner)findViewById(R.id.subMarId);
        classesSp = (Spinner)findViewById(R.id.classesId);
        subjects = (Spinner)findViewById(R.id.subjects);
        Button btn = (Button) findViewById(R.id.btn);
        teacher_list=(ListView)findViewById(R.id.teachers_list) ;
        Intent intent = getIntent();
        mylat=intent.getDoubleExtra("lat",0);
        myLon=intent.getDoubleExtra("lon",0);
        getRating();
        if(mylat==0)
        {
            if (ActivityCompat.checkSelfPermission(Teacher_list.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Teacher_list.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Teacher_list.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {


                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                myLocation = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                if (myLocation == null) {
                    Criteria criteria = new Criteria();
                    criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                    String provider = lm.getBestProvider(criteria, true);
                    myLocation = lm.getLastKnownLocation(provider);
                }

                if (myLocation != null) {
                    mylat = myLocation.getLatitude();
                    myLon = myLocation.getLongitude();

                }
            }
        }

        String[] marahel = new String[]{
                "حدد المرحلة الدراسية"

        };
        String[] Submarahel = new String[]{
                "حدد المرحلة الفرعية"

        };

        String[] classes = new String[]{
                "حدد الصف"

        };

        String[] subject = new String[]{
                "حدد اسم المادة"

        };

        teacher_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Teacher_list.this,Teacher_profile.class);
                intent.putExtra("teacher_id",teacher_id[position]);
                startActivity(intent);
            }
        });
        marahelList = new ArrayList<>(Arrays.asList(marahel));

        // Initializing an ArrayAdapter
         spinnerArrayAdapterMarahel = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,marahelList);

        spinnerArrayAdapterMarahel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mar.setAdapter(spinnerArrayAdapterMarahel);

        //////////////////////////////////////////////////////////////////////////////////////
        SUBmarahelList = new ArrayList<>(Arrays.asList(Submarahel));

        // Initializing an ArrayAdapter
         spinnerArrayAdapterSUBMARAHEL = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,SUBmarahelList);

        spinnerArrayAdapterMarahel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        submar.setAdapter(spinnerArrayAdapterSUBMARAHEL);

        //////////////////////////////////////////////////////////////////////////////////////
         CLASSES = new ArrayList<>(Arrays.asList(classes));

        // Initializing an ArrayAdapter
         spinnerArrayAdapterCLASSES = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,CLASSES);

        spinnerArrayAdapterMarahel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        classesSp.setAdapter(spinnerArrayAdapterCLASSES);

        //////////////////////////////////////////////////////////////////////////////////////
         SUBJECTS = new ArrayList<>(Arrays.asList(subject));

        // Initializing an ArrayAdapter
         spinnerArrayAdapterSUBJECTS = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,SUBJECTS);

        spinnerArrayAdapterMarahel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        subjects.setAdapter(spinnerArrayAdapterSUBJECTS);

        //////////////////////////////////////////////////////////////////////////////////////
        getMarhel();
        mar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
              //  Toast.makeText(getApplicationContext(),"position : "+position,Toast.LENGTH_LONG).show();
                if(position>0) {
                    getSubMarahel(position-1);
                    mar_index=position-1;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
               // Toast.makeText(getApplicationContext(),"position : "+position,Toast.LENGTH_LONG).show();
                if(position>0) {
                    getClasses(position-1);
                    sub_mar_index=position-1;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        classesSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
               // Toast.makeText(getApplicationContext(),"position : "+position,Toast.LENGTH_LONG).show();
                if(position>0) {
                    getCourses(position-1);
                    class_mar_index=position-1;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        subjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
               // Toast.makeText(getApplicationContext(),"position : "+position,Toast.LENGTH_LONG).show();
                if(position>0) {
                   // getCourses(position-1);
                    subjects_mar_index=position-1;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    public void getMarhel() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_list.this);
                    result_reg_face1 = http.SEND_get_maj("http://62.212.88.104/dal/API.asmx/getdata_school_grade");//here same fun of majority

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
                            JSONArray JA = new JSONArray(result_reg_face1);
                            mar_name= new String[JA.length()];
                            mar_id=new int [JA.length()];
                            ArrayList<String> name = new ArrayList<String>();
                            ArrayList<Integer>id = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                mar_name[i] = (String) JO.get("name");
                                mar_id[i] = (int) JO.get("id");

                                name.add( mar_name[i]);
                                id.add(mar_id[i]);

                            }
                            marahel = new String[mar_name.length+1];


                            marahel[0]="حدد المرحلة الدراسية";
                            for (int i =0 ; i<JA.length();i++)
                            {
                                marahel[i+1]= ""+ mar_name[i];
                            }

                            marahelList = new ArrayList<>(Arrays.asList(marahel));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterMarahel = new ArrayAdapter<String>(
                                    Teacher_list.this,R.layout.support_simple_spinner_dropdown_item,marahelList);

                            spinnerArrayAdapterMarahel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            mar.setAdapter(spinnerArrayAdapterMarahel);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void getSubMarahel(final int pos) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_list.this);
                    result_reg_face2 = http.SEND_get_SubMar("http://62.212.88.104/dal/API.asmx/getdata_subschool_grade",mar_id[pos]);//here same fun of majority

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
                    if (result_reg_face2.contains("name")) {
                        submar.setEnabled(true);
                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray JA = new JSONArray(result_reg_face2);
                            Submar_name= new String[JA.length()];
                            Submar_id=new int [JA.length()];
                            ArrayList<String> name = new ArrayList<String>();
                            ArrayList<Integer>id = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                Submar_name[i] = (String) JO.get("name");
                                Submar_id[i] = (int) JO.get("id");

                                name.add( Submar_name[i]);
                                id.add(Submar_id[i]);

                            }
                            Submarahel = new String[Submar_name.length+1];


                            Submarahel[0]="حدد المرحلة الفرعية";
                            for (int i =0 ; i<JA.length();i++)
                            {
                                Submarahel[i+1]= ""+ Submar_name[i];
                            }

                            SUBmarahelList = new ArrayList<>(Arrays.asList(Submarahel));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterSUBMARAHEL = new ArrayAdapter<String>(
                                    Teacher_list.this,R.layout.support_simple_spinner_dropdown_item,SUBmarahelList);

                            spinnerArrayAdapterSUBMARAHEL.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            submar.setAdapter(spinnerArrayAdapterSUBMARAHEL);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        submar.setEnabled(false);
                    }


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void getCourses(final int pos) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_list.this);
                    result_reg_face4 = http.SEND_get_Courses("http://62.212.88.104/dal/API.asmx/getdata_course",Class_id[pos]);//here same fun of majority

                    Log.d("String", result_reg_face4);
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
                if(result_reg_face4!=null) {
                    if (result_reg_face4.contains("name")) {
                        subjects.setEnabled(true);                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray JA = new JSONArray(result_reg_face4);
                            Course_name= new String[JA.length()];
                            Course_id=new int [JA.length()];
                            ArrayList<String> name = new ArrayList<String>();
                            ArrayList<Integer>id = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                Course_name[i] = (String) JO.get("name");
                                Course_id[i] = (int) JO.get("id");



                            }
                            SubCourses = new String[Course_name.length+1];


                            SubCourses[0]="حدد اسم المادة";
                            for (int i =0 ; i<JA.length();i++)
                            {
                                SubCourses[i+1]= ""+ Course_name[i];
                            }

                            SUBJECTS = new ArrayList<>(Arrays.asList(SubCourses));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterSUBJECTS = new ArrayAdapter<String>(
                                    Teacher_list.this,R.layout.support_simple_spinner_dropdown_item,SUBJECTS);

                            spinnerArrayAdapterSUBJECTS.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            subjects.setAdapter(spinnerArrayAdapterSUBJECTS);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        subjects.setEnabled(false);
                    }


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void getClasses(final int pos) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_list.this);
                    result_reg_face3 = http.SEND_get_Class("http://62.212.88.104/dal/API.asmx/getdata_classes",Submar_id[pos]);//here same fun of majority

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
                    if (result_reg_face3.contains("name")) {
                        classesSp.setEnabled(true);                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray JA = new JSONArray(result_reg_face3);
                            Class_name= new String[JA.length()];
                            Class_id=new int [JA.length()];
                            ArrayList<String> name = new ArrayList<String>();
                            ArrayList<Integer>id = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                Class_name[i] = (String) JO.get("name");
                                Class_id[i] = (int) JO.get("id");



                            }
                            SubClass = new String[Class_name.length+1];


                            SubClass[0]="حدد الصف";
                            for (int i =0 ; i<JA.length();i++)
                            {
                                SubClass[i+1]= ""+ Class_name[i];
                            }

                            CLASSES = new ArrayList<>(Arrays.asList(SubClass));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterCLASSES = new ArrayAdapter<String>(
                                    Teacher_list.this,R.layout.support_simple_spinner_dropdown_item,CLASSES);

                            spinnerArrayAdapterCLASSES.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            classesSp.setAdapter(spinnerArrayAdapterCLASSES);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        classesSp.setEnabled(false);
                    }


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }


    public void search(View view) {
        //search for teachers
        if(!mar.getSelectedItem().toString().contains("حدد") && !submar.getSelectedItem().toString().contains("حدد")
                &&!classesSp.getSelectedItem().toString().contains("حدد") && !subjects.getSelectedItem().toString().contains("حدد") ) {
            if(mylat!=0) {
                getTeachers();
            }
            else
            {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
                a_builder.setMessage("عذرا نعاني مشاكل بتحديد موقعك قم بتحديده يدويا من فضلك ")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Teacher_list.this,Student_Map.class));
                                finish();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("خطأ :( ");
                alert.show();
            }
        }
        else
        {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("من فضلك ادخل كافة الحقول")
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
    }
    public void getTeachers() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_list.this);
                    result_reg_teacher = http.SEND_get_teacher("http://62.212.88.104/dal/API.asmx/getdata_teachers",
                            Course_id[subjects_mar_index],mar_id[mar_index],Submar_id[sub_mar_index],Class_id[class_mar_index] ,mylat,myLon  );//here same fun of majority
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
                    if (result_reg_teacher.contains("teacher_id")) {
                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        teacher_list.setVisibility(View.VISIBLE);

                        try {
                            JSONArray JA = new JSONArray(result_reg_teacher);



                            name= new String[JA.length()];
                            teacher_id=new int [JA.length()];
                            course_name = new String[JA.length()];
                                    star=new int [JA.length()];
                                            experience= new String[JA.length()];
                                    picture= new String[JA.length()];
                            longitude=new double [JA.length()];
                                    latitude=new double [JA.length()];
                            teacher_name=new ArrayList<String>();
                            courseName=new ArrayList<String>();
                            teacher_exp=new ArrayList<String>();
                            teacher_pic=new ArrayList<String>();


                            for (int i = 0; i < JA.length(); i++) {
                                JSONObject JO = (JSONObject) JA.get(i);

                                name[i] = (String) JO.get("name");
                                teacher_id[i] = (int) JO.get("teacher_id");
                                course_name[i] = (String) JO.get("course_name");
                                star[i] = (int) JO.get("star");
                                experience[i] = (String) JO.get("experience");
                                picture[i] = "http://62.212.88.104/picture/"+(String) JO.get("picture"); // add link
                                latitude[i] = (Double) JO.get("longitude");
                                longitude[i] = (Double) JO.get("latitude");


                                teacher_name.add(name[i]);
                                courseName.add(course_name[i]);
                                        teacher_pic.add(picture[i]);
                                teacher_exp.add(experience[i]);
                            }
//                            MyCustomerAdapterTeacherList  myCustomerAdapterTeacherList= new  MyCustomerAdapterTeacherList
//                                    (teacher_name,courseName,teacher_pic,teacher_exp,teacher_id,star,latitude,longitude,Teacher_list.this);
//                            teacher_list.setAdapter(myCustomerAdapterTeacherList);
                            getRating();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        teacher_list.setVisibility(View.INVISIBLE);
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_list.this);
                        a_builder.setMessage("لا يوجد معلم حاليا الرجاء الاتصال بنا 0779211116")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("عفوا :( ");
                        alert.show();
                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void getAllTeacher(View view) {
        //search for teachers
        if(!mar.getSelectedItem().toString().contains("حدد") && !submar.getSelectedItem().toString().contains("حدد")
                &&!classesSp.getSelectedItem().toString().contains("حدد") && !subjects.getSelectedItem().toString().contains("حدد") ) {
            if(mylat!=0) {
                getALLTeachers();
            }
            else
            {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
                a_builder.setMessage("عذرا نعاني مشاكل بتحديد موقعك قم بتحديده يدويا من فضلك ")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Teacher_list.this,Student_Map.class));
                                finish();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("خطأ :( ");
                alert.show();
            }
        }
        else
        {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("من فضلك ادخل كافة الحقول")
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
    }


        /////////////////////////////


    ////////////////
    public void getALLTeachers() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_list.this);
                    result_reg_teacher = http.SEND_get_teacher("http://62.212.88.104/dal/API.asmx/getdata_all_teachers?",
                            Course_id[subjects_mar_index],mar_id[mar_index],Submar_id[sub_mar_index],Class_id[class_mar_index] ,mylat,myLon  );//here same fun of majority
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
                    if (result_reg_teacher.contains("teacher_id")) {
                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        teacher_list.setVisibility(View.VISIBLE);

                        try {
                            JSONArray JA = new JSONArray(result_reg_teacher);



                            name= new String[JA.length()];
                            teacher_id=new int [JA.length()];
                            course_name = new String[JA.length()];
                            star=new int [JA.length()];
                            experience= new String[JA.length()];
                            picture= new String[JA.length()];
                            longitude=new double [JA.length()];
                            latitude=new double [JA.length()];
                            teacher_name=new ArrayList<String>();
                            courseName=new ArrayList<String>();
                            teacher_exp=new ArrayList<String>();
                            teacher_pic=new ArrayList<String>();


                            for (int i = 0; i < JA.length(); i++) {
                                JSONObject JO = (JSONObject) JA.get(i);

                                name[i] = (String) JO.get("name");
                                teacher_id[i] = (int) JO.get("teacher_id");
                                course_name[i] = (String) JO.get("course_name");
                                star[i] = (int) JO.get("star");
                                experience[i] = (String) JO.get("experience");
                                picture[i] = "http://62.212.88.104/picture/"+(String) JO.get("picture"); // add link
                                latitude[i] = (Double) JO.get("longitude");
                                longitude[i] = (Double) JO.get("latitude");


                                teacher_name.add(name[i]);
                                courseName.add(course_name[i]);
                                teacher_pic.add(picture[i]);
                                teacher_exp.add(experience[i]);
                            }
//                            MyCustomerAdapterTeacherList  myCustomerAdapterTeacherList= new  MyCustomerAdapterTeacherList
//                                    (teacher_name,courseName,teacher_pic,teacher_exp,teacher_id,star,latitude,longitude,Teacher_list.this);
//                            teacher_list.setAdapter(myCustomerAdapterTeacherList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        teacher_list.setVisibility(View.INVISIBLE);
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_list.this);
                        a_builder.setMessage("لا يوجد معلم حاليا الرجاء الاتصال بنا 0779211116")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("عفوا :( ");
                        alert.show();
                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void getRating() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_list.this);
                    result_reg_teacher1 = http.SEND_get_teacherRate("https://www.fynds.co.uk/wp-json/wc/v3/products?consumer_key=ck_3b8c9c25e6ee950aa51be748e0f092a08224f074" +
                                    "&consumer_secret=cs_a9970fffe1a60524ad99cbf75a90e2bb67a1bbe6&per_page=100&category=4077"
                              );//here same fun of majority
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
                    if (result_reg_teacher1.contains("teacher_id")) {
                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                       // teacher_list.setVisibility(View.VISIBLE);

                        try {
                            JSONArray JA = new JSONArray(result_reg_teacher1);
                            int [] star=new int[JA.length()];
                            newStars =new int [name.length];
                            for(int i =0 ; i<newStars.length;i++)
                            {
                                newStars[i]=5;
                            }





                            for (int i = 0; i < JA.length(); i++) {
                                JSONObject JO = (JSONObject) JA.get(i);

                                star[i] = (int) JO.get("avg");

                            }
                            for(int i=0;i<star.length;i++)
                            {
                                newStars[i]=star[i] ;
                            }

                            //   getRating(teacher_id);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                     //   teacher_list.setVisibility(View.INVISIBLE);
                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
