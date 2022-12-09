package com.vishal.softwarejobalert.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.vishal.softwarejobalert.BaseFragment
import com.vishal.softwarejobalert.R
import com.vishal.softwarejobalert.SearchByLocation.searchByLocationAndSkill
import com.vishal.softwarejobalert.databinding.ActivityHomeScreenBinding
import com.vishal.softwarejobalert.singleClickListener
import com.vishal.softwarejobalert.superNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen : BaseFragment<ActivityHomeScreenBinding,HomeScreenViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        binding.locationConstraintLayout.singleClickListener {
//            val intent = Intent(requireContext(),searchByLocationAndSkill::class.java)
//            startActivity(intent)

            val action =HomeScreenDirections.actionHomeScreenToSearchByLocationAndSkill()
            superNavigate(action)
        }

    }
}