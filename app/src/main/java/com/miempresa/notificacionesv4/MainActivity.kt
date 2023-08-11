package com.miempresa.notificacionesv4

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var ID = 1
    val ONESIGNAL_APP_ID = "0ef9ee96-5584-411a-8405-ca9b4e1e6a7c"
    companion object{
        const val INTENT_REQUEST = 0
        const val BUTTON_INTENT_REQUEST = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE,OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        btnVerNotificacion.setOnClickListener({
            notificacionOreo(this.btnVerNotificacion)
        })
    }

    fun crearCanalNotificacion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name: CharSequence = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(getString(R.string.channel_id), name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun notificacionOreo(v: View?){
        crearCanalNotificacion()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0,intent,0)
        val mBuilder =
            NotificationCompat.Builder(this,getString(R.string.channel_id))
                .setSmallIcon(R.drawable.imgnotificacion)
                .setContentTitle("Notificacion Oreo")
                .setContentText("Mi primera notificacion en Oreo")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setOngoing(true)
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(ID++,mBuilder.build())
    }
    fun notificacionOreoExpanded(v: View?){
        crearCanalNotificacion()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0,intent,0)
        val imagen = BitmapFactory.decodeResource(getResources(), R.drawable.imgnotificacion);
        val mBuilder =
            NotificationCompat.Builder(this,getString(R.string.channel_id))
                .setSmallIcon(R.drawable.imgnotificacion)
                .setContentTitle("Notificacion Oreo")
                .setContentText("Mi primera notificacion expandida en Oreo")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setLargeIcon(imagen)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("Soy un texto gigantesco: \n" +
                            "°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°||||||||||||||||||||||||||" +
                            "°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°||||||||||||||||||||||||||"))
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(ID++,mBuilder.build())
    }/*
    fun notificacionOreoPersonalizada(v: View?){
        crearCanalNotificacion()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0,intent,0)
        val l_imagen = BitmapFactory.decodeResource(getResources(), R.drawable.large_imagen);


        val mBuilder =
            NotificationCompat.Builder(this,getString(R.string.channel_id))
                .setSmallIcon(R.drawable.imgnotificacion)
                .setContentTitle("¡NUEVO MENSAJE!")
                .setContentText("Recibio un mensaje nuevo de su amigo")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setLargeIcon(l_imagen)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("\nAmigo:\n" +
                           "\nNo tengo nada más que decir, el día de hoy todo comenzará."))
                .addAction(R.drawable.large_imagen, "Boton",
                    pendingIntent)
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(ID++,mBuilder.build())
    }*/
    fun notificacionOreoPersonalizada(v: View?){

        var amigo = amigo.text.toString()
        var mensaje = mensaje.text.toString()

        crearCanalNotificacion()
        val intent = Intent(this, Recibido::class.java)
        val pendingIntent:PendingIntent? = TaskStackBuilder.create(this).run{
            addNextIntentWithParentStack(intent)
            getPendingIntent(INTENT_REQUEST,PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val l_imagen = BitmapFactory.decodeResource(getResources(), R.drawable.large_imagen);
        val a_imagen = BitmapFactory.decodeResource(getResources(), R.drawable.imagen_des);

        val buttonIntent = Intent(this,Recibido::class.java)
        buttonIntent.putExtra("EXTRA_ARG",amigo)
        buttonIntent.putExtra("EXTRA_ARG1",mensaje)
        val buttonPendingIntent = PendingIntent.getActivity(
            this,
            BUTTON_INTENT_REQUEST,
            buttonIntent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val action = NotificationCompat.Action.Builder(
            R.drawable.ic_launcher_background,
            "Ver Mensaje",
            buttonPendingIntent
        ).build()

        val mBuilder =
            NotificationCompat.Builder(this,getString(R.string.channel_id))
                .setSmallIcon(R.drawable.imgnotificacion)
                .setContentTitle("¡NUEVO MENSAJE!")
                .setContentText("Recibio un mensaje nuevo de su amigo")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setLargeIcon(l_imagen)

                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("\n"+amigo+":\n" +
                            "\n"+mensaje+"."))
                .addAction(action)
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(ID++,mBuilder.build())
    }
}








