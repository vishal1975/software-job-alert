package com.vishal.softwarejobalert.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vishal.softwarejobalert.R
import com.vishal.softwarejobalert.SearchByLocation.searchByLocationAndSkill
import com.vishal.softwarejobalert.databinding.ActivityHomeScreenBinding

class HomeScreen : AppCompatActivity() {
    lateinit var binding:ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.locationConstraintLayout.setOnClickListener(){
            val intent = Intent(this,searchByLocationAndSkill::class.java)
            startActivity(intent)
        }
    }
}