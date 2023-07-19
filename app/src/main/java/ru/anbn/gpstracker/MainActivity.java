package ru.anbn.gpstracker;

import static ru.anbn.gpstracker.StaticVariables.ALARM_CLOCK_B1_12H;
import static ru.anbn.gpstracker.StaticVariables.ALARM_CLOCK_B1_2H;
import static ru.anbn.gpstracker.StaticVariables.MONITORING_TRACKER;
import static ru.anbn.gpstracker.StaticVariables.OFF_MOTION_SENSOR;
import static ru.anbn.gpstracker.StaticVariables.ON_MOTION_SENSOR;
import static ru.anbn.gpstracker.StaticVariables.PHONE_NUMBER_SIGNALLING;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    String SENT_SMS = "SENT_SMS";
    String DELIVER_SMS = "DELIVER_SMS";

    Intent sent_intent = new Intent(SENT_SMS);
    Intent deliver_intent = new Intent(DELIVER_SMS);

    PendingIntent sent_pi, deliver_pi;

    EditText address;
    EditText text;
    EditText textSerializable;

    Button sendButton;
    Button motionSensorOnButton;
    Button motionSensorOffButton;
    Button b1AlarmClock2H;
    Button b1AlarmClock12H;
    Button monitoringButton;
    Button serializableButton;
    Button deserializableButton;

    int REQUEST_CODE_PERMISSION_SEND_SMS;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ban the night theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // request permission on SEND_SMS
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS},
                REQUEST_CODE_PERMISSION_SEND_SMS);

        // checking permission
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            toastView("Permission denied...");
        }

        sent_pi = PendingIntent.getBroadcast(MainActivity.this, 0, sent_intent, PendingIntent.FLAG_IMMUTABLE);
        deliver_pi = PendingIntent.getBroadcast(MainActivity.this, 0, deliver_intent, PendingIntent.FLAG_IMMUTABLE);

        address = (EditText) findViewById(R.id.address);
        text = (EditText) findViewById(R.id.text);

        // add animation on the button
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(address.getText().toString(), null, text.getText().toString(), sent_pi, deliver_pi);
                } else {
                    toastView("Permission denied...");
                }
            }
        });

        motionSensorOnButton = (Button) findViewById(R.id.motionSensorOnButton);
        motionSensorOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PHONE_NUMBER_SIGNALLING, null, ON_MOTION_SENSOR, sent_pi, deliver_pi);
                    // onSecurityButton.setEnabled(false);
                } else {
                    toastView("Permission denied...");
                }
            }
        });

        motionSensorOffButton = (Button) findViewById(R.id.motionSensorOffButton);
        motionSensorOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PHONE_NUMBER_SIGNALLING, null, OFF_MOTION_SENSOR, sent_pi, deliver_pi);
                    // offSecurityButton.setEnabled(false);
                } else {
                    toastView("Permission denied...");
                }
            }
        });




        b1AlarmClock2H = (Button) findViewById(R.id.b1AlarmClock2H);
        b1AlarmClock2H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PHONE_NUMBER_SIGNALLING, null, ALARM_CLOCK_B1_2H, sent_pi, deliver_pi);
                    // offSecurityButton.setEnabled(false);
                } else {
                    toastView("Permission denied...");
                }
            }
        });

        b1AlarmClock12H = (Button) findViewById(R.id.b1AlarmClock12H);
        b1AlarmClock12H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PHONE_NUMBER_SIGNALLING, null, ALARM_CLOCK_B1_12H, sent_pi, deliver_pi);
                    // offSecurityButton.setEnabled(false);
                } else {
                    toastView("Permission denied...");
                }
            }
        });



        monitoringButton = (Button) findViewById(R.id.monitoringButton);
        monitoringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PHONE_NUMBER_SIGNALLING, null, MONITORING_TRACKER, sent_pi, deliver_pi);
                    // offSecurityButton.setEnabled(false);
                } else {
                    toastView("Permission denied...");
                }
            }
        });



        textSerializable = (EditText) findViewById(R.id.textSerializable);



        serializableButton = (Button) findViewById(R.id.serializableButton);



        deserializableButton = (Button) findViewById(R.id.deserializableButton);


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(sentReceiver, new IntentFilter(SENT_SMS));
        registerReceiver(deliverReceiver, new IntentFilter(DELIVER_SMS));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(sentReceiver);
        unregisterReceiver(deliverReceiver);
    }

    BroadcastReceiver sentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    // Toast.makeText(context, "Sent", Toast.LENGTH_LONG).show();
                    toastView("Sent");
                    break;
                default:
                    // Toast.makeText(context, "Error send", Toast.LENGTH_LONG).show();
                    toastView("Error send");
                    break;
            }
        }
    };

    BroadcastReceiver deliverReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    //Toast.makeText(context, "Delivered", Toast.LENGTH_LONG).show();
                    toastView("Delivered");
                    break;
                default:
                    //Toast.makeText(context, "Delivery error", Toast.LENGTH_LONG).show();
                    toastView("Delivery error");
                    break;
            }
        }
    };

    public void toastView(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }



    // drawing the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // обработчик нажатия позиций меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // строка поиска
        if (id == R.id.action_item1) {
//            Intent intent = new Intent(this, SearchActivity.class);
//            startActivity(intent);
        }

        // политика конфиденциальности
        if (id == R.id.action_item2) {
            // проверим что есть подключение к сети интернет

        }

        // оценить приложение
        if (id == R.id.action_item3) {

        }

        // о программе
        if (id == R.id.action_item4) {

        }
        return true;
    }




}