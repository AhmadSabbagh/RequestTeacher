package com.am.ahmad;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterLib;
import com.am.ahmad.Basics_class.MyCustomerAdapterNormalOffer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Libraries extends AppCompatActivity {
ListView listView;
String  result_reg_face,result_reg_face1,result_reg_face2,result_reg_face6;
int x;
    int [] id;
    ProgressDialog x1;
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
        setContentView(R.layout.activity_libraries);
        listView=(ListView)findViewById(R.id.list);
        Intent intent=getIntent();
        x=intent.getIntExtra("x",0);
        mar = (Spinner)findViewById(R.id.moh);
        submar = (Spinner)findViewById(R.id.addressM);
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
       // getLib();
        x1= new ProgressDialog(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                    Intent intent = new Intent(Teacher_Job.this, Teacher_Evaluation.class);
//                    intent.putExtra("student_id", student_id[position]);
//                    intent.putExtra("app_id", app_id[position]);
//
//                    startActivity(intent);

                if (x != 1) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(Libraries.this);
                    View mView = getLayoutInflater().inflate(R.layout.reg_in_libr_custom_dialog, null);

                    final EditText phone = (EditText) mView.findViewById(R.id.phone);
                    final EditText desc = (EditText) mView.findViewById(R.id.desc);


                    Button mLogin = (Button) mView.findViewById(R.id.reg_lib);
                    mLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (phone.getText().toString().equals("") ||
                                    desc.getText().toString().equals("")) {
                                phone.setError("خانة اجبارية");
                            }
                              else
                            {
                                if (phone.getText().toString().length() > 9) {
                                    phone.setError("رقم الهاتف يزيد عن 9 ارقام");


                                }

                                else {

                                    reg_on_lib(position, phone.getText().toString(), desc.getText().toString());
                                    show();
                                }

                            }


                        }
                    });
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                }
            }
        });
    }


    public void getMohafaza() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Libraries.this);
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
                                    Libraries.this,R.layout.support_simple_spinner_dropdown_item,marahelList);

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

                    HttpRequestSender http = new HttpRequestSender(Libraries.this);
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
                                    Libraries.this,R.layout.support_simple_spinner_dropdown_item,SUBmarahelList);

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
    public void getLib() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Libraries.this);
                    if(x==1) {
                        result_reg_face = http.getOffersPics("http://62.212.88.104/dal/API.asmx/getdata_library",Submar_id[sm]);
                    }
                    else if(x==2) {
                        result_reg_face = http.getOffersPics("http://62.212.88.104/dal/API.asmx/getdata_Summer_Clubs",Submar_id[sm]);
                    }
                    else if(x==3) {
                        result_reg_face = http.getOffersPics("http://62.212.88.104/dal/API.asmx/getdata_Sports_Clubs",Submar_id[sm]);
                    }
                    else if(x==4) {
                        result_reg_face = http.getOffersPics("http://62.212.88.104/dal/API.asmx/getdata_Quran_Centers",Submar_id[sm]);
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
                    if (result_reg_face.contains("name")) {

                        try {
                            listView.setVisibility(View.VISIBLE);

                            JSONArray JA;
                            JA=new JSONArray(result_reg_face);
                            String [] name = new String [JA.length()];
                            String [] desc = new String [JA.length()];
                            id = new int [JA.length()];
                            ArrayList<String> nameA = new ArrayList<String>();
                            String [] address = new String [JA.length()];


                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);

                                name[i] = (String) JO.get("name");
                                id[i] = (int) JO.get("id");
                                desc[i] = (String) JO.get("descr");
                                address[i]=JO.getString("address");
                                nameA.add(name[i]);
                            }
                            MyCustomerAdapterLib myCustomerAdapterLib= new MyCustomerAdapterLib(nameA
                                    ,desc,address,id,Libraries.this );
                            listView.setAdapter(myCustomerAdapterLib);
                            x1.cancel();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            x1.cancel();
                        }



                    }
                    else
                    {
                        Toast.makeText(Libraries.this,"لا يوجد نتائج حاليا",Toast.LENGTH_LONG).show();
                        listView.setVisibility(View.INVISIBLE);
                        x1.cancel();
                    }

                }
                else
                {
                    x1.cancel();
                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void reg_on_lib(final int pos, final String phone , final  String  desc) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Libraries.this);

                        result_reg_face6 = http.reg_on_lib("http://62.212.88.104/dal/API.asmx/rigester_in_Club",id[pos],phone,desc);


                    Log.d("String", result_reg_face6);
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
                if(result_reg_face6!=null) {
                    if (result_reg_face6.contains("succ")) {



                        android.app.AlertDialog.Builder a_builder = new android.app.AlertDialog.Builder(Libraries.this);
                        a_builder.setMessage("شكرا تم تسجيل بنجاح")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();

                                    }
                                });
                        android.app.AlertDialog alert = a_builder.create();
                        alert.setTitle("شكرا :) ");
                        alert.show();



                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(),"حدث خطأ او انك مسجل بالفعل ",Toast.LENGTH_LONG);
                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    private void show() {

        x1.setMessage("جاري التسجيل...");
        x1.setCancelable(false);
        x1.show();
    }

    public void serachM(View view) {
        if(poss1&&poss2) {
            getLib();
            show();
        }
        else
        {
            android.app.AlertDialog.Builder a_builder = new android.app.AlertDialog.Builder(this);
            a_builder.setMessage("من فضلك حدد جميع الخانات ")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                        }
                    });
            android.app.AlertDialog alert = a_builder.create();
            alert.setTitle("خطأ :( ");
            alert.show();
        }
    }
}
