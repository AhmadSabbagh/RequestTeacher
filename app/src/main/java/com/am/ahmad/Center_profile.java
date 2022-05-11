package com.am.ahmad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyCustomerAdapterLessonCenter;
import com.am.ahmad.Basics_class.RecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class Center_profile extends AppCompatActivity {
    private ArrayList<String> mNames = new ArrayList<>(); //teacher Name
    private ArrayList<String> mImageUrls = new ArrayList<>();//teacher Image





    static String[] spaceProbeHeaders={"يوم","وقت","مادة","معلم"};

    String center_desc,center_pic,center_name,address,result_reg_face,result_reg_face1;
    int center_id;
    int tableChose=-1;
    ImageView center_image;
    TextView center_nameET,center_descET,center_addressET;

    String[] teaher_name ,day,course,start,end,teaher_name2 ;
    String [] teacher_pic ;
    int [] teacher_id,table_id;
     TableView<String[]> tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_center_profile);
         center_image = (ImageView) findViewById(R.id.center_img);
        center_nameET = (TextView) findViewById(R.id.centerName);
        center_addressET = (TextView) findViewById(R.id.center_address);
        center_descET = (TextView) findViewById(R.id.CenterDesc);



         tableView = (TableView<String[]>) findViewById(R.id.tableView);

        //SET PROP
        tableView.setHeaderBackgroundColor(Color.parseColor("#161AE5"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,spaceProbeHeaders));
        tableView.setColumnCount(4);

        Intent intent = getIntent();
        center_desc=intent.getStringExtra("desc");
        center_name=intent.getStringExtra("center_name");
        center_pic=intent.getStringExtra("center_pic");
        address=intent.getStringExtra("address");
        center_id=intent.getIntExtra("center_id",0);


        Picasso.with(this).load(center_pic).into(center_image);
        center_nameET.setText(center_name);
        center_addressET.setText(address);
        center_descET.setText(center_desc);












        tableView.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(int rowIndex, Object clickedData) {
              // Toast.makeText(Center_profile.this, ((String[])clickedData)[1], Toast.LENGTH_SHORT).show();
                AlertDialog.Builder a_builder = new AlertDialog.Builder(Center_profile.this);
                a_builder.setMessage("  وقت الدرس في : "+((String[])clickedData)[1]+"\n"+"الأيام :" +((String[])clickedData)[0])
                        .setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("شكرا :) ");
                alert.show();

                tableChose=rowIndex;
            }
        });
       // getImages();
        getTeachers();
        getTeachers_Table();
    }
    private void getImages(){
        Log.d("Tag", "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Trondheim");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Portugal");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Rocky Mountain National Park");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Mahahual");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Frozen Lake");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("White Sands Desert");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Austrailia");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Washington");

        initRecyclerView();

    }
    private void initRecyclerView(){
        Log.d("Tag", "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
    }


    public void regInCenter(View view) {
       // reginCenter();
        if(tableChose==-1)
        {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("من فضلك اختار جدول ")
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
            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("    لقد قمت باختيار المادة   " + course[tableChose])
                    .setCancelable(false)
                    .setNegativeButton("الغاء",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(Center_profile.this,MyView.class);
                            intent.putExtra("center_id",center_id);
                            intent.putExtra("table_id",table_id[tableChose]);
                            startActivity(intent);
                        }

                    });

            AlertDialog alert = a_builder.create();
            alert.setTitle("شكرا ");
            alert.show();



        }
    }
    public void getTeachers() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Center_profile.this);
                    result_reg_face = http.SEND_get_Teachers_center("http://62.212.88.104/dal/API.asmx/getdata_teacher_center?",center_id);
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
                    if (result_reg_face.contains("teacher_name")) {


                        try {
                            JSONArray JA ;
                            JA=new JSONArray(result_reg_face);
                             teaher_name  =new String[JA.length()];
                             teacher_pic = new String[JA.length()];
                             teacher_id= new int[JA.length()];

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);
                                teaher_name[i] = (String) JO.get("teacher_name");
                                teacher_id[i] = (int) JO.get("teacher_id");
                                teacher_pic[i] ="http://62.212.88.104/picture/"+ (String) JO.get("picture");

                                mNames.add(teaher_name[i]);
                                mImageUrls.add(teacher_pic[i]);
                            }
                            initRecyclerView();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void getTeachers_Table() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Center_profile.this);
                    result_reg_face1 = http.SEND_get_Teachers_center("http://62.212.88.104/dal/API.asmx/getdata_table_teacher?",center_id);
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


                        try {
                            JSONArray JA ;
                            JA=new JSONArray(result_reg_face1);
                            teaher_name2  =new String[JA.length()];
                            day = new String[JA.length()];
                            table_id= new int[JA.length()];
                            String[][] spaceProbes = new String [JA.length()][spaceProbeHeaders.length];
                            course  =new String[JA.length()];
                            start = new String[JA.length()];
                            end = new String[JA.length()];



                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);
                                day[i] = (String) JO.get("day");
                                spaceProbes [i][0]=day[i];

                                start[i] = (String) JO.get("start_time");
                                end[i] = (String) JO.get("end_time");
                                spaceProbes [i][1]=start[i]+"To"+end[i];

                                course[i] = (String) JO.get("course");
                                spaceProbes [i][2]=course[i];



                                teaher_name2[i] = (String) JO.get("teacher_name");
                                spaceProbes [i][3]=teaher_name2[i];


                                table_id[i] = (int) JO.get("table_id");

                            }
                            tableView.setDataAdapter(new SimpleTableDataAdapter(Center_profile.this, spaceProbes));

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
