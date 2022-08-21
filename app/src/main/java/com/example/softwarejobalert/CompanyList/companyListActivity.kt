package com.example.softwarejobalert.CompanyList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.softwarejobalert.NotificationList.NotificationsListFragment
import com.example.softwarejobalert.R
import com.example.softwarejobalert.databinding.ActivityCompanyListBinding

class companyListActivity : AppCompatActivity() {

    lateinit var binding:ActivityCompanyListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view,companyListFragment())
        }

        binding.companyListButton.setOnClickListener(){

            supportFragmentManager.commit {
                replace(R.id.fragment_container_view,companyListFragment())
            }
        }

        binding.notificationListButton.setOnClickListener(){

            supportFragmentManager.commit {
                replace(R.id.fragment_container_view,NotificationsListFragment())
            }

        }

    }
}