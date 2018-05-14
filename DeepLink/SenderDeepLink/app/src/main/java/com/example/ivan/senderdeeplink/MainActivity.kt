package com.example.ivan.senderdeeplink

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view:View){
        val address:Uri = Uri.parse("example://myhost");
        val intent:Intent = Intent(Intent.ACTION_VIEW,address);
        startActivity(intent);
    }
}
