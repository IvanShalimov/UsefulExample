package com.example.ivan.secondareatest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity(), Adapter.OnItemClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        list_activity.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter()
        adapter.callback = this
        list_activity.adapter = adapter

    }

    override fun onItemClick(Class: Class<out Any>) {
        val intent = Intent(applicationContext,Class)
        startActivity(intent)
    }
}
