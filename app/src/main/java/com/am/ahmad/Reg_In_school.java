package com.am.ahmad;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Reg_In_school extends AppCompatActivity {
    Spinner Natio;
    String result_reg_face1,result_reg_face2;
    String [] mar_name,marahel;
    int [] mar_id;
    int m;
    boolean poss1;
    EditText fullname,address,phone,age;
    ArrayAdapter spinnerArrayAdapterMarahel;
    ArrayList  marahelList ;
    int rawda_id;
    ProgressDialog x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg__in_school);
        Natio=(Spinner)findViewById(R.id.NatioST);
        fullname=(EditText)findViewById(R.id.NameST);
        address=(EditText)findViewById(R.id.AddressST);
        phone=(EditText)findViewById(R.id.PhoneST);
        age=(EditText)findViewById(R.id.AgeSt);
        Intent intent = getIntent();
        rawda_id=intent.getIntExtra("school_id",0);
        x=new ProgressDialog(this);



        getMarhel();
        Natio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
               // Toast.makeText(getApplicationContext(),"position : "+position,Toast.LENGTH_LONG).show();
                if(position>0) {
                    m=position;
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
    }
    private void show() {

        x.setMessage("جاري التسجيل...");
        x.setCancelable(false);
        x.show();
    }
    public void register(View view) {
        if(poss1 && !fullname.getText().toString().equals("") &&!address.getText().toString().equals("")&&!phone.getText().toString().equals("")
                && !age.getText().toString().equals("")) {
            if(rawda_id!=0) {
                if ( phone.getText().toString().length() > 9) {
                    phone.setError("رقم الهاتف يزيد عن 9 ارقام");


                }
                else {
                    registerInRawda();
                    show();
                }
            }
            else
            {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
                a_builder.setMessage("خطا في معلومات الروضة حاول من جديد لاحقا")
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
    public void getMarhel() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Reg_In_school.this);
                    result_reg_face1 = http.SEND_get_maj("http://62.212.88.104/dal/API.asmx/getdata_nationality?");//here same fun of majority

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
                    if (result_reg_face1.contains("nationality_name")) {
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

                                mar_name[i] = (String) JO.get("nationality_name");
                                mar_id[i] = (int) JO.get("nationality_id");

                                name.add( mar_name[i]);
                                id.add(mar_id[i]);

                            }
                            marahel = new String[mar_name.length+1];


                            marahel[0]="                     حدد الجنسية  ";
                            for (int i =0 ; i<JA.length();i++)
                            {
                                marahel[i+1]= "                                  "+ mar_name[i];
                            }

                            marahelList = new ArrayList<>(Arrays.asList(marahel));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterMarahel = new ArrayAdapter<String>(
                                    Reg_In_school.this,R.layout.support_simple_spinner_dropdown_item,marahelList);

                            spinnerArrayAdapterMarahel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            Natio.setAdapter(spinnerArrayAdapterMarahel);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void registerInRawda() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Reg_In_school.this);
                    result_reg_face2 = http.SEND_reg_in_school("http://62.212.88.104/dal/API.asmx/rigester_student_in_school?",
                            fullname.getText().toString(),phone.getText().toString(),address.getText().toString(),age.getText().toString()
                            ,m,rawda_id);//here same fun of majority

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
                          x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Reg_In_school.this);
                        a_builder.setMessage("ِشكرا لتسجيلك معنا سيتم التواصل معك قريبا من قبل الادارة")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                        finish();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("تم");
                        alert.show();

                    }
                    else if (result_reg_face2.contains("exists"))
                    {                          x.cancel();


                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Reg_In_school.this);
                        a_builder.setMessage("انت مسجل معنا بالفعل سيتم التواصل معك قريبا ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                        finish();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("عذرا");
                        alert.show();
                    }
                    else if (result_reg_face2.contains("تأكد"))
                    {
                        x.cancel();

                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Reg_In_school.this);
                        a_builder.setMessage("تأكد من صحة البيانات او انك مسجل معنا بالفعل ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("عذرا");
                        alert.show();
                    }
                    else
                    {
                        x.cancel();

                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Reg_In_school.this);
                        a_builder.setMessage("حصل خطأ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext(),result_reg_face2,Toast.LENGTH_LONG).show();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("عذرا");
                        alert.show();
                    }


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
