package com.example.softwarejobalert.NotificationDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.softwarejobalert.R
import com.example.softwarejobalert.databinding.ActivityNotificationDetailBinding

class NotificationDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityNotificationDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var role = intent.getStringExtra("role")
        var role_info = intent.getStringExtra("role_info")
        var image = intent.getStringExtra("image")
        var apply_link = intent.getStringExtra("apply_link")

        binding.role.text=role
        binding.roleInfo.text = role_info
        Glide.with(this).load(image).into(binding.image)
        binding.applyLink.text = apply_link
    }
}