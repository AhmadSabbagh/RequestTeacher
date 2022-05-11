package com.am.ahmad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class All_noti_details extends AppCompatActivity {
TextView name,desc;
String n,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_noti_details);
        name=(TextView) findViewById(R.id.name);
        desc=(TextView) findViewById(R.id.desc);
        Intent intent =getIntent();
        n=intent.getStringExtra("name");
        d=intent.getStringExtra("desc");
        name.setText(n);
        desc.setText(d);




    }
}
