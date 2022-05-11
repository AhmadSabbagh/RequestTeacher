package com.am.ahmad;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.am.ahmad.Basics_class.PlayerManager;
import com.bumptech.glide.Glide;
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
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZUserAction;
import cn.jzvd.JZUserActionStd;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class NormalOfferDetails extends AppCompatActivity {
String pic,video,offer_name,desc;
TextView Name,Desc;
    JzvdStd Vedio;
ImageView Image;
    private int mCurrentPosition = 0;
    Uri uri;
    SimpleExoPlayer player;
    // Tag for the instance state bundle.

    private static final String PLAYBACK_TIME = "play_time";
    SimpleExoPlayerView simpleExoPlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_offer_details);
        Intent intent = getIntent();
        pic = intent.getStringExtra("pic");
        video = intent.getStringExtra("video");
        offer_name = intent.getStringExtra("offer_name");
        desc = intent.getStringExtra("desc");

        Name = (TextView) findViewById(R.id.offer_name);
        Desc = (TextView) findViewById(R.id.offer_desc);
        Image = (ImageView) findViewById(R.id.offerPic);
         simpleExoPlayerView = findViewById(R.id.vidious);
        Name.setText(offer_name);
        Desc.setText(desc);

        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector(new DefaultBandwidthMeter.Builder().build()));
        DataSource.Factory data_source_factory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "Application Name"), new DefaultBandwidthMeter());

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
          //  player = new PlayerManager(this);
//
//            JZDataSource jzDataSource = null;
//            jzDataSource= new JZDataSource("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4");
//            Vedio.setUp(jzDataSource, JzvdStd.SCREEN_WINDOW_NORMAL);

//            Vedio.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
//                    , "饺子快长大", JzvdStd.SCREEN_WINDOW_NORMAL);
//            Glide.with(this).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(Vedio.thumbImageView);
//            Jzvd.setJzUserAction(new MyUserActionStd());

//            JzvdStd.startFullscreen(this, JzvdStd.class, video, "饺子辛苦了");



          //  Vedio.thumbImageView.set("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
//            MediaController mediaController;
//             uri= Uri.parse(video);
//            mediaController=new MediaController(NormalOfferDetails.this);
//            Vedio.setVideoURI(uri);
//            Vedio.setMediaController(mediaController);
//            mediaController.setAnchorView(Vedio);
//            Vedio.pause();

//            Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
//            intent1.setDataAndType(uri, "video/mp4");
//            startActivity(intent1);

//            String url = "http://........"; // your URL here
//            MediaPlayer mediaPlayer = new MediaPlayer();
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            try {
//                mediaPlayer.setDataSource(video);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                mediaPlayer.prepare(); // might take long! (for buffering, etc)
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            mediaPlayer.start();


        }


        Picasso.with(this).load(pic).into(Image);
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
