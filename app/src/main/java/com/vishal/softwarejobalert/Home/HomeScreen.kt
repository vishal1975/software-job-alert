package com.vishal.softwarejobalert.Home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.vishal.softwarejobalert.BaseFragment
import com.vishal.softwarejobalert.R
import com.vishal.softwarejobalert.Registeration.RegisterationBottomSheetFragment
import com.vishal.softwarejobalert.Registeration.UserRegisterationCallback
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


        setupViews()
        setupListner()

    }
    private fun setupViews(){
        readRegisteration = requireContext().getSharedPreferences(Constants.REGISTERATION, Context.MODE_PRIVATE)
        editSubscription =  requireContext().getSharedPreferences(Constants.SUBSCRIPTION,Context.MODE_PRIVATE).edit()
        readSubscription = requireContext().getSharedPreferences(Constants.SUBSCRIPTION, Context.MODE_PRIVATE)


        if(!readRegisteration.getBoolean(Constants.REGISTERATION,false)){

            binding.registerTextview.text = "Register"
        }
        else{
            binding.registerTextview.text = "Registered"
        }

        if(readSubscription.getBoolean(Constants.MACHINE_LEARNING,false)){
           binding.mchineLearningText.text="Subscribed"
        }
        else{
            binding.mchineLearningText.text="Subscribe"
        }
        if(readSubscription.getBoolean(Constants.DATA_SCIENCE,false)){

            binding.dataScienceText.text="Subscribed"
        }
        else{
            binding.dataScienceText.text="Subscribe"
        }
        if(readSubscription.getBoolean(Constants.CLOUD_COMPUTING,false)){

            binding.cloudComputingText.text="Subscribed"
        }
        else{
            binding.cloudComputingText.text="Subscribe"
        }
        if(readSubscription.getBoolean(Constants.BLOCKCHAIN,false)){

            binding.blockchainText.text = "Subscribed"
        }
        else{
            binding.blockchainText.text = "Subscribe"
        }
        if(readSubscription.getBoolean(Constants.ANDROID_DEVELOPMENT,false)){

            binding.androidDevelopmentText.text = "Subscribed"
        }
        else{
            binding.androidDevelopmentText.text = "Subscribe"
        }
        if(readSubscription.getBoolean(Constants.WEB_DEVELOPMENT,false)){

            binding.webDevelopmentText.text = "Subscribed"

        }
        else{
            binding.webDevelopmentText.text = "Subscribe"
        }

    }

    private fun setupListner(){
        binding.locationConstraintLayout.singleClickListener {


            val action =HomeScreenDirections.actionHomeScreenToSearchByLocationAndSkill()
            superNavigate(action)
        }

        binding.registerCardview.singleClickListener {
           if(!readRegisteration.getBoolean(Constants.REGISTERATION,false)){


               var registerationBottomSheetFragment = RegisterationBottomSheetFragment()
               registerationBottomSheetFragment.setRegisterationCallback(object:UserRegisterationCallback{
                   override fun registered() {
                       binding.registerTextview.text = "Registered"
                   }

               })
               registerationBottomSheetFragment.show( childFragmentManager,
                   RegisterationBottomSheetFragment.TAG)


           }
            else{
                Toast.makeText(requireContext(),"Already Registered",Toast.LENGTH_LONG).show()
           }


        }
        binding.machineLearningCardview.singleClickListener {

            if(!readRegisteration.getBoolean(Constants.REGISTERATION,false)){
                Toast.makeText(requireContext(),"Please Register First",Toast.LENGTH_LONG).show()
            }

           else if(!readSubscription.getBoolean(Constants.MACHINE_LEARNING,false)){

                subscribeToTopic(Constants.MACHINE_LEARNING,binding.mchineLearningText)
        }
            else{
                unsubscribeToTopic(Constants.MACHINE_LEARNING,binding.mchineLearningText)
            }

        }
        binding.dataScienceCardview.singleClickListener{
            if(!readRegisteration.getBoolean(Constants.REGISTERATION,false)){
                Toast.makeText(requireContext(),"Please Register First",Toast.LENGTH_LONG).show()
            }
            else if(!readSubscription.getBoolean(Constants.DATA_SCIENCE,false)){

                subscribeToTopic(Constants.DATA_SCIENCE,binding.dataScienceText)
            }
            else{
                unsubscribeToTopic(Constants.DATA_SCIENCE,binding.dataScienceText)
            }

        }
        binding.cloudComputingCardview.singleClickListener{

            if(!readRegisteration.getBoolean(Constants.REGISTERATION,false)){
                Toast.makeText(requireContext(),"Please Register First",Toast.LENGTH_LONG).show()
            }
           else if(!readSubscription.getBoolean(Constants.CLOUD_COMPUTING,false)){

                subscribeToTopic(Constants.CLOUD_COMPUTING,binding.cloudComputingText)
            }
            else{
                unsubscribeToTopic(Constants.CLOUD_COMPUTING,binding.cloudComputingText)
            }


        }
        binding.blockChainCardview.singleClickListener {


            if(!readRegisteration.getBoolean(Constants.REGISTERATION,false)){
                Toast.makeText(requireContext(),"Please Register First",Toast.LENGTH_LONG).show()
            }
           else if(!readSubscription.getBoolean(Constants.BLOCKCHAIN,false)){

                subscribeToTopic(Constants.BLOCKCHAIN,binding.blockchainText)
            }
            else{
                unsubscribeToTopic(Constants.BLOCKCHAIN,binding.blockchainText)
            }



        }
        binding.androidDevelopmentCardview.singleClickListener{

            if(!readRegisteration.getBoolean(Constants.REGISTERATION,false)){
                Toast.makeText(requireContext(),"Please Register First",Toast.LENGTH_LONG).show()
            }

            else if(!readSubscription.getBoolean(Constants.ANDROID_DEVELOPMENT,false)){

                subscribeToTopic(Constants.ANDROID_DEVELOPMENT,binding.androidDevelopmentText)
            }
            else{
                unsubscribeToTopic(Constants.ANDROID_DEVELOPMENT,binding.androidDevelopmentText)
            }

        }

        binding.webDevelopmentCardview.singleClickListener {

            if(!readRegisteration.getBoolean(Constants.REGISTERATION,false)){
                Toast.makeText(requireContext(),"Please Register First",Toast.LENGTH_LONG).show()
            }
            else if(!readSubscription.getBoolean(Constants.WEB_DEVELOPMENT,false)){

                subscribeToTopic(Constants.WEB_DEVELOPMENT,binding.webDevelopmentText)
            }
            else{
                unsubscribeToTopic(Constants.WEB_DEVELOPMENT,binding.webDevelopmentText)
            }



        }

    }


    private fun unsubscribeToTopic(topic:String,view: TextView){
        binding.progressBar.visibility = View.VISIBLE

        Firebase.messaging.unsubscribeFromTopic(topic.filter { !it.isWhitespace() })
            .addOnCompleteListener { task ->
                var msg = "UnSubscribed"
                if (!task.isSuccessful) {
                    msg = "UnSubscribe failed"
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                }
                else{

                    binding.progressBar.visibility = View.GONE
                    view.text = "Subscribe"
                    editSubscription.putBoolean(topic,false)
                    editSubscription.apply()
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
        binding.progressBar.visibility = View.VISIBLE
        Firebase.messaging.subscribeToTopic(topic.filter { !it.isWhitespace() })
            .addOnCompleteListener { task ->
                binding.progressBar.visibility = View.GONE
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Subscribe failed"
                    Toast.makeText(requireContext(),"Some Problem Occurred",Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                }
                else{
                    Toast.makeText(requireContext(),"Successful",Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                    editSubscription.putBoolean(topic,true)
                    editSubscription.apply()
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