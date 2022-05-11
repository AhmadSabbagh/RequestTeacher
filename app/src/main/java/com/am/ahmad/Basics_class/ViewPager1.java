package com.am.ahmad.Basics_class;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.am.ahmad.R;
import com.am.ahmad.Welcome;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewPager1 extends PagerAdapter {

    ImageView imageView;



    private Context context;
    private LayoutInflater layoutInflater;
    private String[] img ;

    public ViewPager1(Context context, String [] img) {
        this.context = context ;
        this.img=img;


    }

    public ViewPager1(final Context context) {
        this.context = context;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("myref");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("no"))
                {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(""));
                    context.startActivity(i);
                   // context.startActivity(new Intent(context,Welcome.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.view_image_slider1,null);

        imageView =(ImageView)view.findViewById(R.id.imageView_id);
      //  imageView.setImageResource(img[position]);
        Picasso.with(context).load(img[position]).into(imageView);



      ViewPager viewPager_1 =(ViewPager) container;
        viewPager_1.addView(view, 0);

        return  view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager viewPager_2 =(ViewPager)container;
        View view = (View) object;
        viewPager_2.removeView(view);

    }


}
