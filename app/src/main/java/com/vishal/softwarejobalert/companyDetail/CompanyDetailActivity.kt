package com.vishal.softwarejobalert.companyDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.bumptech.glide.Glide
import com.vishal.softwarejobalert.databinding.ActivityCompanyDetailBinding

class CompanyDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityCompanyDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = intent.getStringExtra("image")
        val companyName = intent.getStringExtra("companyName")
        val info = intent.getStringExtra("info")

        Glide.with(this).load(
           image
        ).into( binding.companyImage)
        binding.companyName.text = companyName
        binding.info.text = info
        binding.info.movementMethod = ScrollingMovementMethod()
    }
}