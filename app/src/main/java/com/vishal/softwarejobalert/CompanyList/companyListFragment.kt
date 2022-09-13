package com.vishal.softwarejobalert.CompanyList

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vishal.softwarejobalert.companyDetail.CompanyDetailActivity
import com.vishal.softwarejobalert.databinding.FragmentCompanyList2Binding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.vishal.softwarejobalert.Registeration.RegisterationBottomSheetFragment
import com.vishal.softwarejobalert.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [companyListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class companyListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding :FragmentCompanyList2Binding
    lateinit var adapter : CompanyListAdapter
    private lateinit var database: DatabaseReference
lateinit var edit:SharedPreferences.Editor
lateinit var read:SharedPreferences
lateinit var readRegisteration:SharedPreferences
    var companyList : ArrayList<CompanyModel> = ArrayList()
    var companyInfoList: ArrayList<CompanyInfoModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCompanyList2Binding.inflate(layoutInflater)
        showPopUp()
    edit=requireContext().getSharedPreferences("com.softwareAlet", Context.MODE_PRIVATE).edit()
        read =requireContext().getSharedPreferences("com.softwareAlet", Context.MODE_PRIVATE)
        readRegisteration = requireContext().getSharedPreferences("registeration", Context.MODE_PRIVATE)
        adapter = CompanyListAdapter(requireActivity())
        adapter.setList(companyList)
adapter.setCallback(object :ClickCallback{
    override fun subscribeButtonClick(position: Int) {
var companyName = companyList.get(position).companyName
        var isSubscribed = read.getString(companyName,"0")

        if(isSubscribed.equals("0")){


            if (readRegisteration.getBoolean("registeration",false)) {

                if(Constants.isNetworkAvailable(requireContext())) {
                    subscribeToTopic(companyList.get(position).companyName, position)
                }
                else{
                    Toast.makeText(requireContext(),"Please check your Internet Connection",Toast.LENGTH_LONG).show()
                }
            }
            else{
                var registerationBottomSheetFragment = RegisterationBottomSheetFragment()
                registerationBottomSheetFragment.show( childFragmentManager,
                    RegisterationBottomSheetFragment.TAG)
            }
        }
        else{
            unsubscribeToTopic(companyList.get(position).companyName,position)
        }
    }

    override fun companyNameClick(position: Int) {
        var intent = Intent(requireContext(),CompanyDetailActivity::class.java)
        intent.putExtra("companyName",companyInfoList.get(position).companyName)
        intent.putExtra("image",companyInfoList.get(position).companyImage)
        intent.putExtra("info",companyInfoList.get(position).companyInfo)
        startActivity(intent)
    }

})
        binding.companyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.companyRecyclerView.adapter = adapter

        database = Firebase.database.reference
        readData()
        readSeachText()

        binding.searchEditText.setOnClickListener {



        }

        return binding.root
    }

    private fun readSeachText() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var tempList:ArrayList<CompanyModel> =ArrayList()
                var tempP= p0.toString().lowercase()
                if(tempP.isEmpty()){
                    tempList=companyList
                }
                else {
                    for (i in companyList) {

                        var companyName = i.companyName.lowercase()

                        if (companyName.contains(tempP)) {
                            tempList.add(i)
                        }


                    }
                }
                adapter.setList(tempList)
                adapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    fun subscribeToTopic(topic: String,position:Int){
        binding.progressBar.visibility = View.VISIBLE
    Firebase.messaging.subscribeToTopic(topic.filter { !it.isWhitespace() })
        .addOnCompleteListener { task ->
            binding.progressBar.visibility = View.GONE
            var msg = "Subscribed"
            if (!task.isSuccessful) {
                msg = "Subscribe failed"
                //Toast.makeText(requireContext(),task.exception?.message,Toast.LENGTH_LONG).show()

            }
            else{


//                database.get().addOnCompleteListener {
//
//                    it.result.ref.child("CompanyList").child("$position").child("subscribed").setValue(1)
//                }
                var companyName = companyList.get(position).companyName
                edit.putString(companyName,"1")
                edit.apply()
                   adapter.notifyDataSetChanged()

                binding.progressBar.visibility = View.GONE
            }

            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }
}


    fun unsubscribeToTopic(topic:String,position: Int){
        binding.progressBar.visibility = View.VISIBLE

        Firebase.messaging.unsubscribeFromTopic(topic.filter { !it.isWhitespace() })
            .addOnCompleteListener { task ->
                var msg = "UnSubscribed"
                if (!task.isSuccessful) {
                    msg = "UnSubscribe failed"
                    binding.progressBar.visibility = View.GONE
                }
                else{


//                    database.get().addOnCompleteListener {
//
//                        it.result.ref.child("CompanyList").child("$position").child("subscribed").setValue(0)
//                    }


                    var companyName = companyList.get(position).companyName
                    edit.putString(companyName,"0")
                     edit.apply()
                    adapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE

                }

                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }

    }

    fun showPopUp(){


        var registerationBottomSheetFragment = RegisterationBottomSheetFragment()
        registerationBottomSheetFragment.show( childFragmentManager,
            RegisterationBottomSheetFragment.TAG)


         lifecycleScope.launch(Dispatchers.IO) {

              delay(10000)
             withContext(Dispatchers.Main){
           registerationBottomSheetFragment.dismiss()
                 Toast.makeText(requireContext(),"show",Toast.LENGTH_LONG).show()
             }

         }
    }

fun readData(){



    database.addValueEventListener(object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            binding.progressBar.visibility = View.VISIBLE
            companyList.clear()
            companyInfoList.clear()
            adapter.notifyDataSetChanged()
            val companyListSnapshot = dataSnapshot.child("CompanyList")
            Log.v("companyListSnapshot","${companyListSnapshot.value}")


            for(companySnapshot in companyListSnapshot.children){
                var companyName=""
                var image=""
                var info = ""
                var subscribed=0
                companySnapshot.children.forEach {
        Log.v("key","${it.key}")


                    if(it.key.equals("companyName")){
                        companyName=it.value.toString()
                    }

                    else if(it.key.equals("image")){
                        image=it.value.toString()
                    }

                    else if(it.key.equals("info")){
                        info = it.value.toString()
                    }

                    else if(it.key.equals("subscribed")){
                    subscribed =it.value.toString().toInt()
                    }


                }

                companyList.add(CompanyModel(companyName,subscribed))
                companyInfoList.add(CompanyInfoModel(image,companyName,info))



            }
            adapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.GONE

            // ...
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Toast.makeText(requireContext(),"${databaseError.toException()}",Toast.LENGTH_LONG).show()
            binding.progressBar.visibility = View.GONE
        }
    }
    )
}



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment companyListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            companyListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}