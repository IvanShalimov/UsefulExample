package com.example.ivan.secondareatest.other.mp4parser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.ivan.secondareatest.R

class Mp4ParserActivity : AppCompatActivity() {

    val file = FileFromAsset(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mp4_parser)
    }

}
