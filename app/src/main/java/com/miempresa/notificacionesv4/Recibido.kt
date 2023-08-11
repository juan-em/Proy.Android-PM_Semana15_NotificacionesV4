package com.miempresa.notificacionesv4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_recibido.*
import kotlinx.android.synthetic.main.notification_small.*

class Recibido : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recibido)

        val extra = intent.getStringExtra("EXTRA_ARG")
        val extra1 = intent.getStringExtra("EXTRA_ARG1")

        extra?.let{
            txt_msj.text = it
        }
        extra1?.let{
            txt_msj1.text = it
        }

    }
}