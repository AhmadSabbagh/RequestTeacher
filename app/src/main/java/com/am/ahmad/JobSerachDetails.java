package com.am.ahmad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class JobSerachDetails extends AppCompatActivity {
String title,desc,url;
TextView Title,Descr;
ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_serach_details);
        Intent intent = getIntent();
        title=intent.getStringExtra("title");
        desc=intent.getStringExtra("desc");
        url=intent.getStringExtra("url");

        Title=(TextView)findViewById(R.id.Title);
        Descr=(TextView)findViewById(R.id.desc);
        imageView=(ImageView) findViewById(R.id.img);

        Title.setText(title);
        Descr.setText(desc);
        Picasso.with(this).load(url).into(imageView);


    }
}
