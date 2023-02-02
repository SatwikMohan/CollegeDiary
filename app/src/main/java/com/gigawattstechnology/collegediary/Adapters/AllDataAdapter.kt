package com.gigawattstechnology.collegediary.Adapters

import android.R.attr.animation
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gigawattstechnology.collegediary.CustomClasses.AllData
import com.gigawattstechnology.collegediary.R


class AllDataAdapter(val context: Context,val list:ArrayList<AllData>):RecyclerView.Adapter<AllDataAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.all_data_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val allData=list.get(position)
        holder.textView.text=allData.name
        val url=allData.picUrl
        holder.imageView.clipToOutline=true
        Glide.with(context).load(url.substring(1,url.length-2)).into(holder.imageView)
        val animation=AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_slide_in_bottom)
        animation.setInterpolator(AccelerateDecelerateInterpolator())
        holder.itemView.animation=animation
        animation.start()
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val imageView=itemView.findViewById<ImageView>(R.id.UserImageData)
        val textView=itemView.findViewById<TextView>(R.id.UserNameData)
    }
}