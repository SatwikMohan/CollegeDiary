package com.gigawattstechnology.collegediary

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.ActivityOptions
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.gigawattstechnology.collegediary.databinding.ActivitySignUpBinding
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class SignUpActivity : AppCompatActivity() {
    var pic=0
    lateinit var binding:ActivitySignUpBinding
    lateinit var uri:Uri
    var birth=0;
    lateinit var bithDayDate:String
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar=supportActionBar
        val colorDrawable= ColorDrawable(Color.parseColor("#E94057"))
        actionBar?.setBackgroundDrawable(colorDrawable)
        binding.EditPic.setOnClickListener{
            if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                val intent=Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.setType("image/*")
                startActivityForResult(intent,1)
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), 10000);
            }
        }
        binding.CheckMark.visibility= View.GONE
        binding.ChooseBirthDay.setOnClickListener{
            val calendar=Calendar.getInstance()
            val year=calendar.get(Calendar.YEAR)
            val month=calendar.get(Calendar.MONTH)
            val day=calendar.get(Calendar.DAY_OF_MONTH)
            val datePicker=DatePickerDialog(binding.root.context,object :DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    bithDayDate=""+dayOfMonth+"/"+(month+1)+"/"+year
                    binding.CheckMark.visibility= View.VISIBLE
                    birth=1
                }
            },year,month,day)
            datePicker.show()
        }
        binding.Proceed.setOnClickListener {
            val email=binding.SignUpEmail.text.toString()
            val password=binding.SignUpPassword.text.toString()
            if(email.isEmpty()){
                binding.SignUpEmail.setError("Required Field")
                return@setOnClickListener
            }
            if(password.isEmpty()) {
                binding.SignUpPassword.setError("Required Field")
                return@setOnClickListener
            }
            if(birth==0){
                Toast.makeText(binding.root.context,"Choose your birthday",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            var url:String="https://firebasestorage.googleapis.com/v0/b/collegediary-af572.appspot.com/o/Enchanted.png?alt=media&token=6f0389e8-bc2c-4807-b842-fed560594409"
            if(pic==1){
                val storageRef=FirebaseStorage.getInstance().reference.child(uri.lastPathSegment.toString())
                storageRef.putFile(uri).addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener {
                        url=it.toString()
                        val intent=Intent(binding.root.context,ChooseInterestActivity::class.java)
                        intent.putExtra("email",email)
                        intent.putExtra("password",password)
                        intent.putExtra("profilePicURL",url)
                        intent.putExtra("birthdaydate",bithDayDate)
                        val options= ActivityOptions.makeCustomAnimation(binding.root.context, androidx.appcompat.R.anim.abc_popup_enter, com.google.android.material.R.anim.abc_fade_out);
                        startActivity(intent,options.toBundle())
                    }
                }.addOnFailureListener{
                    Toast.makeText(binding.root.context,"Try Again Later!!",Toast.LENGTH_LONG).show()
                }
            }else{
                val intent=Intent(binding.root.context,ChooseInterestActivity::class.java)
                intent.putExtra("email",email)
                intent.putExtra("password",password)
                intent.putExtra("profilePicURL",url)
                intent.putExtra("birthdaydate",bithDayDate)
                val options= ActivityOptions.makeCustomAnimation(binding.root.context, androidx.appcompat.R.anim.abc_popup_enter, com.google.android.material.R.anim.abc_fade_out);
                startActivity(intent,options.toBundle())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1&&data!=null){
            uri= data.data!!
            pic=1
           binding.ProfilePic.setImageURI(uri)
        }
    }
}