package com.example.ivan.secondareatest.other.mp4parser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.ivan.secondareatest.R
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import kotlinx.android.synthetic.main.activity_trim_mp4.*
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import java.io.*


class TrimMp4Activity : AppCompatActivity(),View.OnClickListener {

    companion object {
        const val FILE_NAME = "result.mp4"
        const val TAG = "Test"
    }
    var startSec = 0
    var endSec = 0
    private var runThread = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trim_mp4)

        textView.text = "От"
        textView2.text = "До"
        trim_button.setOnClickListener(this)
        check_button.setOnClickListener(this)
        Log.d("Test","Test")
    }

    override fun onClick(v: View?){
        when(v?.id){
            R.id.trim_button ->{
                Log.d(TAG,"onClick")
                if(!runThread){
                    startSec = Integer.valueOf(startTime.text.toString())
                    endSec = Integer.valueOf(endText.text.toString())
                    loadBinary()
                    TrimThread().start()
                } else {
                    Toast.makeText(applicationContext,
                            "Thread work now, wait",
                            Toast.LENGTH_SHORT)
                            .show()
                }

            }
            R.id.check_button ->{
                val file = File(filesDir, FILE_NAME)
                if(file.exists()) Log.d(TAG,"file is exists")
            }
        }
    }

    private fun loadBinary(){
        Log.d(TAG,"loadBinary")
        val ffmpag:FFmpeg = FFmpeg.getInstance(applicationContext)
        try {
            ffmpag.loadBinary(object : LoadBinaryResponseHandler() {

                override fun onStart() {
                    Log.d(TAG,"loadBinary onStart")
                }

                override fun onFailure() {
                    Log.d(TAG,"loadBinary onFailure")
                }

                override fun onSuccess() {
                    Log.d(TAG,"loadBinary onSuccess")
                    val source =  getFileFromRawResources()
                    Log.d(TAG,"name = $filesDir/result.mp4")//ffmpeg
 /*                   val string = "-y -i ${source?.absolutePath} -vcodec copy -acodec copy" +//
                            " -ss 00:00:02 -t 00:00:04 ${getExternalFilesDir(
                                    Environment.DIRECTORY_MUSIC)}/result.mp4"*/

                    val stringSamba = "-y -i smb://192.168.0.1/sdcard/video_2016_09_15_20-04-03.mp4 -vcodec copy -acodec copy" +
                            " -ss 00:00:02 -t 00:00:04 $filesDir/result.mp4"
                    val cmd = stringSamba.split(" ").toTypedArray()
                    ffmpag.execute(cmd, object : ExecuteBinaryResponseHandler() {

                        override fun onStart() {
                            Log.d(TAG,"onStart")
                        }

                        override fun onProgress(message: String?) {
                            Log.d(TAG, "onProgress $message")
                        }

                        override fun onFailure(message: String?) {
                            Log.d(TAG,"failure : ${message}")
                        }

                        override fun onSuccess(message: String?) {
                            Log.d(TAG, "onSuccess $message")
                            val successFile = File(filesDir,"result.mp4")
                            if(successFile.exists()) {
                                Log.d("Test","result file  exists - totalSpace = "+successFile.totalSpace)

                            }
                            else Log.d("Test","result file not exists")
                        }

                        override fun onFinish() {
                            Log.d(TAG, "onFinish")
                        }
                    })
                }

                override fun onFinish() {
                    Log.d(TAG,"loadBinary onFinish")
                }
            })
        } catch(e: FFmpegNotSupportedException){
            Toast.makeText(applicationContext,"Не поддерживается архитектура",Toast.LENGTH_SHORT).show()
        }
    }

    var readS:Int = 0
    private fun getFileFromRawResources():File?{
            readS = 0
            Log.d(TAG,"$filesDir")
            val iStream = resources.openRawResource(R.raw.test2)
            val file= File("$filesDir","test2.mp4")
            val outputStream = FileOutputStream(file)
            try{

                val buffer = ByteArray(4*1024)


                while(read(iStream,buffer)){
                    outputStream.write(buffer,0,readS)
                }

                outputStream.flush()
            } catch(e:Exception){
                Log.d(TAG,e.message)
            }finally {
                outputStream.close()
                iStream.close()

            }
        Log.d(TAG,"end read fle")

        return file
    }

    private fun read(iStream:InputStream,array:ByteArray):Boolean{
        readS = iStream.read(array)
        return readS != -1
    }

    inner class TrimThread:Thread(){

        override fun run() {
            super.run()
            runThread = true
            Thread.sleep(10000)
            runThread = false
        }
    }
}
