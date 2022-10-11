package com.afrinaldi.cinemon.splashscreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.afrinaldi.cinemon.home.MainActivity
import com.afrinaldi.cinemon.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private var _binding : ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = ObjectAnimator.ofFloat(binding.tvAppName, View.ALPHA, 1f).setDuration(800)
        val desc = ObjectAnimator.ofFloat(binding.tvAppDesc, View.ALPHA, 1f).setDuration(1500)
        AnimatorSet().apply {
            playSequentially(title, desc)
            start()
        }

        Handler(Looper.getMainLooper()).postDelayed(
            {
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            },
            2000
        )
    }
}