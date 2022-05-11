package com.am.ahmad.Basics_class;

/**
 * Created by ahmad on 3/29/2018.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

public class HttpRequestSender {
    private Context context;
    private String id;
    public String getID() {
        return this.id;
    }
    public void setID(String id) {
        this.id = id;
    }
    public HttpRequestSender(Activity context) {
        this.context = context;
        AttolSharedPreference mySharedPreference = new AttolSharedPreference(context);
        this.id = mySharedPreference.getKey("id");
    }
    public String SEND_REG(String url, String name, String password, String phone,String parent_phone, int location, int nationality) throws Exception {

//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("POST");
//      //  Date cDate = new Date();
//       // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
//        JSONObject jo = new JSONObject();
//        String name1= name.replace(" ", "");
//        String location1=location.replace(" ", "");

        InputStream is;

        ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();

        nameValuePairs1.add(new BasicNameValuePair("name", name));
        nameValuePairs1.add(new BasicNameValuePair("password", password));
        nameValuePairs1.add(new BasicNameValuePair("phone",phone));
        nameValuePairs1.add(new BasicNameValuePair("nationality", ""+1));
        nameValuePairs1.add(new BasicNameValuePair("address",""+ location));
        nameValuePairs1.add(new BasicNameValuePair("phone_father", parent_phone));


        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(url);

        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1,"UTF-8"));

        HttpResponse responce = httpclient.execute(httppost);

        HttpEntity entity = responce.getEntity();

        is = entity.getContent();

        BufferedReader bufr = new BufferedReader(new InputStreamReader(is,"iso-8859-1"), 8);

        StringBuilder sb = new StringBuilder();

        sb.append(bufr.readLine() + "\n");

        String line = "0";

        while ((line = bufr.readLine()) != null)

        {

            sb.append(line + "\n");

        }

        is.close();

        return  sb.toString();
    }
    public String getOffersPics(String url,int add_id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("address_id",add_id);

//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String getOffersPics1(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();

//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String getcode(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("type",0);

//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String getAll(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();

//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }

    public String deleteaccount(String url,int id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("teacher_id",id);

//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }


    public String sendLocation(String url,double myLoat,double myLon) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
         jo.put("latitude",myLoat);
        jo.put("longitude",myLon);

        jo.put("student_id",getID());
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_admin_noti(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("admin_id",getID());
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String sendLocation2(String url,double myLoat,double myLon) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("latitude",myLoat);
        jo.put("longitude",myLon);

        jo.put("teacher_id",getID());
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_REG_Teacher_Edit(String url, String name,   String phone, int experience, int majority, int location, String photo) throws Exception {




        InputStream is;

        ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
        nameValuePairs1.add(new BasicNameValuePair("teacher_id", getID()));
        nameValuePairs1.add(new BasicNameValuePair("name", name));
        nameValuePairs1.add(new BasicNameValuePair("phone",phone));
        nameValuePairs1.add(new BasicNameValuePair("experience",""+experience));
        nameValuePairs1.add(new BasicNameValuePair("address", ""+location));
        nameValuePairs1.add(new BasicNameValuePair("major_id",""+ majority));
        nameValuePairs1.add(new BasicNameValuePair("picture",photo));


        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(url);

        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1,"UTF-8"));

        HttpResponse responce = httpclient.execute(httppost);

        HttpEntity entity = responce.getEntity();

        is = entity.getContent();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(is));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();


        return   response.toString();
    }
    public String SEND_REG_Teacher(String url, String name, String password, String phone, int experience, int majority, int location, String photo) throws Exception {

//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("POST");
//        //  Date cDate = new Date();
//        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
//        JSONObject jo = new JSONObject();
//       String name1= name.replace(" ", "");
//        String location1=location.replace(" ", "");



        InputStream is;

        ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();

        nameValuePairs1.add(new BasicNameValuePair("name", name));
        nameValuePairs1.add(new BasicNameValuePair("password", password));
        nameValuePairs1.add(new BasicNameValuePair("phone",phone));
        nameValuePairs1.add(new BasicNameValuePair("experience",""+experience));
        nameValuePairs1.add(new BasicNameValuePair("address", ""+location));
        nameValuePairs1.add(new BasicNameValuePair("major_id",""+ majority));
        nameValuePairs1.add(new BasicNameValuePair("url",photo));


        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(url);

        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1,"UTF-8"));

        HttpResponse responce = httpclient.execute(httppost);

        HttpEntity entity = responce.getEntity();

        is = entity.getContent();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(is));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();


        return   response.toString();
    }
    public String SEND_POST_jobSearch(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();


//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String addCourses(String url,int school_id,int subScholl_id,int class_id,int corse_id) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("teacher_id",getID());
        jo.put("scholl_grade_id",school_id);
        jo.put("subscholl_grade_id",subScholl_id);

        jo.put("class_id",class_id);
        jo.put("course_id",corse_id);




//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String addCourses2(String url,int school_id,int subScholl_id,int class_id,int corse_id,String teacher_id) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("teacher_id",teacher_id);
        jo.put("scholl_grade_id",school_id);
        jo.put("subscholl_grade_id",subScholl_id);

        jo.put("class_id",class_id);
        jo.put("course_id",corse_id);




//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String setStatus(String url,String change) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("teacher_id",getID());
        jo.put("state",change);



//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String getStatus(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("teacher_id",getID());


//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String getStatus1(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("teachers_id",getID());


//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_teacher(String url, int course, int mar,int sub,int classes,double lat,double lon) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("course_id", course);
        jo.put("schoolgrade_id", mar);
        jo.put("subschoolgrade_id", sub);
        jo.put("class_id", classes);
        jo.put("latitude",lat);
        jo.put("longitude",lon);
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_Courses(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();


//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_News(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("student_id",getID());
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_News_details(String url,int id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("news_id",id);
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_SignIn(String url, String username, String password) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();

        jo.put("phone", username);
        jo.put("password", password);

//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_maj(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();



//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_Class(String url, int id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("sub_schoolgrade_id", id);

//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_Courses(String url, int id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("class_id", id);

//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_SubMar(String url, int id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("schoolgrade_id", id);

//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_adress(String url, int id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("governorate_id", id);

//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_Lesson(String url, int id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("address_id", id);

//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_reg_in_rawada(String url,String name,String phone,String address,String age,int natio,int rawda_id) throws Exception {

        InputStream is;

        ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();

        nameValuePairs1.add(new BasicNameValuePair("student_id", getID()));
        nameValuePairs1.add(new BasicNameValuePair("fullname", name));
        nameValuePairs1.add(new BasicNameValuePair("phone",phone));
        nameValuePairs1.add(new BasicNameValuePair("nationality",""+natio));
        nameValuePairs1.add(new BasicNameValuePair("address", address));
        nameValuePairs1.add(new BasicNameValuePair("age",age));
        nameValuePairs1.add(new BasicNameValuePair("kindergarten_id",""+rawda_id));


        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(url);

        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1,"UTF-8"));

        HttpResponse responce = httpclient.execute(httppost);

        HttpEntity entity = responce.getEntity();

        is = entity.getContent();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(is));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();


        return   response.toString();
    }
    public String SEND_reg_in_Uni(String url,String name,String phone,String address,String age,int natio,int rawda_id) throws Exception {


        InputStream is;

        ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();

        nameValuePairs1.add(new BasicNameValuePair("student_id", getID()));
        nameValuePairs1.add(new BasicNameValuePair("fullname", name));
        nameValuePairs1.add(new BasicNameValuePair("phone",phone));
        nameValuePairs1.add(new BasicNameValuePair("nationality",""+natio));
        nameValuePairs1.add(new BasicNameValuePair("address", address));
        nameValuePairs1.add(new BasicNameValuePair("specialty",age));
        nameValuePairs1.add(new BasicNameValuePair("university_id",""+rawda_id));


        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(url);

        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1,"UTF-8"));

        HttpResponse responce = httpclient.execute(httppost);

        HttpEntity entity = responce.getEntity();

        is = entity.getContent();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(is));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();


        return   response.toString();

    }
    public String SEND_reg_in_school(String url,String name,String phone,String address,String age,int natio,int rawda_id) throws Exception {

        InputStream is;

        ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();

        nameValuePairs1.add(new BasicNameValuePair("student_id", getID()));
        nameValuePairs1.add(new BasicNameValuePair("fullname", name));
        nameValuePairs1.add(new BasicNameValuePair("phone",phone));
        nameValuePairs1.add(new BasicNameValuePair("nationality",""+natio));
        nameValuePairs1.add(new BasicNameValuePair("address", address));
        nameValuePairs1.add(new BasicNameValuePair("classroom",age));
        nameValuePairs1.add(new BasicNameValuePair("school_id",""+rawda_id));


        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(url);

        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1,"UTF-8"));

        HttpResponse responce = httpclient.execute(httppost);

        HttpEntity entity = responce.getEntity();

        is = entity.getContent();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(is));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();


        return   response.toString();
    }
    public String SEND_get_Teachers_center(String url,int id) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("center_id",id);




//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_rigesetINLesson(String url, int center_id, int table_id, String phone, String name) throws Exception {

        InputStream is;

        ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();

        nameValuePairs1.add(new BasicNameValuePair("student_id", getID()));
        nameValuePairs1.add(new BasicNameValuePair("name", name));
        nameValuePairs1.add(new BasicNameValuePair("phone",phone));
        nameValuePairs1.add(new BasicNameValuePair("table_id",""+table_id));
        nameValuePairs1.add(new BasicNameValuePair("center_id",""+center_id));


        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(url);

        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1,"UTF-8"));

        HttpResponse responce = httpclient.execute(httppost);

        HttpEntity entity = responce.getEntity();

        is = entity.getContent();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(is));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();


        return   response.toString();
    }
    public String SEND_rigesetINCoursesCenter(String url, int center_id, int course_id, String phone, String name) throws Exception {


        InputStream is;

        ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();

        nameValuePairs1.add(new BasicNameValuePair("student_id", getID()));
        nameValuePairs1.add(new BasicNameValuePair("name", name));
        nameValuePairs1.add(new BasicNameValuePair("phone",phone));
        nameValuePairs1.add(new BasicNameValuePair("course_id",""+course_id));
        nameValuePairs1.add(new BasicNameValuePair("center_id",""+center_id));


        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(url);

        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1,"UTF-8"));

        HttpResponse responce = httpclient.execute(httppost);

        HttpEntity entity = responce.getEntity();

        is = entity.getContent();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(is));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();


        return   response.toString();
    }
    public String SEND_get_teacherInfo(String url,int id) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("teacher_id",id);




//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_teacherInfoA(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("teacher_id",getID());




//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_deleteCourse(String url,int id) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("teacher_id",getID());
        jo.put("course_id",id);





//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }

    public String SEND_get_appointment(String url,int flag,int teacher_id,String date,String phone) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("teacher_id",teacher_id);
        jo.put("student_id",getID());
        jo.put("flag",flag);
        jo.put("phone",phone);
        jo.put("date",date);





//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_Accept_reject(String url,boolean flag,int app_id) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("appointment_id",app_id);
        jo.put("flag",flag);

        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_req(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("student_id",getID());
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String setRate(String url,String note,int teacher_id,boolean flag,int star,int app_id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        JSONObject jo = new JSONObject();
        jo.put("student_id",getID());
        jo.put("teacher_id",teacher_id);
        jo.put("star",star);
        jo.put("comment",note);
        jo.put("flag",flag);
        jo.put("appointment_id",app_id);
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_files(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
      //  jo.put("teacher_id",getID());
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_job(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("teachers_id",getID());
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String setRate1(String url,String note,int student_id,boolean flag,int star,int app_id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        JSONObject jo = new JSONObject();
        jo.put("student_id",student_id);
        jo.put("teacher_id",getID());
        jo.put("star",star);
        jo.put("comment",note);
        jo.put("flag",flag);
        jo.put("appointment_id",app_id);
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_delete_files(String url,int id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("file_id",id);
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_get_teacherRate(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");


        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //   con.setDoOutput(true); //remove it if we want to get
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_Forget(String url,String username) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();
        jo.put("phone",username);
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_end_lesson(String url,int app) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        Date cDate = new Date();
        String fDate = new SimpleDateFormat("MM-dd-yyyy").format(cDate);
        int datee=cDate.getDate();
        int time=cDate.getHours();
        int time2=cDate.getMinutes();
        Date currentTime = Calendar.getInstance().getTime();
        String mydaye=fDate+"-"+time+":"+time2;
        JSONObject jo = new JSONObject();
        jo.put("teacher_id",getID());
        jo.put("appointment_id",app);
        jo.put("datetime",mydaye);

//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_start_lesson(String url,int app) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        Date cDate = new Date();
        String fDate = new SimpleDateFormat("MM-dd-yyyy").format(cDate);
        int datee=cDate.getDate();
        int time=cDate.getHours();
        int time2=cDate.getMinutes();
        Date currentTime = Calendar.getInstance().getTime();
        String mydaye=fDate+"-"+time+":"+time2;
        JSONObject jo = new JSONObject();
        jo.put("teacher_id",getID());
        jo.put("appointment_id",app);
        jo.put("datetime",mydaye);

//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }

    public String reg_on_lib(String url,int id,String phone , String descr) throws Exception {


        InputStream is;

        ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();

        nameValuePairs1.add(new BasicNameValuePair("club_id",""+id));
        nameValuePairs1.add(new BasicNameValuePair("student_id",getID()));
        nameValuePairs1.add(new BasicNameValuePair("descr",descr));
        nameValuePairs1.add(new BasicNameValuePair ("phone",phone));


        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(url);

        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1,"UTF-8"));

        HttpResponse responce = httpclient.execute(httppost);

        HttpEntity entity = responce.getEntity();

        is = entity.getContent();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(is));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();


        return   response.toString();


    }
    public String SEND_help(String url, String help) throws Exception {




        InputStream is;

        ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
        nameValuePairs1.add(new BasicNameValuePair("student_id", getID()));
        nameValuePairs1.add(new BasicNameValuePair("help", help));


        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(url);

        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1,"UTF-8"));

        HttpResponse responce = httpclient.execute(httppost);

        HttpEntity entity = responce.getEntity();

        is = entity.getContent();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(is));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();


        return   response.toString();
    }


    public String get_help(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();


        jo.put("student_id",getID());
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String get_info(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();


        jo.put("teacher_id",getID());
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }


    public String changePassT(String url, String old, String newP)throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();


        jo.put("teacher_id",getID());
        jo.put("old_password",old);
        jo.put("new_password",newP);


//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String changePassS(String url, String old, String newP)throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();


        jo.put("student_id",getID());
        jo.put("old_password",old);
        jo.put("new_password",newP);


//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String getOfferDetails(String url,int id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();


        jo.put("offer_id",id);
//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }

    public String getOffersPics2(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //  Date cDate = new Date();
        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        JSONObject jo = new JSONObject();

//name=string&username=string&password=string&phone=string&experience=string&major_id=string&address=string&url=string
// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
}