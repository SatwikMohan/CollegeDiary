package com.gigawattstechnology.collegediary.ui.chat

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gigawattstechnology.collegediary.Adapters.ChatAdapter
import com.gigawattstechnology.collegediary.CustomClasses.ChatMsg
import com.gigawattstechnology.collegediary.R
import com.gigawattstechnology.collegediary.databinding.FragmentChatBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ChatFragment : Fragment() {
lateinit var adapter: ChatAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentChatBinding.inflate(inflater,container,false)

        val sharedpref=binding.root.context.getSharedPreferences("User",AppCompatActivity.MODE_PRIVATE)
        val user=sharedpref.getString("userMail","userMail").toString()

        //val documentRef= Firebase.firestore.collection("messages")

        val database=FirebaseDatabase.getInstance().getReference()

        binding.MessageSendBtn.setOnClickListener {
            val msg=binding.MessengerEditText.text.toString()
//            val data=HashMap<String,String>()
//            data.put(user,msg)
//            if(!msg.isEmpty()){
//                documentRef.document(System.currentTimeMillis().toString()).set(data).addOnSuccessListener {
//                    Toast.makeText(binding.root.context,"Send",Toast.LENGTH_LONG).show()
//                }.addOnFailureListener{
//                    Toast.makeText(binding.root.context,"Not Send",Toast.LENGTH_LONG).show()
//                }
//            }else{
//                Toast.makeText(binding.root.context,"Say Something",Toast.LENGTH_LONG).show()
//            }
            if(!msg.isEmpty()){
                val key=System.currentTimeMillis().toString()
                database.child(key).child("User").setValue(user)
                database.child(key).child("Message").setValue(msg)
                binding.MessengerEditText.setText("")
            }else{
                Toast.makeText(binding.root.context,"Say Something",Toast.LENGTH_LONG).show()
            }
        }

        val list=ArrayList<ChatMsg>()
        val recyclerView=binding.ChatRecyclerView

//        documentRef.get().addOnSuccessListener {
//            for(doc in it){
//                val data=doc.data
//                list.add(ChatMsg(data.keys.first(),data.getValue(data.keys.first()).toString()))
//                //Toast.makeText(binding.root.context,data.keys.first()+" "+data.getValue(data.keys.first()).toString(),Toast.LENGTH_LONG).show()
//            }
//        }
        database.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for(data in snapshot.children){
                    list.add(ChatMsg(data.child("User").value.toString(),data.child("Message").value.toString()))
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(binding.root.context,"An Error has Occurred",Toast.LENGTH_LONG).show()
            }

        })

        recyclerView.setHasFixedSize(true)
        val linearLayoutManager=LinearLayoutManager(binding.root.context,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager=linearLayoutManager
        adapter=ChatAdapter(binding.root.context,list,user)
        recyclerView.adapter=adapter

        return binding.root
    }

}