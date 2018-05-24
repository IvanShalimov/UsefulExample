package com.example.ivan.secondareatest.example.zoom

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.ivan.secondareatest.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_zoom_map.*
import android.animation.Animator
import android.graphics.Point
import android.graphics.Rect
import android.widget.FrameLayout
import android.animation.AnimatorListenerAdapter
import android.view.animation.DecelerateInterpolator
import android.animation.ObjectAnimator
import android.animation.AnimatorSet




class ZoomMapActivity : AppCompatActivity(), OnMapReadyCallback {

    var mMap:GoogleMap? = null
    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private var mCurrentAnimator: Animator? = null

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private val mShortAnimationDuration: Int = 5000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom_map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        mapView2.onCreate(savedInstanceState)
        mapView2.alpha = 0F
        mapView2.getMapAsync(this)
    }

    fun onClick(view: View?){
        Log.d("Test","onClick")
        zoom()
    }

    private fun zoom() {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        mCurrentAnimator?.cancel()

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        val startBounds = Rect()
        val finalBounds = Rect()
        val globalOffset = Point()

        mapView.getGlobalVisibleRect(startBounds)

        findViewById<FrameLayout>(R.id.mapView2).getGlobalVisibleRect(finalBounds,globalOffset)
        startBounds.offset(-globalOffset.x,-globalOffset.y)//-
        finalBounds.offset(-globalOffset.x,-globalOffset.y)//-

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        var startScale:Float
        if(finalBounds.width().toFloat()/finalBounds.height()
                >
           startBounds.width().toFloat()/startBounds.height()){

            startScale = (startBounds.height()/finalBounds.height()).toFloat()
            var startWidth:Float = startScale*finalBounds.width()
            var deltaWitdh:Float = (startWidth - startBounds.width())/2
            startBounds.left-=deltaWitdh.toInt()
            startBounds.right+=deltaWitdh.toInt()

        } else {
            startScale = (startBounds.width()/finalBounds.width()).toFloat()
            var startHeight:Float = startScale*finalBounds.height()
            var deltaHeight:Float = (startHeight - startBounds.height())/2
            startBounds.top-=deltaHeight.toInt()
            startBounds.bottom+=deltaHeight.toInt()
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        mapView.alpha = 0F
        mapView2.visibility = View.VISIBLE
        mapView2.alpha = 1f

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        mapView2.pivotX = 0f
        mapView2.pivotY = 0f

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        val set = AnimatorSet()
        set
                .play(ObjectAnimator.ofFloat<View>(mapView2, View.X,
                        startBounds.left.toFloat(), finalBounds.left.toFloat()))
                .with(ObjectAnimator.ofFloat<View>(mapView2, View.Y,
                        startBounds.top.toFloat(), finalBounds.top.toFloat()))
                .with(ObjectAnimator.ofFloat(mapView2, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(mapView2,
                        View.SCALE_Y, startScale, 1f))
        set.duration = mShortAnimationDuration.toLong()
        set.interpolator = DecelerateInterpolator()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mCurrentAnimator = null
            }

            override fun onAnimationCancel(animation: Animator) {
                mCurrentAnimator = null
            }
        })
        set.start()
        mCurrentAnimator = set

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        val startScaleFinal = startScale
        mapView2.setOnClickListener(View.OnClickListener {
            mCurrentAnimator?.cancel()

            // Animate the four positioning/sizing properties in parallel,
            // back to their original values.
            val set = AnimatorSet()
            set.play(ObjectAnimator
                    .ofFloat<View>(mapView2, View.X, startBounds.left.toFloat()))
                    .with(ObjectAnimator
                            .ofFloat<View>(mapView2,
                                    View.Y, startBounds.top.toFloat()))
                    .with(ObjectAnimator
                            .ofFloat(mapView2,
                                    View.SCALE_X, startScaleFinal))
                    .with(ObjectAnimator
                            .ofFloat(mapView2,
                                    View.SCALE_Y, startScaleFinal))
            set.duration = mShortAnimationDuration.toLong()
            set.interpolator = DecelerateInterpolator()
            set.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    mapView.alpha = 1f
                    mapView2.visibility = View.GONE
                    mCurrentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    mapView.alpha = 1f
                    mapView2.visibility = View.GONE
                    mapView2.alpha = 0f
                    mCurrentAnimator = null
                }
            })
            set.start()
            mCurrentAnimator = set
        })



        zoom_button.isEnabled = false
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
        mapView2.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        mapView2.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
        mapView2.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        mapView2.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        mapView2.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
        mapView2.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        mapView.onSaveInstanceState(outState)
        mapView2.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }
}
