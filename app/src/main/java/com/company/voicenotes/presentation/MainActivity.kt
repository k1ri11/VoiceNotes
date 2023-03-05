package com.company.voicenotes.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.voicenotes.App
import com.company.voicenotes.R
import com.company.voicenotes.di.AppComponent

class MainActivity : AppCompatActivity() {

    lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = App.get(applicationContext).appComponent

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}