package com.am.ahmad.Basics_class;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.am.ahmad.R;
import com.am.ahmad.Teacher_list;
import com.am.ahmad.Teacher_profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ahmad on 4/5/2018.
 */

public class MyCustomerAdapterTeacherList extends BaseAdapter implements ListAdapter {
    private ArrayList<String> teacher_name = new ArrayList<String>();
    private ArrayList<String> courseName = new ArrayList<String>();
    private ArrayList<String> teacher_pic = new ArrayList<String>();
    private ArrayList<String> teacher_exp = new ArrayList<String>();
    int [] teacher_id,star ;
    double [] lat,lon;
    int s;
    Context context;


    public MyCustomerAdapterTeacherList(ArrayList<String> teacher_name, ArrayList<String> courseName, ArrayList<String> teacher_pic, ArrayList<String> teacher_exp, int[] teacher_id, int[] star, double[] latitude, double[] longitude,int s,Context context) {
    this.teacher_name=teacher_name;
    this.teacher_exp=teacher_exp;
    this.courseName=courseName;
    this.teacher_pic=teacher_pic;
    this.lat=latitude;
    this.lon=longitude;
    this.teacher_id=teacher_id;
    this.context=context;
    this.star=star;
    this.s=s;

    }

    @Override
    public int getCount() {
        return teacher_name.size();
    }

    @Override
    public Object getItem(int pos) {
        return teacher_name.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.teacher_list_listviw, null);
        }
        ImageView teacherPic = (ImageView)view.findViewById(R.id.agentImage) ;
        TextView teacherName = (TextView) view.findViewById(R.id.agent_name);
        TextView CourseName = (TextView) view.findViewById(R.id.agent_text);
        RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar_t);
        TextView TeacherExp = (TextView) view.findViewById(R.id.text_descId);
        ratingBar.setNumStars(5);
        Picasso.with(context).load(teacher_pic.get(position)).into(teacherPic);
        teacherName.setText(teacher_name.get(position));
        CourseName.setText(courseName.get(position));
        TeacherExp.setText(teacher_exp.get(position));
        ratingBar.setRating((star[position]));
        ratingBar.setEnabled(false);

        teacherPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Teacher_profile.class);
                intent.putExtra("teacher_id",teacher_id[position]);
                intent.putExtra("s",s);
                context.startActivity(intent);

            }
        });
        teacherName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Teacher_profile.class);
                intent.putExtra("teacher_id",teacher_id[position]);
                intent.putExtra("s",s);

                context.startActivity(intent);
            }
        });
        CourseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Teacher_profile.class);
                intent.putExtra("teacher_id",teacher_id[position]);
                intent.putExtra("s",s);

                context.startActivity(intent);
            }
        });
        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Teacher_profile.class);
                intent.putExtra("teacher_id",teacher_id[position]);
                intent.putExtra("s",s);

                context.startActivity(intent);
            }
        });
        TeacherExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Teacher_profile.class);
                intent.putExtra("teacher_id",teacher_id[position]);
                intent.putExtra("s",s);

                context.startActivity(intent);
            }
        });




        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
