package com.example.ivan.animationareatest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity(), View.OnClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        test_button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
       // val myAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.scaling)
       // val interpolator = MyBounceInterpolator(0.2, 20.0)
       // myAnim.interpolator = interpolator
        container.removeView(test_button)
        test_button.visibility = View.VISIBLE
        //test_button.startAnimation(myAnim)
    }
}
