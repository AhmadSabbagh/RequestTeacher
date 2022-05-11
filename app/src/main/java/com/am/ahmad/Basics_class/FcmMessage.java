package com.am.ahmad.Basics_class;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;

import com.am.ahmad.Admin_page;
import com.am.ahmad.NormalOffers;
import com.am.ahmad.R;
import com.am.ahmad.ReadTextPage;
import com.am.ahmad.Student_Reg;
import com.am.ahmad.Student_Requstes;
import com.am.ahmad.Teacher_Notifications;
import com.am.ahmad.Teacher_page;
import com.am.ahmad.student_page;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by ahmad on 4/22/2018.
 */

public class FcmMessage extends FirebaseMessagingService {
    String result_info;
    Boolean status;
    String title,body,type;
    static  int id=0;
    Date date ;
    int badgeCount ;
    String CHANNEL_ID = "my_channel_01";// The id of the channel.

    private NotificationManager notifManager;
    private NotificationChannel mChannel;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        date=new Date();
        super.onMessageReceived(remoteMessage);
        Log.e("Response >>>>", new Gson().toJson(remoteMessage.getData()));
        Log.e("response2 >>>>", new Gson().toJson(remoteMessage.getNotification()));
     //   getNotificationStatus(remoteMessage);
         title = remoteMessage.getData().get("title");
         body = remoteMessage.getData().get("body");
         type = remoteMessage.getData().get("type");
////////////////////////////////////////////////////////////////////////////////////////////////////
       AttolSharedPreference noti = new AttolSharedPreference(FcmMessage.this);
           badgeCount=noti.getBudge("budge");
           noti.setBudge("budge",badgeCount+1);
           badgeCount++;
           ShortcutBadger.applyCount(FcmMessage.this, badgeCount); //for 1.1.4+












        ////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            String yourAwesomeUnicodeString=URLDecoder.decode(remoteMessage.getData().get("title"),"UTF-8");
            Log.e("null >>>>", yourAwesomeUnicodeString);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        AttolSharedPreference attolSharedPreference = new AttolSharedPreference(FcmMessage.this);
      String me=  attolSharedPreference.getKey1("me");
      attolSharedPreference.setKey1("title",title);
      attolSharedPreference.setKey1("body",body);

        if(me ==null) {
        //  Log.e("null >>>>", me);
      }
        else {
          if(me.equals("admin")) {
              if(type.equals("1")) {
                  Intent intent;
                  PendingIntent pendingIntent;
                  NotificationCompat.Builder builder;
                  if (notifManager == null) {
                      notifManager = (NotificationManager) getSystemService
                              (Context.NOTIFICATION_SERVICE);
                  }

                  intent = new Intent (this, Admin_page.class);
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                      int importance = NotificationManager.IMPORTANCE_HIGH;
                      if (mChannel == null) {
                          NotificationChannel mChannel = new NotificationChannel
                                  ("0", title, importance);
                          mChannel.setDescription (body);
                          mChannel.enableVibration (true);
                          mChannel.setVibrationPattern (new long[]
                                  {100, 200, 300, 400, 500, 400, 300, 200, 400});
                          notifManager.createNotificationChannel (mChannel);
                      }
                      builder = new NotificationCompat.Builder (this, "0");

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)  // flare_icon_30
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))
                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400,
                                      500, 400, 300, 200, 400});
                  } else {

                      builder = new NotificationCompat.Builder (this);

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))

                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400, 500,
                                      400, 300, 200, 400})
                              .setPriority (Notification.PRIORITY_HIGH);
                  } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  Notification notification = builder.build ();
                  notifManager.notify((int)date.getTime(), notification);
              }
              else
              {
                  Intent intent;
                  PendingIntent pendingIntent;
                  NotificationCompat.Builder builder;
                  if (notifManager == null) {
                      notifManager = (NotificationManager) getSystemService
                              (Context.NOTIFICATION_SERVICE);
                  }

                  intent = new Intent (this, ReadTextPage.class);
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                      int importance = NotificationManager.IMPORTANCE_HIGH;
                      if (mChannel == null) {
                          NotificationChannel mChannel = new NotificationChannel
                                  ("0", title, importance);
                          mChannel.setDescription (body);
                          mChannel.enableVibration (true);
                          mChannel.setVibrationPattern (new long[]
                                  {100, 200, 300, 400, 500, 400, 300, 200, 400});
                          notifManager.createNotificationChannel (mChannel);
                      }
                      builder = new NotificationCompat.Builder (this, "0");

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)  // flare_icon_30
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))
                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400,
                                      500, 400, 300, 200, 400});
                  } else {

                      builder = new NotificationCompat.Builder (this);

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))

                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400, 500,
                                      400, 300, 200, 400})
                              .setPriority (Notification.PRIORITY_HIGH);
                  } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  Notification notification = builder.build ();
                  notifManager.notify((int)date.getTime(), notification);
              }




          }
        else  if(me.equals("student")) {
              if(type.equals("1"))
              {
                  Intent intent;
                  PendingIntent pendingIntent;
                  NotificationCompat.Builder builder;
                  if (notifManager == null) {
                      notifManager = (NotificationManager) getSystemService
                              (Context.NOTIFICATION_SERVICE);
                  }

                  intent = new Intent (this, Student_Requstes.class);
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                      int importance = NotificationManager.IMPORTANCE_HIGH;
                      if (mChannel == null) {
                          NotificationChannel mChannel = new NotificationChannel
                                  ("0", title, importance);
                          mChannel.setDescription (body);
                          mChannel.enableVibration (true);
                          mChannel.setVibrationPattern (new long[]
                                  {100, 200, 300, 400, 500, 400, 300, 200, 400});
                          notifManager.createNotificationChannel (mChannel);
                      }
                      builder = new NotificationCompat.Builder (this, "0");

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)  // flare_icon_30
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))
                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400,
                                      500, 400, 300, 200, 400});
                  } else {

                      builder = new NotificationCompat.Builder (this);

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))

                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400, 500,
                                      400, 300, 200, 400})
                              .setPriority (Notification.PRIORITY_HIGH);
                  } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  Notification notification = builder.build ();
                  notifManager.notify((int)date.getTime(), notification);


              }

              else if(type.equals("2"))
              {
                  Intent intent;
                  PendingIntent pendingIntent;
                  NotificationCompat.Builder builder;
                  if (notifManager == null) {
                      notifManager = (NotificationManager) getSystemService
                              (Context.NOTIFICATION_SERVICE);
                  }

                  intent = new Intent (this, ReadTextPage.class);
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                      int importance = NotificationManager.IMPORTANCE_HIGH;
                      if (mChannel == null) {
                          NotificationChannel mChannel = new NotificationChannel
                                  ("0", title, importance);
                          mChannel.setDescription (body);
                          mChannel.enableVibration (true);
                          mChannel.setVibrationPattern (new long[]
                                  {100, 200, 300, 400, 500, 400, 300, 200, 400});
                          notifManager.createNotificationChannel (mChannel);
                      }
                      builder = new NotificationCompat.Builder (this, "0");

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)  // flare_icon_30
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))
                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400,
                                      500, 400, 300, 200, 400});
                  } else {

                      builder = new NotificationCompat.Builder (this);

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))

                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400, 500,
                                      400, 300, 200, 400})
                              .setPriority (Notification.PRIORITY_HIGH);
                  } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  Notification notification = builder.build ();
                  notifManager.notify((int)date.getTime(), notification);
              }

              else if(type.equals("3"))
              {
                  Intent intent;
                  PendingIntent pendingIntent;
                  NotificationCompat.Builder builder;
                  if (notifManager == null) {
                      notifManager = (NotificationManager) getSystemService
                              (Context.NOTIFICATION_SERVICE);
                  }

                  intent = new Intent (this, NormalOffers.class);
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                      int importance = NotificationManager.IMPORTANCE_HIGH;
                      if (mChannel == null) {
                          NotificationChannel mChannel = new NotificationChannel
                                  ("0", title, importance);
                          mChannel.setDescription (body);
                          mChannel.enableVibration (true);
                          mChannel.setVibrationPattern (new long[]
                                  {100, 200, 300, 400, 500, 400, 300, 200, 400});
                          notifManager.createNotificationChannel (mChannel);
                      }
                      builder = new NotificationCompat.Builder (this, "0");

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)  // flare_icon_30
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))
                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400,
                                      500, 400, 300, 200, 400});
                  } else {

                      builder = new NotificationCompat.Builder (this);

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))

                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400, 500,
                                      400, 300, 200, 400})
                              .setPriority (Notification.PRIORITY_HIGH);
                  } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  Notification notification = builder.build ();
                  notifManager.notify((int)date.getTime(), notification);
              }

              else if(type.equals("4"))
              {
                  Intent intent;
                  PendingIntent pendingIntent;
                  NotificationCompat.Builder builder;
                  if (notifManager == null) {
                      notifManager = (NotificationManager) getSystemService
                              (Context.NOTIFICATION_SERVICE);
                  }

                  intent = new Intent (this, student_page.class);
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                      int importance = NotificationManager.IMPORTANCE_HIGH;
                      if (mChannel == null) {
                          NotificationChannel mChannel = new NotificationChannel
                                  ("0", title, importance);
                          mChannel.setDescription (body);
                          mChannel.enableVibration (true);
                          mChannel.setVibrationPattern (new long[]
                                  {100, 200, 300, 400, 500, 400, 300, 200, 400});
                          notifManager.createNotificationChannel (mChannel);
                      }
                      builder = new NotificationCompat.Builder (this, "0");

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)  // flare_icon_30
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))
                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400,
                                      500, 400, 300, 200, 400});
                  } else {

                      builder = new NotificationCompat.Builder (this);

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))

                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400, 500,
                                      400, 300, 200, 400})
                              .setPriority (Notification.PRIORITY_HIGH);
                  } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  Notification notification = builder.build ();
                  notifManager.notify((int)date.getTime(), notification);
              }
          }
        else  if(me.equals("teacher")) {
              if(type.equals("1")) {
                  Intent intent;
                  PendingIntent pendingIntent;
                  NotificationCompat.Builder builder;
                  if (notifManager == null) {
                      notifManager = (NotificationManager) getSystemService
                              (Context.NOTIFICATION_SERVICE);
                  }

                  intent = new Intent (this, Teacher_Notifications.class);
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                      int importance = NotificationManager.IMPORTANCE_HIGH;
                      if (mChannel == null) {
                          NotificationChannel mChannel = new NotificationChannel
                                  ("0", title, importance);
                          mChannel.setDescription (body);
                          mChannel.enableVibration (true);
                          mChannel.setVibrationPattern (new long[]
                                  {100, 200, 300, 400, 500, 400, 300, 200, 400});
                          notifManager.createNotificationChannel (mChannel);
                      }
                      builder = new NotificationCompat.Builder (this, "0");

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)  // flare_icon_30
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))
                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400,
                                      500, 400, 300, 200, 400});
                  } else {

                      builder = new NotificationCompat.Builder (this);

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))

                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400, 500,
                                      400, 300, 200, 400})
                              .setPriority (Notification.PRIORITY_HIGH);
                  } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  Notification notification = builder.build ();
                  notifManager.notify((int)date.getTime(), notification);
              }
              else if(type.equals("2"))
              {
                  Intent intent;
                  PendingIntent pendingIntent;
                  NotificationCompat.Builder builder;
                  if (notifManager == null) {
                      notifManager = (NotificationManager) getSystemService
                              (Context.NOTIFICATION_SERVICE);
                  }

                  intent = new Intent (this, ReadTextPage.class);
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                      int importance = NotificationManager.IMPORTANCE_HIGH;
                      if (mChannel == null) {
                          NotificationChannel mChannel = new NotificationChannel
                                  ("0", title, importance);
                          mChannel.setDescription (body);
                          mChannel.enableVibration (true);
                          mChannel.setVibrationPattern (new long[]
                                  {100, 200, 300, 400, 500, 400, 300, 200, 400});
                          notifManager.createNotificationChannel (mChannel);
                      }
                      builder = new NotificationCompat.Builder (this, "0");

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)  // flare_icon_30
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))
                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400,
                                      500, 400, 300, 200, 400});
                  } else {

                      builder = new NotificationCompat.Builder (this);

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))

                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400, 500,
                                      400, 300, 200, 400})
                              .setPriority (Notification.PRIORITY_HIGH);
                  } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  Notification notification = builder.build ();
                  notifManager.notify((int)date.getTime(), notification);
              }
                    else if(type.equals("3"))
              {
                  Intent intent;
                  PendingIntent pendingIntent;
                  NotificationCompat.Builder builder;
                  if (notifManager == null) {
                      notifManager = (NotificationManager) getSystemService
                              (Context.NOTIFICATION_SERVICE);
                  }

                  intent = new Intent (this, NormalOffers.class);
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                      int importance = NotificationManager.IMPORTANCE_HIGH;
                      if (mChannel == null) {
                          NotificationChannel mChannel = new NotificationChannel
                                  ("0", title, importance);
                          mChannel.setDescription (body);
                          mChannel.enableVibration (true);
                          mChannel.setVibrationPattern (new long[]
                                  {100, 200, 300, 400, 500, 400, 300, 200, 400});
                          notifManager.createNotificationChannel (mChannel);
                      }
                      builder = new NotificationCompat.Builder (this, "0");

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)  // flare_icon_30
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))
                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400,
                                      500, 400, 300, 200, 400});
                  } else {

                      builder = new NotificationCompat.Builder (this);

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))

                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400, 500,
                                      400, 300, 200, 400})
                              .setPriority (Notification.PRIORITY_HIGH);
                  } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  Notification notification = builder.build ();
                  notifManager.notify((int)date.getTime(), notification);
              }

              else if(type.equals("4"))
              {
                  Intent intent;
                  PendingIntent pendingIntent;
                  NotificationCompat.Builder builder;
                  if (notifManager == null) {
                      notifManager = (NotificationManager) getSystemService
                              (Context.NOTIFICATION_SERVICE);
                  }

                  intent = new Intent (this, Teacher_page.class);
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                      int importance = NotificationManager.IMPORTANCE_HIGH;
                      if (mChannel == null) {
                          NotificationChannel mChannel = new NotificationChannel
                                  ("0", title, importance);
                          mChannel.setDescription (body);
                          mChannel.enableVibration (true);
                          mChannel.setVibrationPattern (new long[]
                                  {100, 200, 300, 400, 500, 400, 300, 200, 400});
                          notifManager.createNotificationChannel (mChannel);
                      }
                      builder = new NotificationCompat.Builder (this, "0");

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)  // flare_icon_30
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))
                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400,
                                      500, 400, 300, 200, 400});
                  } else {

                      builder = new NotificationCompat.Builder (this);

                      intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                              Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
                      builder.setContentTitle (title)
                              .setSmallIcon(R.drawable.logo_app).setTicker(title).setWhen(0)
                              .setContentText (body)  // required
                              .setDefaults (Notification.DEFAULT_ALL)
                              .setAutoCancel (true)
                              .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_app))

                              .setContentIntent (pendingIntent)
                              .setSound (RingtoneManager.getDefaultUri
                                      (RingtoneManager.TYPE_NOTIFICATION))
                              .setVibrate (new long[]{100, 200, 300, 400, 500,
                                      400, 300, 200, 400})
                              .setPriority (Notification.PRIORITY_HIGH);
                  } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  Notification notification = builder.build ();
                  notifManager.notify((int)date.getTime(), notification);
              }


              }

          }

      }


    public static String fixEncodingUnicode(String response) {
        String str = "";
        try {
            // displayed as    desired encoding

            str = new String(response.getBytes("windows-1254"), "UTF-8");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        String decodedStr = Html.fromHtml(str).toString();
        return decodedStr;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }



}