package com.example.softwarejobalert.CompanyList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.softwarejobalert.R
import com.example.softwarejobalert.companyDetail.CompanyDetailActivity
import com.example.softwarejobalert.databinding.FragmentCompanyList2Binding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import org.json.JSONObject

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


        adapter = CompanyListAdapter(requireActivity(),companyList)
adapter.setCallback(object :ClickCallback{
    override fun subscribeButtonClick(position: Int) {

        if(companyList.get(position).Subscribed==0){



            subscribeToTopic(companyList.get(position).companyName,position)

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

        return binding.root
    }

fun subscribeToTopic(topic: String,position:Int){
    Firebase.messaging.subscribeToTopic(topic)
        .addOnCompleteListener { task ->
            var msg = "Subscribed"
            if (!task.isSuccessful) {
                msg = "Subscribe failed"
            }
            else{


                database.get().addOnCompleteListener {

                    it.result.ref.child("CompanyList").child("$position").child("subscribed").setValue(1)
                }


            }

            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }
}


    fun unsubscribeToTopic(topic:String,position: Int){

        Firebase.messaging.unsubscribeFromTopic(topic)
            .addOnCompleteListener { task ->
                var msg = "UnSubscribed"
                if (!task.isSuccessful) {
                    msg = "UnSubscribe failed"
                }
                else{


                    database.get().addOnCompleteListener {

                        it.result.ref.child("CompanyList").child("$position").child("subscribed").setValue(0)
                    }

                }

                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }

    }

fun readData(){


    database.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
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


               // Log.v("companySnapshot","${companySnapshot.child(companySnapshot.key.toString()).child("image").value}")
               // val jsonObject = JSONObject(companySnapshot.getValue().toString())


//                var image:String = jsonObject.getString("image")
//                var companyName : String = jsonObject.getString("companyName")
//                var info:String = jsonObject.getString("info")
//                var subscribed:Int = jsonObject.getInt("subscribed")
//                companyList.add(CompanyModel(companyName,subscribed))
//                adapter.notifyDataSetChanged()
//
//                companyInfoList.add(CompanyInfoModel(image,companyName,info))
            }
            adapter.notifyDataSetChanged()
            // ...
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Toast.makeText(requireContext(),"${databaseError.toException()}",Toast.LENGTH_LONG).show()

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