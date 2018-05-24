package com.example.ivan.secondareatest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.ivan.secondareatest.example.*
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        button.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button6.setOnClickListener(this)
        button7.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent:Intent
        = when(v?.id){
            R.id.button -> {
                Intent(applicationContext,MarksMapActivity::class.java)
            }
            R.id.button2 -> {
                Intent(applicationContext, SneakMapActivity::class.java)
            }
            R.id.button3 -> {
                Intent(applicationContext, KmShowMapsActivity::class.java)
            }
            R.id.button4 -> {
                Intent(applicationContext, CircleRotateMapActivity::class.java)
            }
            R.id.button6 -> {
                Intent(applicationContext, HeatMapActivity::class.java)
            }
            R.id.button7 -> {
                Intent(applicationContext, MapViewActivity::class.java)
            }
            else -> {
                Intent(applicationContext,MarksMapActivity::class.java)
            }
        }

        startActivity(intent)
    }
}
