package com.example.ivan.secondareatest.other.mp4parser

import java.io.File
import com.googlecode.mp4parser.FileDataSourceImpl



class Mp4Cutter2 {

    fun startTrim(src: File,dst: File, startMs:Int, endMs:Int){
        val file = FileDataSourceImpl(src)
    }
}