package com.gigawattstechnology.collegediary.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.gigawattstechnology.collegediary.CustomClasses.ChatMsg
import com.gigawattstechnology.collegediary.R

class ChatAdapter(val context:Context,val list:ArrayList<ChatMsg>,val User:String): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.layout_messaging,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj=list.get(position)
        if(obj.user.equals(User)){
            holder.msgRecievedCard.visibility=View.GONE
            holder.msgSentCard.visibility=View.VISIBLE
            holder.msgSendText.text=obj.msg
        }else{
            holder.msgSentCard.visibility=View.GONE
            holder.msgRecievedCard.visibility=View.VISIBLE
            holder.msgRecievedText.text=obj.msg
        }
    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val msgSentCard=itemView.findViewById<CardView>(R.id.MeassageSentCard)
        val msgRecievedCard=itemView.findViewById<CardView>(R.id.MeassageRecievedCard)
        val msgSendText=itemView.findViewById<TextView>(R.id.MessageSentText)
        val msgRecievedText=itemView.findViewById<TextView>(R.id.MessageRecievedText)
    }

}