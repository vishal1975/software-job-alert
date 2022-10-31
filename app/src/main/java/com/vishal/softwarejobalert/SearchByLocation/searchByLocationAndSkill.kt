package com.vishal.softwarejobalert.SearchByLocation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.vishal.softwarejobalert.ModelClasses.JobDetail
import com.vishal.softwarejobalert.databinding.ActivitySearchByLocationAndSkillBinding
import org.json.JSONObject
import java.net.InetAddress
import java.net.NetworkInterface
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class searchByLocationAndSkill : AppCompatActivity() {
    lateinit var binding:ActivitySearchByLocationAndSkillBinding
    lateinit var adapter1: SearchByLocationAndSkillAdapter
    lateinit var adapter2: SearchByLocationAndSkillAdapter
    lateinit var jobDetailAdapter: JobDetailAdapter
    var whatList=ArrayList<String>()
    var whereList = ArrayList<String>()
    var jobDetailList = ArrayList<JobDetail>()
    lateinit var repository: SearchByLocationAndSkillRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchByLocationAndSkillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = SearchByLocationAndSkillRepository(application)
        observer()
        binding.search.setOnClickListener(){
            jobDetailList.clear()
            jobDetailAdapter.notifyDataSetChanged()
            binding.whatRecyclerview.visibility = View.GONE
            binding.whereRecyclerview.visibility = View.GONE
            var ip=getIPAddress(true)
            if(ip.isEmpty()){
                ip="2409:4050:2e17:f563:f057:f3fa:765f:6983"
            }
   Toast.makeText(this,"search",Toast.LENGTH_LONG).show()
            repository.getAllJobs("3802",ip,binding.whatSkill.text.toString(),binding.whereSkill.text.toString(),20,1)

        }
        whatList.add("android")
        whatList.add("web development  fefjenfjej")
        whereList.add("Banglore")
        whereList.add("Noida")
        whereList.add("chennannnnnnjjnjnjjjnjjjnjj")
        setRecyclerview()
        setEditTextListner()

    }

    private fun setEditTextListner() {


        binding.whatSkill.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.whatRecyclerview.visibility = View.VISIBLE

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
                binding.whereRecyclerview.visibility = View.VISIBLE
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
        jobDetailAdapter = JobDetailAdapter(this)
        binding.jobDetailRecyclerview.layoutManager=LinearLayoutManager(this)
        binding.jobDetailRecyclerview.adapter = jobDetailAdapter
        binding.whatRecyclerview.layoutManager=LinearLayoutManager(this)
        binding.whatRecyclerview.adapter=adapter1

        binding.whereRecyclerview.layoutManager=LinearLayoutManager(this)
        binding.whereRecyclerview.adapter=adapter2

    }

    fun observer(){
repository.allJobs.observe(this){

    val json = JSONObject(it)

    val data=json.getJSONArray("data")

    for (i in 0 until data.length()){

        val detail = data.getJSONObject(i)
        val title = detail.getString("title")
        val companyName = detail.getString("company")
        val location =detail.getString("location")
        val url:String =detail.getString("url")
        val job_detail:String =detail.getString("snippet")
        val date =detail.getString("age")

        val job_date: String = parseDateToddMMyyyy(date)
        Log.v("searchByLocationAndSkil","$title + $job_date")
        jobDetailList.add(JobDetail(title,companyName,location,job_date,url,job_detail))

    }
jobDetailAdapter.setList(jobDetailList)
    jobDetailAdapter.notifyDataSetChanged()
}
    }
    fun getAllJobs(){




    }

    fun getMACAddress(interfaceName: String?): String? {
        try {
            val interfaces: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (intf in interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().lowercase(Locale.ROOT).equals(interfaceName.lowercase())) continue
                }
                val mac: ByteArray = intf.getHardwareAddress() ?: return ""
                val buf = StringBuilder()
                for (aMac in mac) buf.append(String.format("%02X:", aMac))
                if (buf.length > 0) buf.deleteCharAt(buf.length - 1)
                return buf.toString()
            }
        } catch (ignored: Exception) {
        } // for now eat exceptions
        return ""

    }
    fun getIPAddress(useIPv4: Boolean): String {
        try {
            val interfaces: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (intf in interfaces) {
                val addrs: List<InetAddress> = Collections.list(intf.inetAddresses)
                for (addr in addrs) {
                    if (!addr.isLoopbackAddress()) {
                        val sAddr: String = addr.getHostAddress()
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        val isIPv4 = sAddr.indexOf(':') < 0
                        if (useIPv4) {
                            if (isIPv4) return sAddr
                        } else {
                            if (!isIPv4) {
                                val delim = sAddr.indexOf('%') // drop ip6 zone suffix
                                return if (delim < 0) sAddr.uppercase(Locale.getDefault()) else sAddr.substring(
                                    0,
                                    delim
                                ).uppercase(
                                    Locale.getDefault()
                                )
                            }
                        }
                    }
                }
            }
        } catch (ignored: java.lang.Exception) {
        } // for now eat exceptions
        return ""
    }
    fun parseDateToddMMyyyy(time: String): String {

        val new_time=StringBuilder("")
        var count =0
        for(c in time){
            if(c.isWhitespace()){
                count++
                new_time.append(c)
            }
            if(count> 0 && (c.isDigit() || c==',')){
                new_time.append(c)
            }
            if(count==0){
                new_time.append(c)
            }

        }
        val inputPattern = "LLL dd, yyyy"
        val outputPattern = "dd.MM.yyyy"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)
        var date: Date? = null
        var str: String = ""
        try {
            date = inputFormat.parse(new_time.toString())
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
    }
}