package com.gigawattstechnology.collegediary.ui.notifications

import android.app.ProgressDialog
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.gigawattstechnology.collegediary.CustomClasses.FindDataBody
import com.gigawattstechnology.collegediary.CustomClasses.FindDataFilter
import com.gigawattstechnology.collegediary.CustomClasses.RetrofitInstance
import com.gigawattstechnology.collegediary.Interfaces.DataRegisterInterface
import com.gigawattstechnology.collegediary.databinding.FragmentNotificationsBinding
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val sharedPreferences=root.context.getSharedPreferences("User", MODE_PRIVATE)
        val email=sharedPreferences.getString("userMail","userMail")
        val filter= FindDataFilter(email!!)
        val FindDataBody= FindDataBody("Cluster0","DataBase","Collections",filter)
        val progressDialog=ProgressDialog(binding.root.context)
        progressDialog.setMessage("Loading Data")
        progressDialog.show()
        val apiInterface= RetrofitInstance.getRetrofit()?.create(DataRegisterInterface::class.java)
        apiInterface?.findOne(FindDataBody)?.enqueue(object: Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val jsonObject=response.body()?.getAsJsonObject("document")
                val myprofilename=jsonObject?.get("email")
                val myprofilebithdate=jsonObject?.get("BirthDate")
                val url=jsonObject?.get("picURL").toString()
                val myprofilepic=url.substring(1,url.length-2)
                binding.MyProfileName.text=myprofilename.toString()
                binding.MyProfileBirthDate.text=myprofilebithdate.toString()
                Glide.with(root.context).load(myprofilepic).into(binding.MyProfilePic)
                val myinterests=jsonObject?.getAsJsonArray("interests")
                for(i in myinterests!!){
                    //Toast.makeText(root.context,i.toString(),Toast.LENGTH_LONG).show()
                    binding.MyProfileInterests.append(i.toString()+"\n")
                }
                progressDialog.dismiss()
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