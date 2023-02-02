package com.gigawattstechnology.collegediary.ui.home

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gigawattstechnology.collegediary.Adapters.AllDataAdapter
import com.gigawattstechnology.collegediary.CustomClasses.AllData
import com.gigawattstechnology.collegediary.CustomClasses.AllDataBody
import com.gigawattstechnology.collegediary.CustomClasses.AllDataFilter
import com.gigawattstechnology.collegediary.CustomClasses.RetrofitInstance
import com.gigawattstechnology.collegediary.Interfaces.DataRegisterInterface
import com.gigawattstechnology.collegediary.databinding.FragmentHomeBinding
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView=binding.AllDataRecyclerView
        val linearLayoutManager=LinearLayoutManager(root.context,LinearLayoutManager.HORIZONTAL,false)
        recyclerView.layoutManager=linearLayoutManager
        val list=ArrayList<AllData>()
        val progressDialog= ProgressDialog(binding.root.context)
        progressDialog.setMessage("Loading Data")
        progressDialog.show()
        val filter=AllDataFilter("all")
        val dataBody=AllDataBody("Cluster0","DataBase","Collections",filter)
        val apiInterface=RetrofitInstance.getRetrofit()?.create(DataRegisterInterface::class.java)
        apiInterface?.findAll(dataBody)?.enqueue(object :Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val jsonArray=response.body()?.getAsJsonArray("documents")
                for(i in jsonArray!!){
                    val jsonObject=i?.asJsonObject
                    val name=jsonObject?.get("email").toString()
                    val picUrl=jsonObject?.get("picURL").toString()
                    //Toast.makeText(root.context,picUrl.substring(1,picUrl.length-2), Toast.LENGTH_LONG).show()
                    list.add(AllData(name,picUrl))
                }
                progressDialog.dismiss()
                val adapter=AllDataAdapter(root.context,list)
                recyclerView.adapter=adapter
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(root.context,"Failure", Toast.LENGTH_LONG).show()
            }

        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}