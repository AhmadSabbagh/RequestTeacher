package com.am.ahmad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Teacher_reg2 extends AppCompatActivity {
    Spinner mar , submar,classesSp,subjects;
    String result_reg_face1,result_reg_face2,result_reg_face3,result_reg_face4,result_reg_face5;
     List<String> marahelList,SUBmarahelList,CLASSES,SUBJECTS;
     ArrayAdapter<String> spinnerArrayAdapterMarahel,spinnerArrayAdapterSUBMARAHEL,spinnerArrayAdapterCLASSES,spinnerArrayAdapterSUBJECTS;
     String []marahel,mar_name,Submar_name,Submarahel,Class_name,SubClass,Course_name,SubCourses;
     int [] mar_id,Submar_id,Class_id,Course_id;
int m,sm,cl,co;
boolean poss1,poss2,poss3,poss4;
int context;
    String teacher_id;
   // CheckBox agree;
    TextView agreeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_reg2);
        mar = (Spinner)findViewById(R.id.marID);
        submar = (Spinner)findViewById(R.id.subMarId);
        classesSp = (Spinner)findViewById(R.id.classesId);
        subjects = (Spinner)findViewById(R.id.subjects);
       // agree=(CheckBox)findViewById(R.id.agree);
       // agreeText=(TextView) findViewById(R.id.agreeText);

        Intent intent=getIntent();
        context=intent.getIntExtra("context",0);
        teacher_id=intent.getStringExtra("teacher_id");

        poss1=false;
        poss2=false;
        poss3=false;
        poss4=false;
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
                "حدد المادة"

        };


//agreeText.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        startActivity(new Intent(Teacher_reg2.this,Policy.class));
//    }
//});
        //////////////////////////////////////////////////////////////////////////////////////
        SUBmarahelList = new ArrayList<>(Arrays.asList(Submarahel));

        // Initializing an ArrayAdapter
        spinnerArrayAdapterSUBMARAHEL = new ArrayAdapter<String>(
                Teacher_reg2.this,R.layout.support_simple_spinner_dropdown_item,SUBmarahelList);

        spinnerArrayAdapterSUBMARAHEL.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        submar.setAdapter(spinnerArrayAdapterSUBMARAHEL);

        //////////////////////////////////////////////////////////////////////////////////////
         CLASSES = new ArrayList<>(Arrays.asList(classes));

        // Initializing an ArrayAdapter
        spinnerArrayAdapterCLASSES = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,CLASSES);

        spinnerArrayAdapterCLASSES.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        classesSp.setAdapter(spinnerArrayAdapterCLASSES);

        //////////////////////////////////////////////////////////////////////////////////////
         SUBJECTS = new ArrayList<>(Arrays.asList(subject));

        // Initializing an ArrayAdapter
       spinnerArrayAdapterSUBJECTS = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,SUBJECTS);

        spinnerArrayAdapterSUBJECTS.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        subjects.setAdapter(spinnerArrayAdapterSUBJECTS);

        //////////////////////////////////////////////////////////////////////////////////////
        getMarhel();
        mar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
               // Toast.makeText(getApplicationContext(),"position : "+position,Toast.LENGTH_LONG).show();
                if(position>0) {
                     getSubMarahel(position-1);
                     m=position-1;
                     poss1=true;
                }
                else
                {
                    poss1=false;
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
                    sm=position-1;

                    poss2=true;
                }
                else
                {
                    poss2=false;
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
                    cl=position-1;

                    poss3=true;
                }
                else
                {
                    poss3=false;
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
                    co=position-1;

                    poss4=true;
                }
                else
                {
                    poss4=false;
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

                    HttpRequestSender http = new HttpRequestSender(Teacher_reg2.this);
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
                                    Teacher_reg2.this,R.layout.support_simple_spinner_dropdown_item,marahelList);

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

                    HttpRequestSender http = new HttpRequestSender(Teacher_reg2.this);
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
                                    Teacher_reg2.this,R.layout.support_simple_spinner_dropdown_item,SUBmarahelList);

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

                    HttpRequestSender http = new HttpRequestSender(Teacher_reg2.this);
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
                                    Teacher_reg2.this,R.layout.support_simple_spinner_dropdown_item,SUBJECTS);

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

                    HttpRequestSender http = new HttpRequestSender(Teacher_reg2.this);
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
                                    Teacher_reg2.this,R.layout.support_simple_spinner_dropdown_item,CLASSES);

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
    public void reg(View view) {
        if(poss1 && poss2 && poss3 && poss4)
            regTeacherNewInfo();
//        {
//            if(agree.isChecked()) {
//
//            }
//            else
//            {
//                AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
//                a_builder.setMessage("من فضلك وافق على شروط التطبيق")
//                        .setCancelable(false)
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
//                            }
//                        });
//                AlertDialog alert = a_builder.create();
//                alert.setTitle("خطأ :( ");
//                alert.show();
//            }
//        }
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
        //teacher list noti

    }
    public void regTeacherNewInfo() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_reg2.this);
                    if (context == 1) {
                        result_reg_face5 = http.addCourses("http://62.212.88.104/dal/API.asmx/add_course_teacher", mar_id[m], Submar_id[sm],
                                Class_id[cl], Course_id[co]);//here same fun of majority
                    }
                    else
                    {
                        result_reg_face5 = http.addCourses2("http://62.212.88.104/dal/API.asmx/add_course_teacher", mar_id[m], Submar_id[sm],
                                Class_id[cl], Course_id[co],teacher_id);//here same fun of majority
                    }
                    Log.d("String", result_reg_face5);
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
                if(result_reg_face5!=null) {
                    if (result_reg_face5.contains("succ ")) {
                     //  Toast.makeText(Teacher_reg2.this,"addedSucc",Toast.LENGTH_SHORT).show();
                        if(context==0) {


                          //  x.cancel();
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_reg2.this);
                            a_builder.setMessage("من فضلك تابع التسجيل ")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent= new Intent(Teacher_reg2.this, Policy.class);
                                            intent.putExtra("teacher_id",teacher_id);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                            AlertDialog alert = a_builder.create();
                            alert.setTitle("شكرا :) ");
                            alert.show();
                        }
                        else
                        {
                            finish();

                        }

                    }
                    else
                    {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_reg2.this);
                        a_builder.setMessage("البيانات اما خاطئة او انك مسجل بهذه المراحل من قبل  ")
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

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
