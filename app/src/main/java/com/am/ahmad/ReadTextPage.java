package com.am.ahmad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.am.ahmad.Basics_class.AttolSharedPreference;
import com.am.ahmad.Basics_class.FcmMessage;

import me.leolin.shortcutbadger.ShortcutBadger;

public class ReadTextPage extends AppCompatActivity {
TextView titleTV,bodyTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_text_page);
        titleTV=(TextView)findViewById(R.id.title) ;
        bodyTV=(TextView)findViewById(R.id.body) ;

        AttolSharedPreference attolSharedPreference = new AttolSharedPreference(ReadTextPage.this);
        String title=  attolSharedPreference.getKey("title");
        String body =  attolSharedPreference.getKey("body" );

        titleTV.setText(title);
        bodyTV.setText(body);
        ShortcutBadger.applyCount(this, 0); //for 1.1.4+
        attolSharedPreference.setBudge1("budge",0);


    }
}
