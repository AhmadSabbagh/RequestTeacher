package com.am.ahmad;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.am.ahmad.Basics_class.HttpRequestSender;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class about_us extends AppCompatActivity {
TextView about_us_text;
String result_reg_face;
ImageView photo;
    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer player;
    DataSource.Factory data_source_factory ;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        about_us_text=(TextView) findViewById(R.id.about_us);
        photo=(ImageView) findViewById(R.id.photo);
        simpleExoPlayerView=(SimpleExoPlayerView)findViewById(R.id.vidious) ;

        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector(new DefaultBandwidthMeter.Builder().build()));
         data_source_factory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "Application Name"), new DefaultBandwidthMeter());

        about_us_info();

    }

    public void about_us_info() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(about_us.this);
                    result_reg_face = http.getOffersPics1("http://62.212.88.104/dal/API.asmx/getdata_About_us");
                    Log.d("String", result_reg_face);
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
                if(result_reg_face!=null) {
                    if (result_reg_face.contains("about_us")) {

                        try {
                            JSONObject jsonObject = new JSONObject(result_reg_face);
                            String text=jsonObject.getString("about_us");
                            String pic="http://37.48.72.231/picture/"+jsonObject.getString("picture");
                            String video="http://37.48.72.231/vidio/"+jsonObject.getString("vidio");
                            about_us_text.setText(text);
                            Picasso.with(about_us.this).load(pic).into(photo);
                            if( !Patterns.WEB_URL.matcher(video).matches()
                                    )
                            {
                                simpleExoPlayerView.setVisibility(View.INVISIBLE);
                            }
                            else {

                                MediaSource mediaSource = new ExtractorMediaSource.Factory(data_source_factory).createMediaSource(Uri.parse(video));

                                player.addListener(new Player.EventListener() {
                                    @Override
                                    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
                                        Log.d("exo", "timeLine Changed");
                                    }

                                    @Override
                                    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                                    }

                                    @Override
                                    public void onLoadingChanged(boolean isLoading) {
                                        Log.d("exo", "loding changed= " + isLoading);
                                    }

                                    @Override
                                    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                                        Log.d("exo", "state changed");
                                    }

                                    @Override
                                    public void onRepeatModeChanged(int repeatMode) {

                                    }

                                    @Override
                                    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                                    }

                                    @Override
                                    public void onPlayerError(ExoPlaybackException error) {
                                        Log.e("exo", "exoplayer error", error);
                                    }

                                    @Override
                                    public void onPositionDiscontinuity(int reason) {

                                    }

                                    @Override
                                    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                                    }

                                    @Override
                                    public void onSeekProcessed() {
                                        Log.d("exo", "seek processed");
                                    }
                                });
                                player.prepare(mediaSource);

                                simpleExoPlayerView.setPlayer(player);

                                player.setPlayWhenReady(true);
                            }
                            /////





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }

                }

                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    @Override
    public void onResume() {
        super.onResume();
        // player.init(this, playerView);
    }

    @Override
    public void onPause() {
        super.onPause();
        // player.reset();
    }

    @Override
    public void onDestroy() {
        // player.release();
        player.setPlayWhenReady(false);
//        player.stop();

        super.onDestroy();
    }


}
