package com.vishal.softwarejobalert.NotificationList

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.vishal.softwarejobalert.databinding.FragmentNotificationListBinding
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationDisplayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationsListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
lateinit var binding: FragmentNotificationListBinding
var notificationList :ArrayList<NotificationModel> = ArrayList()
lateinit var adapter: NotificationListAdapter
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

        binding = FragmentNotificationListBinding.inflate(layoutInflater)

        adapter = NotificationListAdapter(requireContext(),notificationList)

        binding.notificationRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerview.adapter = adapter
        readData()

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotificationDisplayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotificationsListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun readData(){

        val sh = requireContext().getSharedPreferences("com.softwareAlet",Context.MODE_PRIVATE)

        val data = sh.getString("data","[]")
        Log.v("data","${data.toString()}")
        val jsonArray=JSONArray(data.toString())
        if(jsonArray.length()>0) {
            for (i in jsonArray.length() - 1 downTo 0) {

                val temp = jsonArray.getJSONObject(i)

                val image = temp.getString("image")
                val role = temp.getString("role")
                val role_info = temp.getString("role_info")
                val apply_link = temp.getString("apply_link")
                notificationList.add(
                    NotificationModel(
                        image, role, role_info, apply_link
                    )
                )

            }

            adapter.notifyDataSetChanged()
        }
    }

}