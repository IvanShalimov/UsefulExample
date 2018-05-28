package com.example.ivan.animationareatest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),View.OnClickListener,Animation.AnimationListener {

    var type:Boolean = true
    var animation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image_button.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
                Glide
                .with(applicationContext)
                        .asGif()
                .load("https://loading.io/spinners/bluecat/lg.blue-longcat-spinner.gif")

                        .into(git_image)
    }

    override fun onClick(v: View?) {
        animation?.cancel()

        animation = AnimationUtils.loadAnimation(this,R.anim.alpha)
        animation?.setAnimationListener(this)
        image_button.startAnimation(animation)
    }

    override fun onAnimationEnd(animation: Animation?) {
        if(type){
            image_button.setImageDrawable(resources.getDrawable(R.drawable.ic_pause_black_24dp))
        } else {
            image_button.setImageDrawable(resources.getDrawable(R.drawable.ic_play_arrow_black_24dp))
        }

        type = !type
    }

    override fun onAnimationStart(animation: Animation?) {

    }

    override fun onAnimationRepeat(animation: Animation?) {

    }
}
