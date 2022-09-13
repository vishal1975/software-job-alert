package com.vishal.softwarejobalert.CompanyList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.vishal.softwarejobalert.NotificationList.NotificationsListFragment
import com.vishal.softwarejobalert.R
import com.vishal.softwarejobalert.databinding.ActivityCompanyListBinding

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