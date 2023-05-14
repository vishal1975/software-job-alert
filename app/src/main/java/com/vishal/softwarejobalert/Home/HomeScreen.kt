package com.vishal.softwarejobalert.Home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.vishal.softwarejobalert.BaseFragment
import com.vishal.softwarejobalert.R
import com.vishal.softwarejobalert.Registeration.RegisterationBottomSheetFragment
import com.vishal.softwarejobalert.SearchByLocation.searchByLocationAndSkill
import com.vishal.softwarejobalert.databinding.ActivityHomeScreenBinding
import com.vishal.softwarejobalert.singleClickListener
import com.vishal.softwarejobalert.superNavigate
import com.vishal.softwarejobalert.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen : BaseFragment<ActivityHomeScreenBinding,HomeScreenViewModel>() {
    lateinit var readRegisteration:SharedPreferences
    lateinit var editSubscription: SharedPreferences.Editor
    lateinit var readSubscription: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//       var actionBarDrawerToggle =  ActionBarDrawerToggle(requireActivity(), binding.drawerLayout, R.string.nav_open, R.string.nav_close)
//        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();

        binding.mainContentLayout.hamburger.setOnClickListener {

           if(binding.drawerLayout.isDrawerOpen(Gravity.LEFT)){
               binding.drawerLayout.closeDrawers()
           }
            else {
               binding.drawerLayout.openDrawer(Gravity.LEFT)
           }
        }
        setupViews()
        setupListner()

    }
    private fun setupViews(){
        readRegisteration = requireContext().getSharedPreferences(Constants.REGISTERATION, Context.MODE_PRIVATE)
        editSubscription =  requireContext().getSharedPreferences(Constants.SUBSCRIPTION,Context.MODE_PRIVATE).edit()
        readSubscription = requireContext().getSharedPreferences(Constants.SUBSCRIPTION, Context.MODE_PRIVATE)


        if(!readRegisteration.getBoolean("registeration",false)){

            binding.mainContentLayout.registerTextview.text = "Register"
        }
        else{
            binding.mainContentLayout.registerTextview.text = "Registered"
        }

        if(readSubscription.getBoolean(Constants.MACHINE_LEARNING,false)){
           binding.mainContentLayout.mchineLearningText.text="Subscribed"
        }
        else{
            binding.mainContentLayout.mchineLearningText.text="Subscribe"
        }
        if(readSubscription.getBoolean(Constants.DATA_SCIENCE,false)){

            binding.mainContentLayout.dataScienceText.text="Subscribed"
        }
        else{
            binding.mainContentLayout.dataScienceText.text="Subscribe"
        }
        if(readSubscription.getBoolean(Constants.CLOUD_COMPUTING,false)){

            binding.mainContentLayout.cloudComputingText.text="Subscribed"
        }
        else{
            binding.mainContentLayout.cloudComputingText.text="Subscribe"
        }
        if(readSubscription.getBoolean(Constants.BLOCKCHAIN,false)){

            binding.mainContentLayout.blockchainText.text = "Subscribed"
        }
        else{
            binding.mainContentLayout.blockchainText.text = "Subscribe"
        }
        if(readSubscription.getBoolean(Constants.ANDROID_DEVELOPMENT,false)){

            binding.mainContentLayout.androidDevelopmentText.text = "Subscribed"
        }
        else{
            binding.mainContentLayout.androidDevelopmentText.text = "Subscribe"
        }
        if(readSubscription.getBoolean(Constants.WEB_DEVELOPMENT,false)){

            binding.mainContentLayout.webDevelopmentText.text = "Subscribed"

        }
        else{
            binding.mainContentLayout.webDevelopmentText.text = "Subscribe"
        }

    }

    private fun setupListner(){
        binding.mainContentLayout.locationConstraintLayout.singleClickListener {


            val action =HomeScreenDirections.actionHomeScreenToSearchByLocationAndSkill()
            superNavigate(action)
        }

        binding.mainContentLayout.registerCardview.singleClickListener {
           if(!readRegisteration.getBoolean("registeration",false)){


               var registerationBottomSheetFragment = RegisterationBottomSheetFragment()
               registerationBottomSheetFragment.show( childFragmentManager,
                   RegisterationBottomSheetFragment.TAG)


           }
            else{
                Toast.makeText(requireContext(),"Already Registered",Toast.LENGTH_LONG).show()
           }


        }
        binding.mainContentLayout.machineLearningCardview.singleClickListener {

            if(readSubscription.getBoolean(Constants.MACHINE_LEARNING,false)){

                subscribeToTopic(Constants.MACHINE_LEARNING,binding.mainContentLayout.mchineLearningText)
        }
            else{
                unsubscribeToTopic(Constants.MACHINE_LEARNING,binding.mainContentLayout.mchineLearningText)
            }

        }
        binding.mainContentLayout.dataScienceCardview.singleClickListener{

            if(readSubscription.getBoolean(Constants.DATA_SCIENCE,false)){

                subscribeToTopic(Constants.DATA_SCIENCE,binding.mainContentLayout.dataScienceText)
            }
            else{
                unsubscribeToTopic(Constants.DATA_SCIENCE,binding.mainContentLayout.dataScienceText)
            }

        }
        binding.mainContentLayout.cloudComputingCardview.singleClickListener{


            if(readSubscription.getBoolean(Constants.CLOUD_COMPUTING,false)){

                subscribeToTopic(Constants.CLOUD_COMPUTING,binding.mainContentLayout.cloudComputingText)
            }
            else{
                unsubscribeToTopic(Constants.CLOUD_COMPUTING,binding.mainContentLayout.cloudComputingText)
            }


        }
        binding.mainContentLayout.blockChainCardview.singleClickListener {



            if(readSubscription.getBoolean(Constants.BLOCKCHAIN,false)){

                subscribeToTopic(Constants.BLOCKCHAIN,binding.mainContentLayout.blockchainText)
            }
            else{
                unsubscribeToTopic(Constants.BLOCKCHAIN,binding.mainContentLayout.blockchainText)
            }



        }
        binding.mainContentLayout.androidDevelopmentCardview.singleClickListener{

            if(readSubscription.getBoolean(Constants.ANDROID_DEVELOPMENT,false)){

                subscribeToTopic(Constants.ANDROID_DEVELOPMENT,binding.mainContentLayout.androidDevelopmentText)
            }
            else{
                unsubscribeToTopic(Constants.ANDROID_DEVELOPMENT,binding.mainContentLayout.androidDevelopmentText)
            }

        }

        binding.mainContentLayout.webDevelopmentCardview.singleClickListener {


            if(readSubscription.getBoolean(Constants.WEB_DEVELOPMENT,false)){

                subscribeToTopic(Constants.WEB_DEVELOPMENT,binding.mainContentLayout.webDevelopmentText)
            }
            else{
                unsubscribeToTopic(Constants.WEB_DEVELOPMENT,binding.mainContentLayout.webDevelopmentText)
            }



        }

    }


    private fun unsubscribeToTopic(topic:String,view: TextView){
        binding.mainContentLayout.progressBar.visibility = View.VISIBLE

        Firebase.messaging.unsubscribeFromTopic(topic.filter { !it.isWhitespace() })
            .addOnCompleteListener { task ->
                var msg = "UnSubscribed"
                if (!task.isSuccessful) {
                    msg = "UnSubscribe failed"
                    binding.mainContentLayout.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                }
                else{

                    binding.mainContentLayout.progressBar.visibility = View.GONE
                    view.text = "Subscribe"
//                    var companyName = companyList.get(position).companyName
//                    edit.putString(companyName,"0")
//                    edit.apply()
//                    adapter.notifyDataSetChanged()
//                    binding.progressBar.visibility = View.GONE
//                    showPopUp("Successfully UnSubscribed")
                }


            }

    }

    fun subscribeToTopic(topic: String,view: TextView){
        binding.mainContentLayout.progressBar.visibility = View.VISIBLE
        Firebase.messaging.subscribeToTopic(topic.filter { !it.isWhitespace() })
            .addOnCompleteListener { task ->
                binding.mainContentLayout.progressBar.visibility = View.GONE
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Subscribe failed"
                    Toast.makeText(requireContext(),"Some Problem Occurred",Toast.LENGTH_LONG).show()
                    binding.mainContentLayout.progressBar.visibility = View.GONE
                }
                else{

                    binding.mainContentLayout.progressBar.visibility = View.GONE
                    view.text="Subscribed"

//                database.get().addOnCompleteListener {
//
//                    it.result.ref.child("CompanyList").child("$position").child("subscribed").setValue(1)
//                }
//                    var companyName = companyList.get(position).companyName
//                    edit.putString(companyName,"1")
//                    edit.apply()
//                    adapter.notifyDataSetChanged()
//
//                    binding.progressBar.visibility = View.GONE
//                    showPopUp("Successflly Subscribed")
                }

                //Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
    }

}