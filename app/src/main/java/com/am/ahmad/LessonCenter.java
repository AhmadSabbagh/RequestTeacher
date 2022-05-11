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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterCenters;
import com.am.ahmad.Basics_class.MyCustomerAdapterLessonCenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LessonCenter extends AppCompatActivity {
ListView listView;
String result_reg_face,result_reg_face1,result_reg_face2;
    Spinner mar , submar;
    List<String> marahelList,SUBmarahelList,CLASSES,SUBJECTS;
    ArrayAdapter<String> spinnerArrayAdapterMarahel,spinnerArrayAdapterSUBMARAHEL,spinnerArrayAdapterCLASSES,spinnerArrayAdapterSUBJECTS;
    int mar_index,sm;
    String [] mar_name,marahel,Submar_name,Submarahel;

    boolean poss2,poss1;
    String []center_desc,center_pic,center_name;

    int []mar_id,Submar_id;
    int [] center_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_center);
        listView=(ListView)findViewById(R.id.lessonCenterId);
        mar = (Spinner)findViewById(R.id.marID);
                           submar = (Spinner)findViewById(R.id.subMarId);
        String[] marahel = new String[]{
                "حدد المحافظة"

        };
        String[] Submarahel = new String[]{
                "حدد المنطقة"

        };
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
        getMohafaza();
        mar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
           //     Toast.makeText(getApplicationContext(),"position : "+position,Toast.LENGTH_LONG).show();
                if(position>0) {
                    getAdress(position-1);
                    mar_index=position-1;
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(LessonCenter.this,Center_profile.class);
                intent.putExtra("desc",center_desc[position]);
                intent.putExtra("center_name",center_name[position]);
                intent.putExtra("center_id",center_id[position]);
                intent.putExtra("center_pic",center_pic[position]);
                intent.putExtra("address",submar.getSelectedItem().toString());

                startActivity(intent);
            }
        });


    }
    public void getLesson() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(LessonCenter.this);
                    result_reg_face = http.SEND_get_Lesson("http://62.212.88.104/dal/API.asmx/getdata_lesson_center?",Submar_id[sm]);
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
                    if (result_reg_face.contains("name")) {


                        try {
                            JSONArray JA ;
                            JA=new JSONArray(result_reg_face);
                            center_name  =new String[JA.length()];
                           center_pic = new String[JA.length()];
                             center_desc = new String[JA.length()];

                            center_id= new int[JA.length()];
                            ArrayList<String> namelist = new ArrayList<String>();
                            ArrayList<String>pic  = new ArrayList<String>();

                            listView.setVisibility(View.VISIBLE);

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);
                                center_name[i] = (String) JO.get("name");
                                center_id[i] = (int) JO.get("lesson_center_id");
                                center_pic[i] ="http://62.212.88.104/picture/"+ (String) JO.get("picture");
                                center_desc[i] = (String) JO.get("descr");

                                namelist.add(center_name[i]);
                                pic.add(center_pic[i]);
                            }
                            MyCustomerAdapterLessonCenter myCustomerAdapterCenters= new MyCustomerAdapterLessonCenter(namelist,pic,center_id,LessonCenter.this);
                            listView.setAdapter(myCustomerAdapterCenters);;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                    else
                    {
                        Toast.makeText(LessonCenter.this,"لا يوجد نتائج حاليا",Toast.LENGTH_LONG).show();
                        listView.setVisibility(View.INVISIBLE);

                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void getMohafaza() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(LessonCenter.this);
                    result_reg_face1 = http.SEND_get_maj("http://62.212.88.104/dal/API.asmx/getdata_governorate ");//here same fun of majority

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


                            marahel[0]="                     حدد المحافظة";
                            for (int i =0 ; i<JA.length();i++)
                            {
                                marahel[i+1]= "                                  "+ mar_name[i];
                            }

                            marahelList = new ArrayList<>(Arrays.asList(marahel));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterMarahel = new ArrayAdapter<String>(
                                    LessonCenter.this,R.layout.support_simple_spinner_dropdown_item,marahelList);

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
    public void getAdress(final int pos) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(LessonCenter.this);
                    result_reg_face2 = http.SEND_get_adress("http://62.212.88.104/dal/API.asmx/getdata_address?",mar_id[pos]);//here same fun of majority

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


                            Submarahel[0]="                     حدد المنطقة";
                            for (int i =0 ; i<JA.length();i++)
                            {
                                Submarahel[i+1]= "                                  "+ Submar_name[i];
                            }

                            SUBmarahelList = new ArrayList<>(Arrays.asList(Submarahel));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterSUBMARAHEL = new ArrayAdapter<String>(
                                    LessonCenter.this,R.layout.support_simple_spinner_dropdown_item,SUBmarahelList);

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

    public void searchForLesson(View view) {
        if(poss1&&poss2) {
            getLesson();
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
}
