package com.example.ivan.secondareatest.other.mp4parser

import android.app.Activity
import com.example.ivan.secondareatest.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class FileFromAsset(private val activity:Activity) {

    var readS:Int = 0
    private val SOURCE_FILE_NAME = "test2.mp4"

    fun getFileFromRawResources(): File?{
        val iStream = activity.resources.openRawResource(R.raw.test2)
        val file= File("${activity.filesDir}",SOURCE_FILE_NAME)
        val outputStream = FileOutputStream(file)

        try{

            val buffer = ByteArray(4*1024)

            while(read(iStream,buffer)){
                outputStream.write(buffer,0,readS)
            }

            outputStream.flush()
        } finally {
            outputStream.close()
            iStream.close()
        }

        return file
    }


    private fun read(iStream: InputStream, array:ByteArray):Boolean{
        readS = iStream.read(array)
        return readS != -1
    }
}