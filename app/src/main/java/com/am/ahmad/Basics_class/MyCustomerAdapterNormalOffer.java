package com.am.ahmad.Basics_class;

/**
 * Created by ahmad on 4/13/2018.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.am.ahmad.R;
import com.am.ahmad.Teacher_page;
import com.am.ahmad.about_us;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class MyCustomerAdapterNormalOffer extends BaseAdapter implements ListAdapter {
    private ArrayList<String> url = new ArrayList<String>();//offer pic
    private ArrayList<String> desc = new ArrayList<String>();//offer pic
    int [] id ;
    String [] video;

    int [] AgentId;
    Context context;
    String result_Un;






    public MyCustomerAdapterNormalOffer(ArrayList<String> url,ArrayList<String>  desc ,int [] id,Context context ,String [] video) {
        this.url = url;
        this.desc=desc;
        this.context = context;
        this.id=id;
        this.video=video;


    }


    @Override
    public int getCount() {
        return url.size();
    }

    @Override
    public Object getItem(int pos) {
        return url.get(pos);
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
            view = inflater.inflate(R.layout.normal_offers_list, null);
        }
        ImageView offerPic = (ImageView)view.findViewById(R.id.offerPic) ;
        VideoView  videoView=(VideoView)view.findViewById(R.id.vidious) ;

        if(video[position].equals("http://37.48.72.231/vidio/"))
        {
            videoView.setVisibility(View.INVISIBLE);
        }
        else {
            videoView.setVisibility(View.VISIBLE);
            MediaController mediaController;
            Uri uri = Uri.parse(video[position]);
            mediaController = new MediaController(context);
            videoView.setVideoURI(uri);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            videoView.pause();
        }

        Picasso.with(context).load(url.get(position)).into(offerPic);

        TextView descText = (TextView) view.findViewById(R.id.offer_desc);
        descText.setText(desc.get(position));


        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}