package ru.anbn.gpstracker

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class TestKotlin : AppCompatActivity() {
    var SENT_SMS = "SENT_SMS"
    var DELIVER_SMS = "DELIVER_SMS"
    var sent_intent = Intent(SENT_SMS)
    var deliver_intent = Intent(DELIVER_SMS)
    var sent_pi: PendingIntent? = null
    var deliver_pi: PendingIntent? = null
    var textSerializable: EditText? = null
    var motionSensorOnButton: Button? = null
    var motionSensorOffButton: Button? = null
    var b1AlarmClock2H: Button? = null
    var b1AlarmClock12H: Button? = null
    var trackerInformationButton: Button? = null
    var serializableButton: Button? = null
    var deserializableButton: Button? = null
    var trackerCoordinatesBriefButton: Button? = null
    var trackerCoordinatesInDetailButton: Button? = null
    var REQUEST_CODE_PERMISSION_SEND_SMS = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ban the night theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // request permission on SEND_SMS
        ActivityCompat.requestPermissions(this@TestKotlin, arrayOf(Manifest.permission.SEND_SMS), REQUEST_CODE_PERMISSION_SEND_SMS)

        // checking permission
        if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            displayToast("Permission denied...")
        }
        sent_pi = PendingIntent.getBroadcast(this@TestKotlin, 0,
                sent_intent, PendingIntent.FLAG_IMMUTABLE)
        deliver_pi = PendingIntent.getBroadcast(this@TestKotlin, 0,
                deliver_intent, PendingIntent.FLAG_IMMUTABLE)

        // add animation on the button
        val animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha)

        // датчик движения - включить
        motionSensorOnButton = findViewById<View>(R.id.motionSensorOnButton) as Button
        motionSensorOnButton!!.setOnClickListener { view ->
            view.startAnimation(animAlpha)
            if (ActivityCompat.checkSelfPermission(this@TestKotlin,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(StaticVariables.PHONE_NUMBER_SIGNALLING, null,
                        StaticVariables.ON_MOTION_SENSOR, sent_pi, deliver_pi)
                // onSecurityButton.setEnabled(false);
            } else {
                displayToast("Permission denied...")
            }
        }

        // датчик движения - отключить
        motionSensorOffButton = findViewById<View>(R.id.motionSensorOffButton) as Button
        motionSensorOffButton!!.setOnClickListener { view ->
            view.startAnimation(animAlpha)
            if (ActivityCompat.checkSelfPermission(this@TestKotlin,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(StaticVariables.PHONE_NUMBER_SIGNALLING, null,
                        StaticVariables.OFF_MOTION_SENSOR, sent_pi, deliver_pi)
                // offSecurityButton.setEnabled(false);
            } else {
                displayToast("Permission denied...")
            }
        }

        // будильник В1 - 2 часа
        b1AlarmClock2H = findViewById<View>(R.id.b1AlarmClock2H) as Button
        b1AlarmClock2H!!.setOnClickListener { view ->
            view.startAnimation(animAlpha)
            if (ActivityCompat.checkSelfPermission(this@TestKotlin,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(StaticVariables.PHONE_NUMBER_SIGNALLING, null,
                        StaticVariables.ALARM_CLOCK_B1_2H, sent_pi, deliver_pi)
                // offSecurityButton.setEnabled(false);
            } else {
                displayToast("Permission denied...")
            }
        }

        // будильник В1 - 12 часов
        b1AlarmClock12H = findViewById<View>(R.id.b1AlarmClock12H) as Button
        b1AlarmClock12H!!.setOnClickListener { view ->
            view.startAnimation(animAlpha)
            if (ActivityCompat.checkSelfPermission(this@TestKotlin,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(StaticVariables.PHONE_NUMBER_SIGNALLING, null,
                        StaticVariables.ALARM_CLOCK_B1_12H, sent_pi, deliver_pi)
                // offSecurityButton.setEnabled(false);
            } else {
                displayToast("Permission denied...")
            }
        }

        // получить информацию о трекере ()
        trackerInformationButton = findViewById<View>(R.id.trackerInformationButton) as Button
        trackerInformationButton!!.setOnClickListener { view ->
            view.startAnimation(animAlpha)
            if (ActivityCompat.checkSelfPermission(this@TestKotlin,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(StaticVariables.PHONE_NUMBER_SIGNALLING, null,
                        StaticVariables.TRACKER_INFORMATION_BUTTON, sent_pi, deliver_pi)
                // offSecurityButton.setEnabled(false);
            } else {
                displayToast("Permission denied...")
            }
        }

        // кнопка: получить координаты кратко
        trackerCoordinatesBriefButton = findViewById<View>(R.id.trackerCoordinatesBriefButton) as Button
        trackerCoordinatesBriefButton!!.setOnClickListener { view ->
            view.startAnimation(animAlpha)
            if (ActivityCompat.checkSelfPermission(this@TestKotlin,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(StaticVariables.PHONE_NUMBER_SIGNALLING, null,
                        StaticVariables.TRACKER_COORDINATES_BRIEF_BUTTON, sent_pi, deliver_pi)
                // offSecurityButton.setEnabled(false);
            } else {
                displayToast("Permission denied...")
            }
        }

        // кнопка: получить координаты подробно
        trackerCoordinatesInDetailButton = findViewById<View>(R.id.trackerCoordinatesInDetailButton) as Button
        trackerCoordinatesInDetailButton!!.setOnClickListener { view ->
            view.startAnimation(animAlpha)
            if (ActivityCompat.checkSelfPermission(this@TestKotlin,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(StaticVariables.PHONE_NUMBER_SIGNALLING, null,
                        StaticVariables.TRACKER_COORDINATES_IN_DETAIL_BUTTON, sent_pi, deliver_pi)
                // offSecurityButton.setEnabled(false);
            } else {
                displayToast("Permission denied...")
            }
        }
        textSerializable = findViewById<View>(R.id.textSerializable) as EditText
        serializableButton = findViewById<View>(R.id.serializableButton) as Button
        serializableButton!!.setOnClickListener { view ->
            view.startAnimation(animAlpha)
            // let's check that file data.json exists
            val user = User()
            user.trackerModel = textSerializable!!.text.toString()
            //textSerializable.setText(user.getTrackerModel());

            // Сериализация в файл с помощью класса ObjectOutputStream
            var objectOutputStream: ObjectOutputStream? = null
            val filePath = File(getExternalFilesDir(null), StaticVariables.FILE_NAME)
            try {
                objectOutputStream = ObjectOutputStream(
                        FileOutputStream(filePath))
                objectOutputStream.writeObject(user)
                //                objectOutputStream.writeObject(renat);
                objectOutputStream.close()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
        deserializableButton = findViewById<View>(R.id.deserializableButton) as Button
        deserializableButton!!.setOnClickListener { view ->
            view.startAnimation(animAlpha)
            // let's check that file data.json exists

            // Востановление из файла с помощью класса ObjectInputStream
            var objectInputStream: ObjectInputStream? = null
            val filePath = File(getExternalFilesDir(null), StaticVariables.FILE_NAME)
            try {
                objectInputStream = ObjectInputStream(
                        FileInputStream(filePath))
                val userRestored = objectInputStream.readObject() as User
                objectInputStream.close()
                textSerializable!!.setText(userRestored.trackerModel)
            } catch (e: IOException) {
                throw RuntimeException(e)
            } catch (e: ClassNotFoundException) {
                throw RuntimeException(e)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(sentReceiver, IntentFilter(SENT_SMS))
        registerReceiver(deliverReceiver, IntentFilter(DELIVER_SMS))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(sentReceiver)
        unregisterReceiver(deliverReceiver)
    }

    var sentReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (resultCode) {
                RESULT_OK ->                     // Toast.makeText(context, "Sent", Toast.LENGTH_LONG).show();
                    displayToast("Sent")

                else ->                     // Toast.makeText(context, "Error send", Toast.LENGTH_LONG).show();
                    displayToast("Error send")
            }
        }
    }
    var deliverReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (resultCode) {
                RESULT_OK ->                     //Toast.makeText(context, "Delivered", Toast.LENGTH_LONG).show();
                    displayToast("Delivered")

                else ->                     //Toast.makeText(context, "Delivery error", Toast.LENGTH_LONG).show();
                    displayToast("Delivery error")
            }
        }
    }

    fun displayToast(text: String?) {
        Toast.makeText(this@TestKotlin, text, Toast.LENGTH_LONG).show()
    }

    // drawing the menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // обработчик нажатия позиций меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

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
            if (isOnline) {
                val intent = Intent(Intent.ACTION_VIEW)
                //intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.anbn.pinout"));
                intent.data = Uri.parse("https://apps.rustore.ru/app/com.anbn.ipcalculatorforandroid?utm_source=rustore_inner")
                startActivity(intent)
            } else {
                displayToast("Нет подключения к Интернету...")
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
        return true
    }

    protected val isOnline: Boolean
        // проверка наличия подключения к интернету
        protected get() {
            val cs = CONNECTIVITY_SERVICE
            val cm = getSystemService(cs) as ConnectivityManager
            return if (cm.activeNetworkInfo == null) {
                false
            } else {
                true
            }
        }
}