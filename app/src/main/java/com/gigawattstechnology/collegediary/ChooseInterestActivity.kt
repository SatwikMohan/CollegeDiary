package com.gigawattstechnology.collegediary

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import com.gigawattstechnology.collegediary.Adapters.InterestAdapter
import com.gigawattstechnology.collegediary.CustomClasses.*
import com.gigawattstechnology.collegediary.Interfaces.DataRegisterInterface
import com.gigawattstechnology.collegediary.Interfaces.InterestClickListner
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseInterestActivity : AppCompatActivity(),InterestClickListner {
    val interestList=ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_interest)
        val list=ArrayList<InterestData>()
        val actionBar=supportActionBar
        val colorDrawable= ColorDrawable(Color.parseColor("#E94057"))
        actionBar?.setBackgroundDrawable(colorDrawable)
        list.add(InterestData(R.drawable.ic_baseline_airplanemode_active_24,"Travelling"))
        list.add(InterestData(R.drawable.ic_baseline_shopping_bag_24,"Shopping"))
        list.add(InterestData(R.drawable.ic_baseline_sports_baseball_24,"Tennis"))
        list.add(InterestData(R.drawable.ic_baseline_waves_24,"Swimming"))
        list.add(InterestData(R.drawable.ic_baseline_music_note_24,"Music"))
        list.add(InterestData(R.drawable.ic_baseline_keyboard_voice_24,"Karaoke"))
        list.add(InterestData(R.drawable.ic_baseline_camera_alt_24,"Photography"))
        list.add(InterestData(R.drawable.ic_baseline_videogame_asset_24,"Video Games"))
        val gridView=findViewById<GridView>(R.id.InterestGrid)
        val adapter=InterestAdapter(this,list,this)
        gridView.adapter=adapter
        val Continue=findViewById<Button>(R.id.Continue)
        Continue.setOnClickListener {
            if(interestList.size>0){
                val email=intent.getStringExtra("email")
                val password=intent.getStringExtra("password")
                val picURL=intent.getStringExtra("profilePicURL")
                val birthDate=intent.getStringExtra("birthdaydate")
                Toast.makeText(this,email+" "+password+" "+picURL+" "+birthDate,Toast.LENGTH_LONG).show()
                for(i in interestList){
                    Toast.makeText(this,i,Toast.LENGTH_LONG).show()
                }
                val dataDoc= DataRegisterDoc(email,password,picURL,birthDate,interestList,"all")
                val dataBody=DataRegisterBody("Cluster0","DataBase","Collections",dataDoc)
                val apiInterface:DataRegisterInterface= RetrofitInstance.getRetrofit()!!.create(DataRegisterInterface::class.java)
                val call=apiInterface.insertOne(dataBody)
                call.enqueue(object :Callback<RegisterResponseBody>{
                    override fun onResponse(call: Call<RegisterResponseBody>, response: Response<RegisterResponseBody>) {
                        if(response.body()!=null){
                            val sharedPreferences=getSharedPreferences("User", MODE_PRIVATE)
                            val editor=sharedPreferences.edit()
                            editor.putString("userMail",email)
                            editor.putString("userPass",password)
                            for(i in interestList.indices){
                                editor.putString("Interest"+i,interestList.get(i))
                            }
                            editor.putInt("InterestSize",interestList.size)
                            editor.commit()
                            startActivity(Intent(applicationContext,HomeActivity::class.java))
                            Toast.makeText(applicationContext,response.body().toString(),Toast.LENGTH_LONG).show()
                            //finish()
                        }else{
                            Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponseBody>, t: Throwable) {
                        Toast.makeText(applicationContext,"Failure",Toast.LENGTH_LONG).show()
                    }

                })
            }else{
                Toast.makeText(applicationContext,"Choose Interests",Toast.LENGTH_LONG).show()
            }

        }

    }

    override fun onInterestTaken(interest: String) {
        interestList.add(interest)
    }

    override fun onInterestDiscarded(interest: String) {
        interestList.remove(interest)
    }
}