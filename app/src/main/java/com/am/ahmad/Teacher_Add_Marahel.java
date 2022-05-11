package com.am.ahmad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class Teacher_Add_Marahel extends AppCompatActivity {
    Spinner mar , submar,classesSp,subjects;
    String result_reg_face1,result_reg_face2,result_reg_face3,result_reg_face4,result_reg_face5,result_reg_face9;
    List<String> marahelList,SUBmarahelList,CLASSES,SUBJECTS;
    ArrayAdapter<String> spinnerArrayAdapterMarahel,spinnerArrayAdapterSUBMARAHEL,spinnerArrayAdapterCLASSES,spinnerArrayAdapterSUBJECTS;
    String []marahel,mar_name,Submar_name,Submarahel,Class_name,SubClass,Course_name,SubCourses;
    int [] mar_id,Submar_id,Class_id,Course_id;
    int m,sm,cl,co;
    boolean poss1,poss2,poss3,poss4;
    int context;
    String teacher_id;
    TableView<String[]> tableView;
    String[][] spaceProbes;
    static String[] spaceProbeHeaders={"حذف","المادة","الصف","الفرعية", "الاساسية"};
    String [] main,sub,Class,course;
    int [] course_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__add__marahel);
        mar = (Spinner)findViewById(R.id.marID);
        submar = (Spinner)findViewById(R.id.subMarId);
        classesSp = (Spinner)findViewById(R.id.classesId);
        subjects = (Spinner)findViewById(R.id.subjects);
        tableView = (TableView<String[]>) findViewById(R.id.tableView);

        tableView.setHeaderBackgroundColor(Color.parseColor("#161AE5"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,spaceProbeHeaders));
        tableView.setColumnCount(5);
        tableView.setColumnWeight(0,5);
        tableView.setColumnWeight(1,6);
        tableView.setColumnWeight(2,6);
        tableView.setColumnWeight(3,6);
        tableView.setColumnWeight(4,6);


        tableView.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(final int rowIndex, Object clickedData) {
                // Toast.makeText(Teacher_profile.this, ((String[])clickedData)[1], Toast.LENGTH_SHORT).show();
                // tableChose=rowIndex;

                AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_Add_Marahel.this);
                a_builder.setMessage("هل تريد حذف ما يلي ؟ "+"\n"+
                        "المرحلة الاساسية:    " +spaceProbes[rowIndex][4]+"\n"+
                        "المرحلة الفرعية:    " +  spaceProbes[rowIndex][3]+"\n"+
                        "الصف:    " +   spaceProbes[rowIndex][2]+"\n"+
                        "المادة:    " +   spaceProbes[rowIndex][1]+"\n")
                        .setCancelable(false)
                        .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delete(course_id[rowIndex]);

                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("شكرا :) ");
                alert.show();

            }
        });

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
                "حدداسم المادة"

        };



        //////////////////////////////////////////////////////////////////////////////////////
        SUBmarahelList = new ArrayList<>(Arrays.asList(Submarahel));

        // Initializing an ArrayAdapter
        spinnerArrayAdapterSUBMARAHEL = new ArrayAdapter<String>(
                Teacher_Add_Marahel.this,R.layout.support_simple_spinner_dropdown_item,SUBmarahelList);

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
        getInfo();
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



    public void delete(final int id) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_Add_Marahel.this);
                    result_reg_face9 = http.SEND_deleteCourse("http://62.212.88.104/dal/API.asmx/delete_course_teacher",id);//here same fun of majority

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
                if(result_reg_face9!=null) {
                    if (result_reg_face9.contains("succ")) {

                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_Add_Marahel.this);
                        a_builder.setMessage("تم الحذف  ")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                     startActivity(new Intent(Teacher_Add_Marahel.this,Teacher_Add_Marahel.class));
                                     finish();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("شكرا :) ");
                        alert.show();
                    }
                    else
                    {
                        Toast.makeText(getApplication(),"حدث خطأ",Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(getApplication(),"حدث خطأ",Toast.LENGTH_LONG).show();

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void getInfo() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_Add_Marahel.this);
                    result_reg_face9 = http.SEND_get_teacherInfoA("http://62.212.88.104/dal/API.asmx/getdata_course_teacher");//here same fun of majority

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
                if(result_reg_face9!=null) {
                    if (result_reg_face9.contains("name")) {
                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();

                        try {
                            JSONArray JA = new JSONArray(result_reg_face9);
                            main= new String[JA.length()];
                            sub=new String [JA.length()];
                            Class= new String[JA.length()];
                            course=new String [JA.length()];
                            course_id=new int [JA.length()];
                            spaceProbes = new String [JA.length()][spaceProbeHeaders.length];


                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                main[i] = (String) JO.get("schoolgrade_name");
                                spaceProbes [i][4]=main[i];

                                sub[i] = (String) JO.get("subschoolgrade_name");
                                spaceProbes [i][3]=sub[i];

                                Class[i] = (String) JO.get("class_name");
                                spaceProbes [i][2]=Class[i];

                                course[i] = (String) JO.get("course_name");
                                spaceProbes [i][1]=course[i];
                                spaceProbes [i][0]="مسح";

                                course_id[i] = (int) JO.get("course_id");


                            }


                            tableView.setDataAdapter(new SimpleTableDataAdapter(Teacher_Add_Marahel.this, spaceProbes));




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }


    public void getMarhel() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_Add_Marahel.this);
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
                                    Teacher_Add_Marahel.this,R.layout.support_simple_spinner_dropdown_item,marahelList);

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

                    HttpRequestSender http = new HttpRequestSender(Teacher_Add_Marahel.this);
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
                                    Teacher_Add_Marahel.this,R.layout.support_simple_spinner_dropdown_item,SUBmarahelList);

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

                    HttpRequestSender http = new HttpRequestSender(Teacher_Add_Marahel.this);
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
                                    Teacher_Add_Marahel.this,R.layout.support_simple_spinner_dropdown_item,SUBJECTS);

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

                    HttpRequestSender http = new HttpRequestSender(Teacher_Add_Marahel.this);
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
                                    Teacher_Add_Marahel.this,R.layout.support_simple_spinner_dropdown_item,CLASSES);

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
        if(poss1 && poss2 && poss3 && poss4) {
            regTeacherNewInfo();
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
        //teacher list noti

    }
    public void regTeacherNewInfo() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_Add_Marahel.this);

                        result_reg_face5 = http.addCourses("http://62.212.88.104/dal/API.asmx/add_course_teacher", mar_id[m], Submar_id[sm],
                                Class_id[cl], Course_id[co]);//here same fun of majority


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
                        //  Toast.makeText(Teacher_Add_Marahel.this,"addedSucc",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_Add_Marahel.this);
                        a_builder.setMessage("تمت الاضافة بنجاح  ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                        startActivity(new Intent(Teacher_Add_Marahel.this, Teacher_Add_Marahel.class));
                                        finish();

                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("شكرا :)");
                        alert.show();



                    }
                    else
                    {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_Add_Marahel.this);
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
