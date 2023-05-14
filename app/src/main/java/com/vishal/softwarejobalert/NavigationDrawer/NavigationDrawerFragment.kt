package com.vishal.softwarejobalert.NavigationDrawer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vishal.softwarejobalert.BaseFragment
import com.vishal.softwarejobalert.R
import com.vishal.softwarejobalert.databinding.FragmentNavigationDrawerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigationDrawerFragment :
    BaseFragment<FragmentNavigationDrawerBinding, NavigationDrawerViewModel>() {


     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inflate the layout for this fragment

    }

}