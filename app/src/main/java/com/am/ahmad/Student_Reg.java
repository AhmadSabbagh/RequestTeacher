package com.am.ahmad;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.am.ahmad.Basics_class.AttolSharedPreference;
import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.IOHelper;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Student_Reg extends AppCompatActivity {
EditText name,password,phone,parent_phone,location;
Button sign_in;
    String result_reg_face,result_reg_face1,result_reg_face2;
    String id;
    ProgressDialog x;
    int nationalityID;
    Spinner nationality;
boolean poss1;
Spinner locationSp,governetSp;
    String []mar_name,Submar_name,Submarahel,Class_name,SubClass,Course_name,SubCourses;
    int [] mar_id,Gov_id,Add_id,Course_id;
    List<String> SUBmarahelList,CLASSES,SUBJECTS;
    ArrayAdapter<String> country;

    String[] marahel,marahelEXP,Gov_name,Add_name,marahelGov,marahelAdd;
    ArrayAdapter<String> spinnerArrayAdapterMarahel,spinnerArrayAdapterExp , spinnerArrayAdapterGovernet,spinnerArrayAdapterAddress ;
    List<String> marahelList,ExpList,GovList,AddressList;
int governetIndex,lcationIndex;
boolean poss2,poss3;
    ArrayList<String> result;
    CountryCodePicker StudentPhone,StudentParentPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__reg);
        name = (EditText) findViewById(R.id.nameET);
        password = (EditText) findViewById(R.id.passET);
        phone = (EditText) findViewById(R.id.phoneET);
        parent_phone = (EditText) findViewById(R.id.parentPhoneEt);
        locationSp=(Spinner) findViewById(R.id.LocationET);
        governetSp=(Spinner) findViewById(R.id.governetSp);
        nationality = (Spinner) findViewById(R.id.nationalityET);
        StudentPhone=(CountryCodePicker)findViewById(R.id.ccp);
        StudentParentPhone=(CountryCodePicker)findViewById(R.id.spinner2);
        StudentPhone.setAutoDetectedCountry(true);
        StudentParentPhone.setAutoDetectedCountry(true);
         String test=  phone.getText().toString();

        //  ;readJson();

        x = new ProgressDialog(this);
        String[] marahel = new String[]{
                "حدد المحافظة"

        };
        String[] Submarahel = new String[]{
                "حدد المنطقة"

        };
        GovList = new ArrayList<>(Arrays.asList(marahel));

        // Initializing an ArrayAdapter
        spinnerArrayAdapterGovernet = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,GovList);

        spinnerArrayAdapterGovernet.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        governetSp.setAdapter(spinnerArrayAdapterGovernet);

        //////////////////////////////////////////////////////////////////////////////////////
        AddressList = new ArrayList<>(Arrays.asList(Submarahel));

        // Initializing an ArrayAdapter
        spinnerArrayAdapterAddress = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,AddressList);

        spinnerArrayAdapterAddress.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        locationSp.setAdapter(spinnerArrayAdapterAddress);
        getMohafaza();

        governetSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                //     Toast.makeText(getApplicationContext(),"position : "+position,Toast.LENGTH_LONG).show();
                if(position>0) {
                    getAdress(position-1);
                    governetIndex=position-1;
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
        locationSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                // Toast.makeText(getApplicationContext(),"position : "+position,Toast.LENGTH_LONG).show();
                if(position>0) {
                    lcationIndex=position-1;

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

       // getMarhel();
        nationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                // Toast.makeText(getApplicationContext(),"position : "+position,Toast.LENGTH_LONG).show();
                if(position>0) {
                    //getSubMarahel(position-1);
                    nationalityID=position-1;
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

    public void getMohafaza() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Student_Reg.this);
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
                            Gov_name= new String[JA.length()];
                            Gov_id=new int [JA.length()];
                            ArrayList<String> name = new ArrayList<String>();
                            ArrayList<Integer>id = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                Gov_name[i] = (String) JO.get("name");
                                Gov_id[i] = (int) JO.get("id");

                                name.add( Gov_name[i]);
                                id.add(Gov_id[i]);

                            }
                            marahelGov = new String[Gov_name.length+1];


                            marahelGov[0]="                     حدد المحافظة";
                            for (int i =0 ; i<JA.length();i++)
                            {
                                marahelGov[i+1]= "                          "+ Gov_name[i];
                            }

                            GovList = new ArrayList<>(Arrays.asList(marahelGov));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterMarahel = new ArrayAdapter<String>(
                                    Student_Reg.this,R.layout.support_simple_spinner_dropdown_item,GovList);

                            spinnerArrayAdapterMarahel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            governetSp.setAdapter(spinnerArrayAdapterMarahel);





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

                    HttpRequestSender http = new HttpRequestSender(Student_Reg.this);
                    result_reg_face2 = http.SEND_get_adress("http://62.212.88.104/dal/API.asmx/getdata_address?",Gov_id[pos]);//here same fun of majority

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
                        locationSp.setEnabled(true);
                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray JA = new JSONArray(result_reg_face2);
                            Add_name= new String[JA.length()];
                            Add_id=new int [JA.length()];
                            ArrayList<String> name = new ArrayList<String>();
                            ArrayList<Integer>id = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                Add_name[i] = (String) JO.get("name");
                                Add_id[i] = (int) JO.get("id");

                                name.add(Add_name[i]);
                                id.add(Add_id[i]);

                            }
                            marahelAdd = new String[Add_name.length+1];


                            marahelAdd[0]="                     حدد المنطقة";
                            for (int i =0 ; i<JA.length();i++)
                            {
                                marahelAdd[i+1]= "                       "+ Add_name[i];
                            }

                            marahelList = new ArrayList<>(Arrays.asList(marahelAdd));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterAddress = new ArrayAdapter<String>(
                                    Student_Reg.this,R.layout.support_simple_spinner_dropdown_item,marahelList);

                            spinnerArrayAdapterAddress.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            locationSp.setAdapter(spinnerArrayAdapterAddress);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        locationSp.setEnabled(false);
                    }


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void readJson() {
        String jsonString = IOHelper.stringFromAsset(this, "county.json");
        try {
            //JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray cities = new JSONArray(jsonString);
             result= new ArrayList<String>();
            for (int i = 0; i < cities.length(); i++) {
                JSONObject city = cities.getJSONObject(i);
                //new Gson().fromJson(city.toString(), City.class);
                result.add(city.getString("dial_code"));
                country = new ArrayAdapter<String>(
                        Student_Reg.this,R.layout.support_simple_spinner_dropdown_item,result);

                country.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);




            }
           // txtJson.setText(result);
        } catch (Exception e) {
            Log.d("ReadPlacesFeedTask", e.getLocalizedMessage());
        }
    }


    private void show() {

        x.setMessage("جاري الدخول...");
        x.setCancelable(false);
        x.show();
    }
    public void sign_in(View view) {
        if(name.getText().toString().equals("") ||password.getText().toString().equals("") ||
                phone.getText().toString().equals("") ||parent_phone.getText().toString().equals("") ||poss2==false||poss3==false
               ) {

            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("من فضلك أملا كافة الحقول")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("عذرا :( ");
            alert.show();
        }
        else {
            if (parent_phone.getText().toString().length() > 9
                    || phone.getText().toString().length() > 9) {
                phone.setError("رقم الهاتف يزيد عن 9 ارقام");


            }
else
            {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("رقم هاتفك  " + StudentPhone.getFullNumberWithPlus() + phone.getText().toString())
                    .setCancelable(false)
                    .setNegativeButton("تعديل", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            show();
                            sendReg();
                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("شكرا ");
            alert.show();
        }
           // startActivity(new Intent(Student_Reg.this,Sign_In.class));
        }

    }

    public void sendReg() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(Student_Reg.this);
                    result_reg_face = http.SEND_REG("http://62.212.88.104/dal/API.asmx/add_student",name.getText().toString(),password.getText().toString()
                    ,StudentPhone.getFullNumberWithPlus()+phone.getText().toString(),StudentParentPhone.getFullNumberWithPlus()+parent_phone.getText().toString(),Add_id[lcationIndex],1);
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
                        //  x.cancel();

                           Toast.makeText(getBaseContext(), "مرحبا", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject JO = new JSONObject(result_reg_face);
                            id =JO.getString("student_id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            x.cancel();
                        }
                        x.cancel();
//                        AttolSharedPreference attolSharedPreference = new AttolSharedPreference(Student_Reg.this);
//                        attolSharedPreference.setKey("id",id);
//                        attolSharedPreference.setKey("p_phone",parent_phone.getText().toString());
                       startActivity(new Intent(Student_Reg.this,Sign_In.class));

                    }
                    else if (result_reg_face.contains("exist")) {

                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        //  sendRequest2(user.getPhoneNumber().toString());
                        try {
                            JSONObject JO = new JSONObject(result_reg_face);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            x.cancel();
                        }

//                        AttolSharedPreference attolSharedPreference = new AttolSharedPreference(Teacher_Reg.this);
//                        attolSharedPreference.setKey("id",Teacher_id);

                        x.cancel();
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Student_Reg.this);
                        a_builder.setMessage("رقم الهاتف موجود بالفعل ")
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
                    else
                    {
                        x.cancel();
                        Toast.makeText(getBaseContext(), "تاكد من صحة البيانات", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    x.cancel();
                    Toast.makeText(getBaseContext(), "تاكد من صحة البيانات", Toast.LENGTH_SHORT).show();
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

                    HttpRequestSender http = new HttpRequestSender(Student_Reg.this);
                    result_reg_face1 = http.SEND_get_maj("http://62.212.88.104/dal/API.asmx/getdata_nationality");//here same fun of majority

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

                                mar_name[i] = (String) JO.get("nationality_name");
                                mar_id[i] = (int) JO.get("nationality_id");
                                name.add( mar_name[i]);
                                id.add(mar_id[i]);

                            }
                            marahel = new String[mar_name.length+1];


                            marahel[0]="                                        البلد";
                            for (int i =0 ; i<JA.length();i++)
                            {
                                marahel[i+1]= "                                       "+ mar_name[i];
                            }

                            marahelList = new ArrayList<>(Arrays.asList(marahel));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterMarahel = new ArrayAdapter<String>(
                                    Student_Reg.this,R.layout.support_simple_spinner_dropdown_item,marahelList);

                            spinnerArrayAdapterMarahel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            nationality.setAdapter(spinnerArrayAdapterMarahel);





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
