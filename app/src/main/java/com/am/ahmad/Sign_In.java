package com.am.ahmad;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class Sign_In extends AppCompatActivity {
String type,result_reg_face,id;
EditText username,password;
String p_phone;
    ProgressDialog x;
CountryCodePicker sign;
    ArrayAdapter<String> spinnerArrayAdapterMarahel,country;

    ArrayList<String> result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in);
        username=(EditText)findViewById(R.id.mailET);
        password=(EditText)findViewById(R.id.passET);
        x=new ProgressDialog(this);
        sign=(CountryCodePicker) findViewById(R.id.spinner5) ;
        type="";
        sign.setAutoDetectedCountry(true);

        //  readJson();
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
                        Sign_In.this,R.layout.support_simple_spinner_dropdown_item,result);

                country.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
               // spinner.setAdapter(country);



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
    public void clickB(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.student:
                if (checked)
                {
                    type="student";
                }
                break;
            case R.id.teacher:
                if (checked)
                {
                    type="teacher";

                }
                break;
            case R.id.admin:
                if (checked)
                {
                    type="admin";

                }
                break;
        }
    }


    public void forget(View view) {
        startActivity(new Intent(Sign_In.this,ForgetPass.class));

    }

    public void signIn(View view) {
        if(username.getText().toString().equals("") ||password.getText().toString().equals("")
                ) {

            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("من فضلك ادخل كافة البيانات")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("خطأ :( ");
            alert.show();
        }
        else
        {
          show();
            sign_in();
        }
    }


    public void sign_in() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(Sign_In.this);
                    result_reg_face="";
                    if(type.equals("") )
                    {
                        result_reg_face = http.SEND_SignIn("http://62.212.88.104/dal/API.asmx/Login_admin", sign.getFullNumberWithPlus()+username.getText().toString(), password.getText().toString());

                    }
                    else {
                        if (type.equals("student")) {
                            result_reg_face = http.SEND_SignIn("http://62.212.88.104/dal/API.asmx/Login_student", sign.getFullNumberWithPlus()+username.getText().toString(), password.getText().toString()
                            );
                        } else if (type.equals("teacher")) {
                            result_reg_face = http.SEND_SignIn("http://62.212.88.104/dal/API.asmx/Login_teacher", sign.getFullNumberWithPlus()+username.getText().toString(), password.getText().toString());

                        }
                        else
                        {
                            x.cancel();
                           // Toast.makeText(Sign_In.this,"من فضلك املأ كافة الخانات ",Toast.LENGTH_LONG).show();

                        }
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
                        if (result_reg_face.contains("admin "))
                  {
                      try {
                          JSONObject JO = new JSONObject(result_reg_face);
                          id =JO.getString("admin_id");
                      } catch (JSONException e) {
                          e.printStackTrace();
                          x.cancel();

                      }
                      AttolSharedPreference attolSharedPreference = new AttolSharedPreference(Sign_In.this);
                      attolSharedPreference.setKey("id",id);
                      startActivity(new Intent(Sign_In.this,Admin_page.class));
                      finish();
                      x.cancel();
                  }
                else   if (result_reg_face.contains("succ")) {
                      //  x.cancel();
                          Toast.makeText(getBaseContext(), "welcome", Toast.LENGTH_SHORT).show();
                      //  sendRequest2(user.getPhoneNumber().toString());
                      if (type.equals("student")) {
                          try {
                              JSONObject JO = new JSONObject(result_reg_face);
                              id =JO.getString("student_id");
                              p_phone =JO.getString("phone_father");
                          } catch (JSONException e) {
                              e.printStackTrace();
                              x.cancel();

                          }
                          AttolSharedPreference attolSharedPreference = new AttolSharedPreference(Sign_In.this);
                          attolSharedPreference.setKey("id",id);
                          attolSharedPreference.setKey("p_phone",p_phone);
                          startActivity(new Intent(Sign_In.this,student_page.class));
                        finish();
                          x.cancel();


                      }
                      else if (type.equals("teacher")) {
                          try {
                              JSONObject JO = new JSONObject(result_reg_face);
                              id=JO.getString("teacher_id");
                          } catch (JSONException e) {
                              e.printStackTrace();
                              x.cancel();

                          }

                          AttolSharedPreference attolSharedPreference = new AttolSharedPreference(Sign_In.this);
                          attolSharedPreference.setKey("id",id);
                          startActivity(new Intent(Sign_In.this,Teacher_page.class));
                          finish();
                          x.cancel();


                      }

                  }
                  else if (result_reg_face.contains("Waiting"))
                  {
                      x.cancel();

                      AlertDialog.Builder a_builder = new AlertDialog.Builder(Sign_In.this);
                      a_builder.setMessage("بانتظار تأكيد التسجيل من قبل المدير ")
                              .setCancelable(false)
                              .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int which) {

                                  }
                              });
                      AlertDialog alert = a_builder.create();
                      alert.setTitle(" أهلا :)");
                      alert.show();
                  }
                  else if (result_reg_face.contains("in correct"))
                  {
                      Toast.makeText(getBaseContext(), "خطأ في كلمة السر او اسم المستخدم", Toast.LENGTH_SHORT).show();
                      x.cancel();


                  }
                  else
                        {
                            Toast.makeText(getBaseContext(), "خطأ في كلمة السر او اسم المستخدم", Toast.LENGTH_SHORT).show();
                            x.cancel();
                        }

              }
              else
              {
                  Toast.makeText(getBaseContext(), "خطأ في كلمة السر او اسم المستخدم", Toast.LENGTH_SHORT).show();
                  x.cancel();
              }



                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
