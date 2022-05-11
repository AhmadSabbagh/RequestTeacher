package com.am.ahmad;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.am.ahmad.Basics_class.AttolSharedPreference;
import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.IOHelper;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Teacher_Reg extends AppCompatActivity {
    EditText name,password,phone,photo,classes;
String result_reg_face,result_reg_face1,result_reg_face2,result_reg_face4,result_reg_face3;
String [] maj_name,exp_name;
int []maj_id,exp_id,Gov_id,Add_id;
Spinner majority;
    String Teacher_id;
    String[] marahel,marahelEXP,Gov_name,Add_name,marahelGov,marahelAdd;
    ArrayAdapter<String> spinnerArrayAdapterMarahel,spinnerArrayAdapterExp , spinnerArrayAdapterGovernet,spinnerArrayAdapterAddress ;
    List<String> marahelList,ExpList,GovList,AddressList;
    String picturePath;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    Bitmap bitmap;
    ImageView agentamage;
    String imageString="/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAKBueIx4ZKCMgoy0qqC+8P//8Nzc8P//////////////\n" +
            "////////////////////////////////////////////2wBDAaq0tPDS8P//////////////////\n" +
            "////////////////////////////////////////////////////////////wAARCADIAMgDASIA\n" +
            "AhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\n" +
            "AAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\n" +
            "ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\n" +
            "p6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\n" +
            "AwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\n" +
            "BhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\n" +
            "U1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\n" +
            "uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwCaiiig\n" +
            "AooooAKKKKACigkAZJxUTTf3R+NAEtNLqOpqBmZuppKYiYzL2BNJ53+zUVFAEnnH0FHnH+7UdFAE\n" +
            "wmHcGnCRT3x9ar0UAWqKqgkdDipFmP8AEM0hk1FIrBhwaWgAooooAKKKKACiiigAooooAKKKKACm\n" +
            "PIF4HJpskvZfzqKgBWYsck0lFFMQUUUUAFFFFABRRRQAUUUUAFFFFAACQcipUl7N+dRUUAWqKgjk\n" +
            "K8HkVODkZFIYUUUUAFFFFABRRRQAVFLJ/CPxp0r7RgdTUFABRRRTEFFFFABRRRQAUUU4Rse2PrQA\n" +
            "2inmJvY0wqR1BFABRRRQAUUUUAFFFFABT432nB6UyigC1RUML/wn8KmpDCiiigAoJwCTRUUzdF/O\n" +
            "gCNiWJJpKKKYgooooAKKKKACgc0VLCvVqAHRxheTyafRRSGFFFFADGiU+xqFlKnBqzSOu5cUAVqK\n" +
            "KKYgooooAKKKKACrEbbl9+9V6fE21/Y0AT0UUUhhVZjuYmp5DhDVemIKKKKACiiigAooooAKnh+5\n" +
            "UFTQ/cP1oAkooopDCiiigAooooArP98/WkpWOWJ96SmIKKKKACiiigAooooAsodyg0VHAeCKKQxZ\n" +
            "zwBUNST/AHh9KjpiCiiigAooooAKKKKACp4gAmfWoKmhb5cdxQBJRRRSGFFFFABRRQSFBJoAqsMM\n" +
            "R70UHk0UxBRRRQAUUUUAFFFFAD4Th/rRSR/fFFIY6f74+n+NR1JP94fSo6YgooooAKKKKACiiigA\n" +
            "p0Rw49+KbRQBaopqNuXPfvTqQwooooAKjmPygetSHgZNVnbc2aAEooopiCiiigAooooAKKKKAFT7\n" +
            "6/UUUJ98fWikMknHQ1FU8wyn0qCmIKKKKACiiigAooooAKKKesbN14FADUYqcirCsGGRSbF27ccV\n" +
            "GY2U5Xn6Uhk1BOBk1B5knT+lG1365/GgAkk3cDp/OmVOsagYPNMeIjleaYiOigjHWigAooooAKKK\n" +
            "KACiiigB0QzIKKdAOSaKQyYjII9aqng4q1UEy4bPrQAyiiimIKKKKAAAk4FSrD/eP5U6Jdq57mn0\n" +
            "hiKir0FLRRQAUUUUAFFFFABRRRQAEA9Rmo2hB+6cVJRQBWZSp5FJVllDDBqsRg4NMQUUUUAFFFKi\n" +
            "7mAoAniGEHvzRTqKQwprruUinUUAVaKkmTB3D8ajpiClQbmApKkgHzE+lAE1FFFIYUUUUAFFFFAB\n" +
            "RRRQAUUUUAFFFFABUMww2fWpqjmGUz6GgCGiiimIKmhXA3HvUcabm9u9WKACiiikMKKKKAAjIwar\n" +
            "umw+1WKCAwwaAKtTQD5Sfeo3Qofb1qWHGymIfRRRSGFFFFABRRRQAUUUUAFFFFABRRRQAU2QZQ/S\n" +
            "nUjY2nNAFahQWOBQoLHAqwiBB70xCqoUYFLRRSGFFFFABRRRQAUUUUABAIwahZGQ7l6VNRQAxJQe\n" +
            "DwafTHjDcjg0z54/cfpQBNRUazKevFSAg9DmgAooooAKKKKACiijpQAUUxpVHfP0pm934UYH+e9A\n" +
            "EjSBfc+lR/NKfQU5YgOW5qSgBFUKMCloooAKKKKACiiigAooooAKKKKACiiigAooooAa0at2/KmG\n" +
            "Ej7rUUUAJiVff9aN8g6r+lFFAB5r/wB39DR5kh/h/Q0UUxB+9Pt+lHlMfvN/WiikMesSj3+tP6UU\n" +
            "UAFFFFABRRRQAUUUUAFFFFAH/9k=\n";
    ProgressDialog x;
    Button sign_up;
    Spinner expSpinner,locationSp,governetSp;
    ArrayAdapter<String> country;
int exp_index,lcationIndex,governetIndex;
boolean poss1,poss2,poss3;
    ArrayList<String> result;
    CountryCodePicker TeacherPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__reg);
        name=(EditText)findViewById(R.id.nameET);
       // username=(EditText)findViewById(R.id.mailET);
        password=(EditText)findViewById(R.id.passET);
        phone=(EditText)findViewById(R.id.phoneET);
        photo=(EditText)findViewById(R.id.photoET);
        expSpinner=(Spinner) findViewById(R.id.experiencesET);
        majority=(Spinner)findViewById(R.id.majorityET);
        locationSp=(Spinner) findViewById(R.id.LocationET);
        governetSp=(Spinner) findViewById(R.id.governetSp);

        sign_up=(Button)findViewById(R.id.signup_bu);
        x=new ProgressDialog(this);
        TeacherPhone=(CountryCodePicker)findViewById(R.id.spinner4) ;


        TeacherPhone.setAutoDetectedCountry(true);
      //  readJson();
        String[] marahel = new String[]{
                "                           حدد المحافظة                      "

        };
        String[] Submarahel = new String[]{
                "                              حدد المنطقة                      "

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
        getExperience();
       // agentamage=(ImageView)findViewById(R.id.agenttest);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }
        expSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                //     Toast.makeText(getApplicationContext(),"position : "+position,Toast.LENGTH_LONG).show();
                if(position>0) {
                  //  getAdress(position-1);
                    exp_index=position-1;
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

        getMajority();

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
                        Teacher_Reg.this,R.layout.support_simple_spinner_dropdown_item,result);

                country.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
             //   spinner3.setAdapter(country);



            }
            // txtJson.setText(result);
        } catch (Exception e) {
            Log.d("ReadPlacesFeedTask", e.getLocalizedMessage());
        }
    }
    private void show() {

        x.setMessage("جاري التسجيل...");
        x.setCancelable(false);
        x.show();
    }
    public void sign_in(View view) {
        if(name.getText().toString().equals("") ||password.getText().toString().equals("") ||
                phone.getText().toString().equals("")
               || majority.getSelectedItem().toString().contains("حدد")||poss1==false||poss2==false||poss3==false) {

            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("من فضلك أملا كافة الخانات ")
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
        else
        {
            if ( phone.getText().toString().length() > 9) {
                phone.setError("رقم الهاتف يزيد عن 9 ارقام");


            }
            else {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
                a_builder.setMessage("رقم هاتفك  " + TeacherPhone.getFullNumberWithPlus() + phone.getText().toString())
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
        }

    }
    public void getMajority() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_Reg.this);
                    result_reg_face1 = http.SEND_get_maj("http://62.212.88.104/dal/API.asmx/getdata_major");

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
                        sign_up.setEnabled(true);

                        //  x.cancel();
                      //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray JA = new JSONArray(result_reg_face1);
                            maj_name= new String[JA.length()];
                            maj_id=new int [JA.length()];
                            ArrayList<String> name = new ArrayList<String>();
                            ArrayList<Integer>id = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                maj_name[i] = (String) JO.get("name");
                                maj_id[i] = (int) JO.get("id");

                                name.add( maj_name[i]);
                                id.add(maj_id[i]);

                            }
                            marahel = new String[maj_name.length+1];


                            marahel[0]="                        حدد التخصص ";
                            for (int i =0 ; i<JA.length();i++)
                            {
                                marahel[i+1]= "                                  "+ maj_name[i];
                            }
                              marahelList = new ArrayList<>(Arrays.asList(marahel));

                            // Initializing an ArrayAdapter
                             spinnerArrayAdapterMarahel = new ArrayAdapter<String>(
                                    Teacher_Reg.this,R.layout.support_simple_spinner_dropdown_item,marahel);

                            spinnerArrayAdapterMarahel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            majority.setAdapter(spinnerArrayAdapterMarahel);
                        //    spinnerArrayAdapterMarahel.notifyDataSetChanged();






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        sign_up.setEnabled(false);
                         Toast.makeText(getBaseContext(), "انتظر من الادمن اضافة تخصاصات حتى يمكنك اكمال التسجيل", Toast.LENGTH_SHORT).show();

                    }

                }
                else
                {
                    sign_up.setEnabled(false);
                    Toast.makeText(getBaseContext(), "انتظر من الادمن اضافة تخصاصات حتى يمكنك اكمال التسجيل", Toast.LENGTH_SHORT).show();


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void getExperience() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_Reg.this);
                    result_reg_face4 = http.SEND_get_maj("http://62.212.88.104/dal/API.asmx/getdata_exp");

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
                    if (result_reg_face4.contains("expe")) {
                        sign_up.setEnabled(true);

                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray JA = new JSONArray(result_reg_face4);
                            exp_name = new String[JA.length()];
                            exp_id = new int[JA.length()];
                            ArrayList<String> name = new ArrayList<String>();
                            ArrayList<Integer> id = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);
                                exp_name[i] = (String) JO.get("expe");
                                exp_id[i] = (int) JO.get("id");

                                name.add(exp_name[i]);
                                id.add(exp_id[i]);


                            }
                            marahelEXP = new String[exp_name.length + 1];


                            marahelEXP[0] = "                      الخبرات ";
                            for (int i = 0; i < JA.length(); i++) {
                                marahelEXP[i + 1] = "                                  " + exp_name[i];
                            }
                            ExpList = new ArrayList<>(Arrays.asList(marahelEXP));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterExp = new ArrayAdapter<String>(
                                    Teacher_Reg.this,R.layout.support_simple_spinner_dropdown_item,marahelEXP);

                            spinnerArrayAdapterExp.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            expSpinner.setAdapter(spinnerArrayAdapterExp);
                            //    spinnerArrayAdapterMarahel.notifyDataSetChanged();






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        sign_up.setEnabled(false);
                        Toast.makeText(getBaseContext(), "انتظر من الادمن اضافة خبرات حتى يمكنك اكمال التسجيل", Toast.LENGTH_SHORT).show();

                    }

                }
                else
                {
                    sign_up.setEnabled(false);
                    Toast.makeText(getBaseContext(), "انتظر من الادمن اضافة خبرات حتى يمكنك اكمال التسجيل", Toast.LENGTH_SHORT).show();


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void sendReg() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    String getMajority=majority.getSelectedItem().toString();
                    int index=0 ;

                    for (int i=0;i<maj_name.length;i++)
                    {
                        if (getMajority.contains(maj_name[i]))
                        {
                          index=i;
                        }
                    }
                    HttpRequestSender http = new HttpRequestSender(Teacher_Reg.this);
                    result_reg_face = http.SEND_REG_Teacher("http://62.212.88.104/dal/API.asmx/add_teacher",name.getText().toString(),password.getText().toString()
                            ,TeacherPhone.getFullNumberWithPlus()+phone.getText().toString(),exp_id[exp_index],maj_id[index],Add_id[lcationIndex]
                            ,imageString);
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
                    if (result_reg_face.contains("سيتم التواصل معك لاحقا")) {

                        //  x.cancel();
                         //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        //  sendRequest2(user.getPhoneNumber().toString());
                        try {
                            JSONObject JO = new JSONObject(result_reg_face);
                            Teacher_id=JO.getString("teacher_id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            x.cancel();
                        }

//                        AttolSharedPreference attolSharedPreference = new AttolSharedPreference(Teacher_Reg.this);
//                        attolSharedPreference.setKey("id",Teacher_id);

                        x.cancel();
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_Reg.this);
                        a_builder.setMessage("شكرا من فضلك تابع التسجيل")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent= new Intent(Teacher_Reg.this,Teacher_reg2.class);
                                        intent.putExtra("teacher_id",Teacher_id);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("شكرا:) ");
                        alert.show();

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
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_Reg.this);
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
                        Toast.makeText(Teacher_Reg.this,"حدث خطأ",Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    x.cancel();
                    Toast.makeText(Teacher_Reg.this,"حدث خطأ",Toast.LENGTH_LONG).show();

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
             picturePath = cursor.getString(columnIndex);
             photo.setText(picturePath);
            cursor.close();
            bitmap=BitmapFactory.decodeFile(picturePath);
           bitmap= scaleDown(bitmap,700,true);
           // agentamage.setImageBitmap(bitmap);
            AttolSharedPreference attolSharedPrefence = new AttolSharedPreference(this);
            attolSharedPrefence.setKey("ImagePath", picturePath);
            imageString =ImageToString(bitmap);


        }
    }

    public void getPic(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    private String ImageToString (Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,70,byteArrayOutputStream);
        byte[] imageBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.DEFAULT);

    }
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        if (ratio>=1)
        {
            return realImage;
        }
        else {
            return newBitmap;
        }
    }

    public void getMohafaza() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_Reg.this);
                    result_reg_face3 = http.SEND_get_maj("http://62.212.88.104/dal/API.asmx/getdata_governorate ");//here same fun of majority

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
                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray JA = new JSONArray(result_reg_face3);
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


                            marahelGov[0]="                             حدد المحافظة";
                            for (int i =0 ; i<JA.length();i++)
                            {
                                marahelGov[i+1]= "                                  "+ Gov_name[i];
                            }

                            GovList = new ArrayList<>(Arrays.asList(marahelGov));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterMarahel = new ArrayAdapter<String>(
                                    Teacher_Reg.this,R.layout.support_simple_spinner_dropdown_item,GovList);

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

                    HttpRequestSender http = new HttpRequestSender(Teacher_Reg.this);
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


                            marahelAdd[0]="                          حدد المنطقة";
                            for (int i =0 ; i<JA.length();i++)
                            {
                                marahelAdd[i+1]= "                              "+ Add_name[i];
                            }

                            marahelList = new ArrayList<>(Arrays.asList(marahelAdd));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterAddress = new ArrayAdapter<String>(
                                    Teacher_Reg.this,R.layout.support_simple_spinner_dropdown_item,marahelList);

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

}
