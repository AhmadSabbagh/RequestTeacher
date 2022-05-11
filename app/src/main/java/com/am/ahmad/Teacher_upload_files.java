package com.am.ahmad;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.am.ahmad.Basics_class.AttolSharedPreference;
import com.am.ahmad.Basics_class.FilePath;
import com.am.ahmad.Basics_class.HttpFileUpload;
import com.am.ahmad.Basics_class.HttpRequestSender;
import com.am.ahmad.Basics_class.MyApplication;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;



import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;


import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

import static java.io.FileDescriptor.in;


public class Teacher_upload_files extends AppCompatActivity {
    Uri uri;
    String PdfNameHolder, PdfPathHolder, PdfID;
    File myFile , myFile2;
    Date date = new Date();
    String displayName;
    TableView<String[]> tableView;
    int [] files_id;
    int state;
    String result_reg_face1;
   public static ProgressDialog x;
    String []files;

    String[][] spaceProbes;
    String CustomFileName;
    static String[] spaceProbeHeaders={"مسح / تحميل","اسم","PDF"};

    public static String BASE_URL = "http://37.48.72.231/file.aspx";
    static final int PICK_IMAGE_REQUEST = 1;
    String filePath;
    String NAME;
    EditText FileNameEt;
    String displayfileName;
    TextView fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_upload_files);
        tableView = (TableView<String[]>) findViewById(R.id.tableView);
        AllowRunTimePermission();
        displayName = String.valueOf(date.getTime());
        fileName=(TextView)findViewById(R.id.FIleName);
        x = new ProgressDialog(this);
        tableView.setHeaderBackgroundColor(Color.parseColor("#161AE5"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,spaceProbeHeaders));
        tableView.setColumnCount(3);
        Date date= new Date();
       // FileNameEt=(EditText)findViewById(R.id.fileName) ;
        fileName.setText("اسم الملف");

        getFiles();


        tableView.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(final int rowIndex, Object clickedData) {
                // Toast.makeText(Teacher_profile.this, ((String[])clickedData)[1], Toast.LENGTH_SHORT).show();
               // tableChose=rowIndex;

                AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_upload_files.this);
                a_builder.setMessage(" هل تريد مسح / تحميل الملف ؟ ")
                        .setCancelable(true)
                        .setNegativeButton("تحميل", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(files[rowIndex]));
                                startActivity(i);
                            }
                        })
                        .setPositiveButton("مسح", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(files[rowIndex]));
//                                startActivity(browserIntent);
                                removeFile(rowIndex);
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("شكرا :) ");
                alert.show();

            }
        });
    }
    public void getFiles() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_upload_files.this);
                    result_reg_face1 = http.SEND_get_files("https://ssyoutube.com/watch?v=ZNG2u4wFtFM&list=RDuw_UFqaGetk&index=2"
                             );//here same fun of majority
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
                            JSONArray JA ;
                            JA=new JSONArray(result_reg_face1);
                            files  =new String[JA.length()];
                            files_id= new int [JA.length()];
                            spaceProbes = new String [JA.length()][spaceProbeHeaders.length];

                            for (int i = 0; i < JA.length(); i++) {

                                JSONObject JO = (JSONObject) JA.get(i);
                                files[i] = "http://62.212.88.104/file/"+(String) JO.get("name");
                                files_id[i]= (int) JO.get("id");
                                spaceProbes [i][0]="مسح / تحميل";

                                spaceProbes [i][1]=(String) JO.get("name");

                                spaceProbes [i][2]="PDF";




                            }
                            tableView.setDataAdapter(new SimpleTableDataAdapter(Teacher_upload_files.this, spaceProbes));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void removeFile(final int id) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(Teacher_upload_files.this);
                    result_reg_face1 = http.SEND_delete_files("http://62.212.88.104/dal/API.asmx/delete_file?"
                    ,files_id[id]);//here same fun of majority
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
                    if (result_reg_face1.contains("succ")) {
                        //  x.cancel();
                      Toast.makeText(Teacher_upload_files.this,"تم مسح الملف بنجاح",Toast.LENGTH_LONG).show();
                      finish();
                      startActivity(new Intent(Teacher_upload_files.this,Teacher_upload_files.class));

                    }
                    else
                    {
                        Toast.makeText(Teacher_upload_files.this,"حصل خطأ",Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(Teacher_upload_files.this,Teacher_upload_files.class));

                    }


                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public   void show() {

        x.setMessage("جارٍ الرفع...");
        x.setCancelable(true);
        x.show();
    }

    public void chose(View view) {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        a_builder.setMessage("pdf الرجاء تحميل ملفات من نوع  ")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                        imageBrowse();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("رفع ملف");
        alert.show();

//
//        Intent intent = new Intent();
//        intent.putExtra("CONTENT_TYPE", "*/*");
//        intent.setAction("com.sec.android.app.myfiles.PICK_DATA_MULTIPLE");
//        Uri uri = Uri.parse("storage/emulated/0/Documents");
//        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), 1);
//
////        String path = Environment.getExternalStorageDirectory().toString()+"/Documents";
////        Intent selectFile = new Intent();
////        selectFile.setAction("com.sec.android.app.myfiles.PICK_DATA_MULTIPLE");
////       // selectFile.putExtra("CONTENT_TYPE", "*/*");
////      //  selectFile.addCategory(Intent.CATEGORY_DEFAULT);
////        selectFile.setDataAndType(uri, "*/*");
//
//
//        startActivityForResult(intent, 1);


    }
    private void imageBrowse() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        // Start the Intent
//        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
/*
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_IMAGE_REQUEST);
        */
        int PICKFILE_RESULT_CODE=1;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent,PICKFILE_RESULT_CODE);
    }
    public String getPathfromURI(Uri uri) {
        try {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            //int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
           // String path = cursor.getString(column_index);
            displayfileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

            cursor.close();
            return displayfileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if(requestCode == PICK_IMAGE_REQUEST){
                Uri picUri = data.getData();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    filePath = getPath(this,picUri);
                }


                NAME = getPathfromURI(picUri);
                AlertDialog.Builder a_builder = new AlertDialog.Builder(Teacher_upload_files.this);
                a_builder.setMessage("اسم الملف :"+NAME)
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                 fileName.setText(NAME);
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("شكرا :) ");
                alert.show();

                Log.d("picUri", picUri.toString());
              //  Log.d("filePath", filePath);

             //   imageView.setImageURI(picUri);

            }

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
       // x.cancel();
    }
    @Override
    public void onBackPressed() {
     x.cancel();
            super.onBackPressed();

    }
/*
    private int uploadFile(final String filePath1)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection uploadConnection = null;
                DataOutputStream outputStream;
                String boundary = "********";
                String CRLF = "\r\n";
                String Hyphens = "--";
                int bytesRead, bytesAvailable, bufferSize;
                int maxBufferSize = 1024 * 1024;
                byte[] buffer;
                File ourFile = new File(filePath1);

                try {
                    FileInputStream fileInputStream = new FileInputStream(ourFile);
                    URL url = new URL("http://37.48.72.231/file.aspx");
                    uploadConnection = (HttpURLConnection) url.openConnection();
                    uploadConnection.setDoInput(true);
                    uploadConnection.setDoOutput(true);
                    uploadConnection.setRequestMethod("POST");

                    uploadConnection.setRequestProperty("Connection", "Keep-Alive");
                    uploadConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    uploadConnection.setRequestProperty("uploaded_file", filePath);

                    outputStream = new DataOutputStream(uploadConnection.getOutputStream());

                    outputStream.writeBytes(Hyphens + boundary + CRLF);
                    outputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + filePath + "\"" + CRLF);
                    outputStream.writeBytes(CRLF);

                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        outputStream.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    outputStream.writeBytes(CRLF);
                    outputStream.writeBytes(Hyphens + boundary + Hyphens + CRLF);

                    InputStreamReader resultReader = new InputStreamReader(uploadConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(resultReader);
                    ;
                    String line = "";
                    String response = "";
                    while ((line = reader.readLine()) != null) {
                        response += line;
                    }

                    final String finalResponse = response;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          //  responseTextView.setText(finalResponse);
                            if(finalResponse.equals("Success"))
                            {
                                state=1;
                                Toast.makeText(getApplicationContext(), "شكرا تم رفع الملف بنجاح", Toast.LENGTH_LONG).show();
                                x.cancel();
                                finish();


                            }
                            else
                            {
                                state=0;
                                Toast.makeText(getApplicationContext(), "حدث خطا حاول مرة اخرى", Toast.LENGTH_LONG).show();
                                x.cancel();
                                finish();
                            }
                        }
                    });


                    fileInputStream.close();
                    outputStream.flush();
                    outputStream.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        }).start();
return state;
    }


    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void imageUpload(final String imagePath) {

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String message = jObj.getString("message");

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }




        });

        smr.addFile("image", imagePath);
        MyApplication.getInstance().addToRequestQueue(smr);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1212:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    myFile = new File(uriString);
                    String path1 = myFile.getAbsolutePath();
                    myFile2 = new File(path1);
//                    String base = encodeFileToBase64Binary(myFile);
//                    String base1 = encodeFileToBase64Binary(myFile2);


                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = Teacher_upload_files.this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                File path = Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_DOCUMENTS);
                                File file1 = new File(path1, displayName +".pdf");
                                String base = encodeFileToBase64Binary(file1);

                                try {
                                    // Make sure the Pictures directory exists.
                                    path.mkdirs();

                                    file1.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }
                }
                break;
            case 1:
                String picturePath;
                URI i;
                String selectedImage ;
               data=getIntent();
               selectedImage=data.getStringExtra("CONTENT_TYPE");
                try {

                     i = new URI(selectedImage);
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(i, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    File file=new File(picturePath);
                    break;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

*/
    public void AllowRunTimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Teacher_upload_files.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            Toast.makeText(Teacher_upload_files.this, "READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(Teacher_upload_files.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {

        switch (RC) {

            case 1:

                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {

               //     Toast.makeText(Teacher_upload_files.this, "Permission Granted", Toast.LENGTH_LONG).show();

                } else {

              //      Toast.makeText(Teacher_upload_files.this, "Permission Canceled", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public void upload(View view) {
        //uploadFile("http://internetfaqs.net/AndroidPdfUpload/upload.php",myFile);
        if (filePath != null ) {
            show();
             //uploadFile(filePath);
            UploadFile();

        } else {
            Toast.makeText(getApplicationContext(), "الملف خاطئ حاول تجنب رفع الملف من خارج الموبايل مثل الدرايف", Toast.LENGTH_LONG).show();
        }
    }
    public void UploadFile(){
        try {
            // Set your file path here
            File file=new File(filePath);
            FileInputStream fstrm = new FileInputStream(file);
            AttolSharedPreference attolSharedPreference = new AttolSharedPreference(this);
            String id = attolSharedPreference.getKey("id");
            // Set your server page url (and the file title/description)
            HttpFileUpload hfu = new HttpFileUpload("http://62.212.88.104/file2.aspx", id,NAME,Teacher_upload_files.this);

            hfu.Send_Now(fstrm);

        } catch (FileNotFoundException e) {
            // Error: File not found
        }
    }
    /*
    public static void  print (int state,Context context)
    {
        if(state==1)
        {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
            a_builder.setMessage("شكرا تم رفع الملف")
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
            Toast.makeText(context, "عفوا حصل خطأ!", Toast.LENGTH_LONG).show();

        }
    }
    */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
/*
    public Boolean uploadFile(String serverURL, File file) {



            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("variable", displayName,
                            RequestBody.create(MediaType.parse("pdf"), file))
                    .addFormDataPart("key", "")


                    .build();
            Request request = new Request.Builder()
                    .url(serverURL)
                    .post(requestBody)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("Registration Error" + e.getMessage());
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {

                    try {
                        String resp = response.body().string();
                        Log.v("Docs", resp);

                    } catch (IOException e) {
                        System.out.println("Exception caught" + e.getMessage());
                    }
                }

            });

            return false;

    }
    private String encodeFileToBase64Binary(File file){
        String encodedfile = null;
        try {
            file.mkdirs();
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = Base64.encodeBase64(bytes).toString();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch <span id="IL_AD2" class="IL_AD">block
            Log.e("error",e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }
    */
}

