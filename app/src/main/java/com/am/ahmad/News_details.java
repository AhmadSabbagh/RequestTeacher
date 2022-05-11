package com.am.ahmad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class News_details extends AppCompatActivity {
String text,pic;
ImageView imageView;
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        Intent intent=getIntent();
        text= intent.getStringExtra("text");
        pic=intent.getStringExtra("pic");
        imageView=(ImageView)findViewById(R.id.newsPic);
        textView=(TextView) findViewById(R.id.newsText);
        textView.setText(text);
        Picasso.with(News_details.this).load(pic).into(imageView);

    }
}
