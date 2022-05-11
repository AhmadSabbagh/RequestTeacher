package com.am.ahmad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.am.ahmad.Basics_class.HttpRequestSender;

public class Student_Evaluation extends AppCompatActivity {
RatingBar ratingBar;
int teaher_id,app_id;
EditText note;
String note2,result_reg_face;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__evaluation);
        ratingBar=(RatingBar)findViewById(R.id.ratingBarStudent);
        note=(EditText) findViewById(R.id.noteET);
      note2="درس جيد ومعلم ممتاز";

        Intent intent =getIntent();
        teaher_id=intent.getIntExtra("teacher_id",0);
        app_id=intent.getIntExtra("app_id",0);
    }

    public void rate(View view) {
        if(note.getText().toString().equals("")) {
            rateTeacher(note2);
        }
        else
        {
            rateTeacher(note.getText().toString());
        }
    }

    public void rateTeacher(final String change) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Student_Evaluation.this);
                    result_reg_face = http.setRate("http://62.212.88.104/dal/API.asmx/evaluation?",change,teaher_id,false
                            ,(int)ratingBar.getRating(),app_id);
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

                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Student_Evaluation.this);
                        a_builder.setMessage("شكرا لتقيمك ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                        finish();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("شكرا ");
                        alert.show();


                    }
                    else if(result_reg_face.contains("exist"))
                    {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Student_Evaluation.this);
                        a_builder.setMessage("قد قمت بتقيم الدرس من قبل لايمكنك اعادة التقييم  ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                        finish();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("نعتذر ");
                        alert.show();
                    }
                    else
                    {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Student_Evaluation.this);
                        a_builder.setMessage("حصل خطا ما من فضلك حاول لاحقا ")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                                        finish();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("نعتذر ");
                        alert.show();
                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }

}
