package com.am.ahmad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class SpecialOffersDetails extends AppCompatActivity {
ImageView img;
TextView txt;
String image,text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_offers_details);
        img=(ImageView)findViewById(R.id.img);
        txt=(TextView)findViewById(R.id.txt);
        Intent intent=getIntent();
        image=intent.getStringExtra("img");
        text=intent.getStringExtra("txt");
        txt.setText(text);
        Picasso.with(this).load(image).into(img);

    }
}
