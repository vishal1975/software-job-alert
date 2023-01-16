package com.vishal.softwarejobalert.Registeration

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.vishal.softwarejobalert.ModelClasses.User
import com.vishal.softwarejobalert.R
import com.vishal.softwarejobalert.databinding.RegisterationLayoutBinding
import com.vishal.softwarejobalert.utils.Constants

class RegisterationBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var binding: RegisterationLayoutBinding
   var database: DatabaseReference =Firebase.database.reference
    lateinit var edit: SharedPreferences.Editor
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = RegisterationLayoutBinding.inflate(inflater)
        edit=requireContext().getSharedPreferences(Constants.REGISTERATION, Context.MODE_PRIVATE).edit()
        setOnClick()
       return binding.root
    }

    private fun setOnClick() {
        binding.registerButton.setOnClickListener(){
        registerUser()
        }
    }

    private fun registerUser(){

        binding.emptyName.visibility = View.GONE
        binding.emptyCountry.visibility = View.GONE
        binding.error.visibility = View.GONE
        binding.Networkerror.visibility = View.GONE
        var name = binding.userNameEditext.text.toString();
        var country = binding.userCountryEditext.text.toString()

        if(name.isEmpty()){
            binding.emptyName.visibility = View.VISIBLE
            return
        }
        if(country.isEmpty()){
            binding.emptyCountry.visibility = View.VISIBLE
            return
        }

        sendDataToFirebase(name,country)
    }

    private fun sendDataToFirebase(userName:String,countryName:String){

        val user = User(userName,countryName)
    var temp=  database.child("users").push().setValue(user)
if(Constants.isNetworkAvailable(requireContext())) {
    temp.addOnSuccessListener {


        Toast.makeText(requireContext(), "registered", Toast.LENGTH_LONG).show()
        edit.putBoolean(Constants.REGISTERATION, true)
        edit.apply()
dismiss()

        //binding.error.visibility = View.VISIBLE

    }

    temp.addOnFailureListener() {
        binding.error.visibility = View.VISIBLE
    }

}
        else{
            binding.Networkerror.visibility = View.VISIBLE
        }

    }


    companion object {
        const val TAG = "REGISTERBOTTOMFRAGMENT"
    }
}