package com.gigawattstechnology.collegediary

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.gigawattstechnology.collegediary.CustomClasses.DataRegisterDoc
import com.gigawattstechnology.collegediary.CustomClasses.FindDataBody
import com.gigawattstechnology.collegediary.CustomClasses.FindDataFilter
import com.gigawattstechnology.collegediary.CustomClasses.RetrofitInstance
import com.gigawattstechnology.collegediary.Interfaces.DataRegisterInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val actionBar=supportActionBar
        val colorDrawable= ColorDrawable(Color.parseColor("#E94057"))
        actionBar?.setBackgroundDrawable(colorDrawable)
        val sharedPreferences=getSharedPreferences("User", MODE_PRIVATE)
        val s1=sharedPreferences.getString("userMail","userMail")
        if(s1!="userMail"){
            startActivity(Intent(applicationContext,HomeActivity::class.java))
            finish()
        }
        val proceed=findViewById<Button>(R.id.ProceedSignIn)
        val Email=findViewById<EditText>(R.id.SignInEmail)
        val Password=findViewById<TextInputEditText>(R.id.SignInPassword)
        proceed.setOnClickListener {
            val email=Email.text.toString()
            val password=Password.text.toString()
            if(email.isEmpty()){
                Email.setError("Required Field")
                return@setOnClickListener
            }
            if(password.isEmpty()) {
                Password.setError("Required Field")
                return@setOnClickListener
            }
            val filter=FindDataFilter(email)
            val FindDataBody=FindDataBody("Cluster0","DataBase","Collections",filter)
            val apiInterface=RetrofitInstance.getRetrofit()?.create(DataRegisterInterface::class.java)
            apiInterface?.findOne(FindDataBody)?.enqueue(object :Callback<JsonObject>{
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    val out=response.body().toString()
                    //Toast.makeText(applicationContext,out,Toast.LENGTH_LONG).show()
                    if(out!="{\"document\":null}"){
                        //Toast.makeText(applicationContext,response.body().toString(),Toast.LENGTH_LONG).show()
                        val jsonObject= response.body()!!.getAsJsonObject("document")
                        val passw= jsonObject?.get("password").toString()
                        //Toast.makeText(applicationContext,passw,Toast.LENGTH_LONG).show()
                        val p="\""+password+"\""
                        if(p!=passw){
                            Password.setError("Incorrect Field")
                        }else{
                            val sharedPref=getSharedPreferences("User", MODE_PRIVATE)
                            val editor=sharedPref.edit()
                            editor.putString("userMail",email)
                            editor.putString("userPass",password)
                            editor.commit()
                            startActivity(Intent(applicationContext,HomeActivity::class.java))
                            //finish()
                        }
                    }else{
                        Toast.makeText(applicationContext,"Need to create an account first",Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(applicationContext,"Failure",Toast.LENGTH_LONG).show()
                }

            })
        }
    }
}