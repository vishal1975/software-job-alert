package com.vishal.softwarejobalert.SearchByLocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.vishal.softwarejobalert.CompanyList.CompanyModel
import com.vishal.softwarejobalert.databinding.ActivitySearchByLocationAndSkillBinding

class searchByLocationAndSkill : AppCompatActivity() {
    lateinit var binding:ActivitySearchByLocationAndSkillBinding
    lateinit var adapter1: SearchByLocationAndSkillAdapter
    lateinit var adapter2: SearchByLocationAndSkillAdapter
    var whatList=ArrayList<String>()
    var whereList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchByLocationAndSkillBinding.inflate(layoutInflater)
        setContentView(binding.root)
        whatList.add("android")
        whatList.add("web development  fefjenfjej")
        whereList.add("Banglore")
        whereList.add("chennannnnnnjjnjnjjjnjjjnjj")
        setRecyclerview()
        setEditTextListner()
    }

    private fun setEditTextListner() {


        binding.whatSkill.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var tempList:ArrayList<String> =ArrayList()
                var tempP= p0.toString().lowercase()
                 if(!tempP.isEmpty()) {


                     for (i in whatList) {

                         var name = i.lowercase()

                         if (name.contains(tempP)) {
                             tempList.add(i)
                         }


                     }
                 }
                adapter1.setList(tempList)
                adapter1.notifyDataSetChanged()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.whereSkill.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var tempList:ArrayList<String> =ArrayList()
                var tempP= p0.toString().lowercase()

               if(!tempP.isEmpty()) {
                   for (i in whereList) {

                       var name = i.lowercase()

                       if (name.contains(tempP)) {
                           tempList.add(i)
                       }


                   }
               }
                adapter2.setList(tempList)
                adapter2.notifyDataSetChanged()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun setRecyclerview() {
        adapter1 = SearchByLocationAndSkillAdapter(this)
       // adapter1.setList(whatList)
        adapter1.setCallback(object :NameClickCallback{
            override fun nameClick(name: String) {
                binding.whatSkill.setText(name)
            }

        })


        adapter2 = SearchByLocationAndSkillAdapter(this)
       // adapter2.setList(whereList)
        adapter2.setCallback(object :NameClickCallback{
            override fun nameClick(name: String) {
                binding.whereSkill.setText(name)
            }

        })
        binding.whatRecyclerview.layoutManager=LinearLayoutManager(this)
        binding.whatRecyclerview.adapter=adapter1

        binding.whereRecyclerview.layoutManager=LinearLayoutManager(this)
        binding.whereRecyclerview.adapter=adapter2

    }
}