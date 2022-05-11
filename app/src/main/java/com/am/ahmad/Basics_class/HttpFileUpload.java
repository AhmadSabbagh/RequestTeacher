package com.am.ahmad.Basics_class;

/**
 * Created by ahmad on 4/27/2018.
 */

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.am.ahmad.Teacher_upload_files;
import com.android.volley.Request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class HttpFileUpload implements Runnable{
    URL connectURL;
    String responseString;
    String Title;
    String Description;
    byte[ ] dataToServer;
    FileInputStream fileInputStream = null;
    Context context;

    public HttpFileUpload(String urlString, String vTitle, String vDesc, Context context){
        try{
            connectURL = new URL(urlString);
            Title= vTitle;
            Description = vDesc;
            this.context=context;
        }catch(Exception ex){
            Log.i("HttpFileUpload","URL Malformatted");
        }
    }

    public void Send_Now(FileInputStream fStream){
        fileInputStream = fStream;
        Sending();
    }

    void Sending(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String iFileName = "ovicam_temp_vid.mp4";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String Tag="fSnd";
        try
        {
            Log.e(Tag,"Starting Http File Sending to URL");

            // Open a HTTP connection to the URL
            HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection();

            // Allow Inputs
            conn.setDoInput(true);

            // Allow Outputs
            conn.setDoOutput(true);

            // Don't use a cached copy.
            conn.setUseCaches(false);

            // Use a post method.
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Connection", "Keep-Alive");

            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"title\""+ lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Title);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"description\""+ lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Description);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + Description +"\"" + lineEnd);
            dos.writeBytes(lineEnd);

            Log.e(Tag,"Headers are written");

            // create a buffer of maximum size
            int bytesAvailable = fileInputStream.available();

            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[ ] buffer = new byte[bufferSize];

            // read file and write it into form...
            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0)
            {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0,bufferSize);
            }
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            fileInputStream.close();

            dos.flush();

            Log.e(Tag,"File Sent, Response: "+String.valueOf(conn.getResponseCode()));



            InputStream is = conn.getInputStream();

            // retrieve the response from server
            int ch;

            StringBuffer b =new StringBuffer();
            while( ( ch = is.read() ) != -1 ){ b.append( (char)ch ); }
            String s=b.toString();
            Log.i("Response",s);
          //  Teacher_upload_files.x.cancel();
            dos.close();
            if(String.valueOf(conn.getResponseCode()).equals("200"))
            {
                Teacher_upload_files.x.cancel();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                       Toast.makeText(context,"تم رفع الملف ",Toast.LENGTH_LONG).show();
                        ((Activity)context).finish();
                        ((Activity)context).startActivity(new Intent(context,Teacher_upload_files.class));
                    }
                });


            }
            else
            {
                Teacher_upload_files.x.cancel();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"حدث خطا في الرفع تاكد من امتداد الملف ",Toast.LENGTH_LONG).show();
                        ((Activity)context).finish();
                        ((Activity)context).startActivity(new Intent(context,Teacher_upload_files.class));
                    }
                });
            }

        }
        catch (MalformedURLException ex)
        {
            Log.e(Tag, "URL error: " + ex.getMessage(), ex);
            Teacher_upload_files.x.cancel();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"خطأ ",Toast.LENGTH_LONG).show();
                    ((Activity)context).finish();
                    ((Activity)context).startActivity(new Intent(context,Teacher_upload_files.class));
                }
            });
        }

        catch (IOException ioe)
        {
            Log.e(Tag, "IO error: " + ioe.getMessage(), ioe);
            Teacher_upload_files.x.cancel();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"خطأ ",Toast.LENGTH_LONG).show();
                    ((Activity)context).finish();
                    ((Activity)context).startActivity(new Intent(context,Teacher_upload_files.class));
                }
            });
        }
    }
}).start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
    }

}



