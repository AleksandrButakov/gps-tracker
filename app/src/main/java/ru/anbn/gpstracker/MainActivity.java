package ru.anbn.gpstracker;

import static ru.anbn.gpstracker.StaticVariables.ALARM_CLOCK_B1_12H;
import static ru.anbn.gpstracker.StaticVariables.ALARM_CLOCK_B1_2H;
import static ru.anbn.gpstracker.StaticVariables.FILE_NAME;
import static ru.anbn.gpstracker.StaticVariables.OFF_MOTION_SENSOR;
import static ru.anbn.gpstracker.StaticVariables.ON_MOTION_SENSOR;
import static ru.anbn.gpstracker.StaticVariables.PHONE_NUMBER_SIGNALLING;
import static ru.anbn.gpstracker.StaticVariables.TRACKER_COORDINATES_BRIEF_BUTTON;
import static ru.anbn.gpstracker.StaticVariables.TRACKER_COORDINATES_IN_DETAIL_BUTTON;
import static ru.anbn.gpstracker.StaticVariables.TRACKER_INFORMATION_BUTTON;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    String SENT_SMS = "SENT_SMS";
    String DELIVER_SMS = "DELIVER_SMS";

    Intent sent_intent = new Intent(SENT_SMS);
    Intent deliver_intent = new Intent(DELIVER_SMS);

    PendingIntent sent_pi, deliver_pi;

    EditText textSerializable;

    Button motionSensorOnButton;
    Button motionSensorOffButton;
    Button b1AlarmClock2H;
    Button b1AlarmClock12H;
    Button trackerInformationButton;
    Button serializableButton;
    Button deserializableButton;
    Button trackerCoordinatesBriefButton;
    Button trackerCoordinatesInDetailButton;

    int REQUEST_CODE_PERMISSION_SEND_SMS;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ban the night theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // request permission on SEND_SMS
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE_PERMISSION_SEND_SMS);

        // checking permission
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            displayToast("Permission denied...");
        }

        sent_pi = PendingIntent.getBroadcast(MainActivity.this, 0,
                sent_intent, PendingIntent.FLAG_IMMUTABLE);
        deliver_pi = PendingIntent.getBroadcast(MainActivity.this, 0,
                deliver_intent, PendingIntent.FLAG_IMMUTABLE);

        // add animation on the button
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        // датчик движения - включить
        motionSensorOnButton = (Button) findViewById(R.id.motionSensorOnButton);
        motionSensorOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PHONE_NUMBER_SIGNALLING, null,
                            ON_MOTION_SENSOR, sent_pi, deliver_pi);
                    // onSecurityButton.setEnabled(false);
                } else {
                    displayToast("Permission denied...");
                }
            }
        });

        // датчик движения - отключить
        motionSensorOffButton = (Button) findViewById(R.id.motionSensorOffButton);
        motionSensorOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PHONE_NUMBER_SIGNALLING, null,
                            OFF_MOTION_SENSOR, sent_pi, deliver_pi);
                    // offSecurityButton.setEnabled(false);
                } else {
                    displayToast("Permission denied...");
                }
            }
        });

        // будильник В1 - 2 часа
        b1AlarmClock2H = (Button) findViewById(R.id.b1AlarmClock2H);
        b1AlarmClock2H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PHONE_NUMBER_SIGNALLING, null,
                            ALARM_CLOCK_B1_2H, sent_pi, deliver_pi);
                    // offSecurityButton.setEnabled(false);
                } else {
                    displayToast("Permission denied...");
                }
            }
        });

        // будильник В1 - 12 часов
        b1AlarmClock12H = (Button) findViewById(R.id.b1AlarmClock12H);
        b1AlarmClock12H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PHONE_NUMBER_SIGNALLING, null,
                            ALARM_CLOCK_B1_12H, sent_pi, deliver_pi);
                    // offSecurityButton.setEnabled(false);
                } else {
                    displayToast("Permission denied...");
                }
            }
        });

        // получить информацию о трекере ()
        trackerInformationButton = (Button) findViewById(R.id.trackerInformationButton);
        trackerInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PHONE_NUMBER_SIGNALLING, null,
                            TRACKER_INFORMATION_BUTTON, sent_pi, deliver_pi);
                    // offSecurityButton.setEnabled(false);
                } else {
                    displayToast("Permission denied...");
                }
            }
        });

        // кнопка: получить координаты кратко
        trackerCoordinatesBriefButton = (Button) findViewById(R.id.trackerCoordinatesBriefButton);
        trackerCoordinatesBriefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PHONE_NUMBER_SIGNALLING, null,
                            TRACKER_COORDINATES_BRIEF_BUTTON, sent_pi, deliver_pi);
                    // offSecurityButton.setEnabled(false);
                } else {
                    displayToast("Permission denied...");
                }
            }
        });

        // кнопка: получить координаты подробно
        trackerCoordinatesInDetailButton = (Button)
                findViewById(R.id.trackerCoordinatesInDetailButton);
        trackerCoordinatesInDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(PHONE_NUMBER_SIGNALLING, null,
                            TRACKER_COORDINATES_IN_DETAIL_BUTTON, sent_pi, deliver_pi);
                    // offSecurityButton.setEnabled(false);
                } else {
                    displayToast("Permission denied...");
                }
            }
        });

        textSerializable = (EditText) findViewById(R.id.textSerializable);

        serializableButton = (Button) findViewById(R.id.serializableButton);
        serializableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                // let's check that file data.json exists

                User user = new User();
                user.setTrackerModel(String.valueOf(textSerializable.getText()));
                //textSerializable.setText(user.getTrackerModel());

                // Сериализация в файл с помощью класса ObjectOutputStream
                ObjectOutputStream objectOutputStream = null;

                File filePath = new File(getExternalFilesDir(null), FILE_NAME);

                try {
                    objectOutputStream = new ObjectOutputStream(
                            new FileOutputStream(filePath));
                    objectOutputStream.writeObject(user);
//                objectOutputStream.writeObject(renat);
                    objectOutputStream.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        deserializableButton = (Button) findViewById(R.id.deserializableButton);
        deserializableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                // let's check that file data.json exists

                // Востановление из файла с помощью класса ObjectInputStream
                ObjectInputStream objectInputStream = null;

                File filePath = new File(getExternalFilesDir(null), FILE_NAME);

                try {
                    objectInputStream = new ObjectInputStream(
                            new FileInputStream(filePath));
                    User userRestored = (User) objectInputStream.readObject();
                    objectInputStream.close();

                    textSerializable.setText(userRestored.getTrackerModel());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
                    displayToast("Sent");
                    break;
                default:
                    // Toast.makeText(context, "Error send", Toast.LENGTH_LONG).show();
                    displayToast("Error send");
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
                    displayToast("Delivered");
                    break;
                default:
                    //Toast.makeText(context, "Delivery error", Toast.LENGTH_LONG).show();
                    displayToast("Delivery error");
                    break;
            }
        }
    };

    public void displayToast(String text) {
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

        // TODO
        // оценить приложение
        if (id == R.id.action_item3) {
            if (isOnline()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.anbn.pinout"));
                intent.setData(Uri.parse("https://apps.rustore.ru/app/com.anbn.ipcalculatorforandroid?utm_source=rustore_inner"));

                startActivity(intent);
            } else {
                displayToast("Нет подключения к Интернету...");
            }


        }


        /*
                // оценить приложение
        if (id == R.id.action_item3) {
            if (isOnline()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.anbn.pinout"));
                startActivity(intent);
            } else {
                displayToast("Нет подключения к Интернету...");
            }
        }
         */





        // о программе
        if (id == R.id.action_item4) {

        }
        return true;
    }


    // проверка наличия подключения к интернету
    protected boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        } else {
            return true;
        }
    }


}