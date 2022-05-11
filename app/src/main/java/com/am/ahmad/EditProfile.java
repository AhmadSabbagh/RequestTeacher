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
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.am.ahmad.Basics_class.AttolSharedPreference;
import com.am.ahmad.Basics_class.HttpRequestSender;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditProfile extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    EditText name,username,password,phone,photo,classes;
    ImageView agentImg;
    Spinner majority,experiences,location,governetSp;
    String Teacher_id;

    String result_reg_face,result_reg_face1,result_reg_face2,result_reg_face11,result_reg_face23;
    String [] maj_name,exp_name;
    int []maj_id,exp_id,Gov_id,Add_id;

    String[] marahel,marahelEXP,Gov_name,Add_name,marahelGov,marahelAdd;
    ArrayAdapter<String> spinnerArrayAdapterMarahel,spinnerArrayAdapterExp , spinnerArrayAdapterGovernet,spinnerArrayAdapterAddress ;
    List<String> marahelList,ExpList,GovList,AddressList;
ProgressDialog x;
    String picturePath;
    Bitmap bitmap;
    ImageView agentamage;
    String imageString,teacher_name,image_url,experience,phoneC,addressC,governorate,major_name;
    int experience_id,address_id,governorate_id,major_id;
    int exp_index,lcationIndex,governetIndex;
    boolean poss1,poss2,poss3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        picturePath="";
        agentImg = (ImageView) findViewById(R.id.profilePic);
        name = (EditText) findViewById(R.id.nameET);
        phone = (EditText) findViewById(R.id.phoneET);
      //  photo = (EditText) findViewById(R.id.profilePic);
        x=new ProgressDialog(this);
       // classes = (EditText) findViewById(R.id.ClassesET);
        experiences = (Spinner) findViewById(R.id.experiencesET);
        governetSp = (Spinner) findViewById(R.id.governetESP);
exp_index=lcationIndex=-1;
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        majority = (Spinner) findViewById(R.id.majorityET);
                    location = (Spinner) findViewById(R.id.LocationET);
                    experiences.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
        getInfo();
        show();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }
     //   getMajority();


    }
    public void getMajority() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(EditProfile.this);
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


                            marahel[0]="                                   "+major_name;
                            for (int i =0 ; i<JA.length();i++)
                            {
                                marahel[i+1]= "                             "+ maj_name[i];
                            }
                            marahelList = new ArrayList<>(Arrays.asList(marahel));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterMarahel = new ArrayAdapter<String>(
                                    EditProfile.this,R.layout.support_simple_spinner_dropdown_item,marahel);

                            spinnerArrayAdapterMarahel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            majority.setAdapter(spinnerArrayAdapterMarahel);
                            //    spinnerArrayAdapterMarahel.notifyDataSetChanged();






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        x.cancel();
                    }

                }
                else
                {
                    x.cancel();
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

                    HttpRequestSender http = new HttpRequestSender(EditProfile.this);
                    result_reg_face23 = http.get_info("http://62.212.88.104/dal/API.asmx/getdata_infoteacher");

                    Log.d("String", result_reg_face23);
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
                if(result_reg_face23!=null) {
                    if (result_reg_face23.contains("teacher_name")) {
                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();



//                        {"id":28,"teacher_name":null,"image_url":null,"experience":null
//                                ,"experience_id":0,"phone":"","address_id":0,"address":"","governorate_id":0,
//                                "governorate":"","major_id":0,"major_name":""}
                        try {
                            JSONObject JO = new JSONObject(result_reg_face23);




                            teacher_name = (String) JO.get("teacher_name");
                            image_url = "http://62.212.88.104/picture/"+(String) JO.get("image_url");
                            phoneC = (String) JO.get("phone");
                            name.setText(teacher_name);
                            phone.setText(phoneC);
                            Picasso.with(EditProfile.this).load(image_url).into(agentImg);

                            experience = (String) JO.get("experience");
                            major_name = (String) JO.get("major_name");
                            addressC = (String) JO.get("address");
                            governorate = (String) JO.get("governorate");

                            experience_id  = (int) JO.get("experience_id");
                            address_id  = (int) JO.get("address_id");
                            governorate_id  = (int) JO.get("governorate_id");
                            major_id  = (int) JO.get("major_id");

                      getMajority();
                      getExperience();
                      getMohafaza();
                      getAdress1(governorate_id);
                          //  imageString= getByteArrayFromImageURL(image_url);
                           // sendReg();








                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

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

                    HttpRequestSender http = new HttpRequestSender(EditProfile.this);
                    result_reg_face11 = http.SEND_get_maj("http://62.212.88.104/dal/API.asmx/getdata_exp");

                    Log.d("String", result_reg_face11);
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
                if(result_reg_face11!=null) {
                    if (result_reg_face11.contains("expe")) {
                       // sign_up.setEnabled(true);

                        //  x.cancel();
                        //  Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray JA = new JSONArray(result_reg_face11);
                            exp_name= new String[JA.length()];
                            exp_id=new int [JA.length()];
                            ArrayList<String> name = new ArrayList<String>();
                            ArrayList<Integer>id = new ArrayList<Integer>();

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                exp_name[i] = (String) JO.get("expe");
                                exp_id[i] = (int) JO.get("id");

                                name.add( exp_name[i]);
                                id.add(exp_id[i]);

                            }
                            marahelEXP = new String[exp_name.length+1];


                            marahelEXP[0]="                                   "+experience;
                            for (int i =0 ; i<JA.length();i++)
                            {
                                marahelEXP[i+1]= "                                   "+ exp_name[i];
                            }
                            ExpList = new ArrayList<>(Arrays.asList(marahelEXP));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterExp = new ArrayAdapter<String>(
                                    EditProfile.this,R.layout.support_simple_spinner_dropdown_item,marahelEXP);

                            spinnerArrayAdapterExp.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            experiences.setAdapter(spinnerArrayAdapterExp);
                            //    spinnerArrayAdapterMarahel.notifyDataSetChanged();






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                      //  sign_up.setEnabled(false);
                        Toast.makeText(getBaseContext(), "انتظر من الادمن اضافة خبرات حتى يمكنك اكمال التسجيل", Toast.LENGTH_SHORT).show();
                        x.cancel();

                    }

                }
                else
                {
                   // sign_up.setEnabled(false);
                    Toast.makeText(getBaseContext(), "انتظر من الادمن اضافة خبرات حتى يمكنك اكمال التسجيل", Toast.LENGTH_SHORT).show();
x.cancel();

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

                    HttpRequestSender http = new HttpRequestSender(EditProfile.this);
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


                            marahelGov[0]="                                   "+governorate;
                            for (int i =0 ; i<JA.length();i++)
                            {
                                marahelGov[i+1]= "                                   "+ Gov_name[i];
                            }

                            GovList = new ArrayList<>(Arrays.asList(marahelGov));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterMarahel = new ArrayAdapter<String>(
                                    EditProfile.this,R.layout.support_simple_spinner_dropdown_item,GovList);

                            spinnerArrayAdapterMarahel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            governetSp.setAdapter(spinnerArrayAdapterMarahel);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
else
                    {
                        x.cancel();
                    }
                }
                else {
                    x.cancel();
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

                    HttpRequestSender http = new HttpRequestSender(EditProfile.this);
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
                        location.setEnabled(true);
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


                            marahelAdd[0]="                                 حدد المنطقة";
                            for (int i =0 ; i<JA.length();i++)
                            {
                                marahelAdd[i+1]= "                                      "+ Add_name[i];
                            }

                            marahelList = new ArrayList<>(Arrays.asList(marahelAdd));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterAddress = new ArrayAdapter<String>(
                                    EditProfile.this,R.layout.support_simple_spinner_dropdown_item,marahelList);

                            spinnerArrayAdapterAddress.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            location.setAdapter(spinnerArrayAdapterAddress);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        location.setEnabled(false);
                    }


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void getAdress1(final int pos) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(EditProfile.this);
                    result_reg_face2 = http.SEND_get_adress("http://62.212.88.104/dal/API.asmx/getdata_address?",pos);//here same fun of majority

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
                        location.setEnabled(true);
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


                            marahelAdd[0]="                                   "+addressC;
                            for (int i =0 ; i<JA.length();i++)
                            {
                                marahelAdd[i+1]= "                                   "+ Add_name[i];
                            }

                            marahelList = new ArrayList<>(Arrays.asList(marahelAdd));

                            // Initializing an ArrayAdapter
                            spinnerArrayAdapterAddress = new ArrayAdapter<String>(
                                    EditProfile.this,R.layout.support_simple_spinner_dropdown_item,marahelList);

                            spinnerArrayAdapterAddress.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            location.setAdapter(spinnerArrayAdapterAddress);
x.cancel();




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        location.setEnabled(false);
                        x.cancel();
                    }


                }
                else
                {
                    x.cancel();
                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }


    private void show() {

        x.setMessage("Loging...");
        x.setCancelable(true);
        x.show();
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
           // photo.setText(picturePath);
            cursor.close();
            bitmap=BitmapFactory.decodeFile(picturePath);
            bitmap= scaleDown(bitmap,1000,true);
             agentImg.setImageBitmap(bitmap);
            AttolSharedPreference attolSharedPrefence = new AttolSharedPreference(this);
            attolSharedPrefence.setKey("ImagePath", picturePath);
            imageString =ImageToString(bitmap);


        }
    }


    private String ImageToString (Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);
        byte[] imageBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.DEFAULT);


    }

    public void Edit(View view) {
        if(imageString==null) {
            {

                imageString= getByteArrayFromImageURL(image_url);

            }

//            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
//            a_builder.setMessage("من فضلك أملا كافة الخانات وخانة الصورة  ")
//                    .setCancelable(false)
//                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
//                        }
//                    });
//            AlertDialog alert = a_builder.create();
//            alert.setTitle("خطأ :( ");
//            alert.show();
        }
        else
        {
            sendReg();
        }
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
                    HttpRequestSender http = new HttpRequestSender(EditProfile.this);
                    if(exp_index==-1 && lcationIndex!=-1) {
                        result_reg_face = http.SEND_REG_Teacher_Edit("http://62.212.88.104/dal/API.asmx/update_teacher_information?",
                                name.getText().toString()
                                , phone.getText().toString(),
experience_id    ,                            maj_id[index],
                                Add_id[lcationIndex]
                                , imageString);
                    }
                    else if(lcationIndex==-1 && exp_index!=-1)
                    {
                        result_reg_face = http.SEND_REG_Teacher_Edit("http://62.212.88.104/dal/API.asmx/update_teacher_information?",
                                name.getText().toString()
                                , phone.getText().toString(),
                                exp_id[exp_index],
                                maj_id[index],
address_id
                                , imageString);
                    }
                    else if(lcationIndex==-1&&exp_index==-1)
                    {
                        result_reg_face = http.SEND_REG_Teacher_Edit("http://62.212.88.104/dal/API.asmx/update_teacher_information?",
                                name.getText().toString()
                                , phone.getText().toString(),
                                experience_id,
                                maj_id[index],
                                address_id
                                , imageString);
                    }
                    else
                    {
                        result_reg_face = http.SEND_REG_Teacher_Edit("http://62.212.88.104/dal/API.asmx/update_teacher_information?",
                                name.getText().toString()
                                , phone.getText().toString(),
                                exp_id[exp_index],
                                maj_id[index],
                                Add_id[lcationIndex]

                                , imageString);
                    }
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
                    if (result_reg_face.contains("Successfully")) {
                        Toast.makeText(EditProfile.this,"تم",Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(EditProfile.this,Teacher_page.class));
                        finish();

                    }
                    else
                    {
                        //Toast.makeText(EditProfile.this,"حصل خطأ",Toast.LENGTH_SHORT).show();
                        getByteArrayFromImageURL(image_url);

                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void picClick(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
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
    private String getByteArrayFromImageURL(String url) {

        try {
            final InputStream[] is = new InputStream[1];

            URL imageUrl = new URL(url);
            final URLConnection ucon = imageUrl.openConnection();

                        // Your implementation
                        is[0] = ucon.getInputStream();


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = is[0].read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.flush();
            sendReg();
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        } catch (Exception e) {
            Log.d("Error", e.toString());
        }
        return null;
    }
}
