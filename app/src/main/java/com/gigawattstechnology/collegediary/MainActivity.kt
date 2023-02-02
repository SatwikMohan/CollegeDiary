package com.gigawattstechnology.collegediary

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import com.gigawattstechnology.collegediary.Adapters.SliderAdapter
import com.gigawattstechnology.collegediary.CustomClasses.SliderData
import com.gigawattstechnology.collegediary.databinding.ActivityMainBinding
import com.smarteist.autoimageslider.SliderView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val list=ArrayList<SliderData>()
        val actionBar=supportActionBar
        val colorDrawable= ColorDrawable(Color.parseColor("#E94057"))
        actionBar?.setBackgroundDrawable(colorDrawable)
        list.add(SliderData(R.drawable.discoverslide,"Discover People"))
        list.add(SliderData(R.drawable.sameinterest,"Find People Of\nSimilar Interest"))
        list.add(SliderData(R.drawable.yourprofilestatusslide,"Check Your Status"))
        val sliderAdapter= SliderAdapter(list,this)
        binding.ImageSlider.setSliderAdapter(sliderAdapter)
        binding.ImageSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR)
        binding.ImageSlider.setScrollTimeInSec(3)
        binding.ImageSlider.setAutoCycle(true)
        binding.ImageSlider.startAutoCycle()
        binding.CreateAnAccount.setOnClickListener {
            val intent=Intent(binding.root.context,SignUpActivity::class.java)
            val options= ActivityOptions.makeCustomAnimation(binding.root.context, androidx.appcompat.R.anim.abc_fade_in, com.google.android.material.R.anim.abc_fade_out);
            startActivity(intent,options.toBundle())
        }
        binding.LoginAccount.setOnClickListener {
            val intent=Intent(binding.root.context,SignInActivity::class.java)
            val options= ActivityOptions.makeCustomAnimation(binding.root.context, androidx.appcompat.R.anim.abc_fade_in, com.google.android.material.R.anim.abc_fade_out);
            startActivity(intent,options.toBundle())
        }
    }
}