package com.gigawattstechnology.collegediary.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.gigawattstechnology.collegediary.CustomClasses.SliderData
import com.gigawattstechnology.collegediary.R
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(val list:ArrayList<SliderData>,val context:Context): SliderViewAdapter<SliderAdapter.ViewHolder>() {

    override fun getCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.slider_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        val sliderObjet=list.get(position)
        viewHolder?.textView?.text=sliderObjet.message
        viewHolder?.imageView?.setImageResource(sliderObjet.imageResID)
    }
    class ViewHolder(val itemView:View) : SliderViewAdapter.ViewHolder(itemView) {
        val textView=itemView.findViewById<TextView>(R.id.SlideMessage)
        val imageView=itemView.findViewById<ImageView>(R.id.SlideImage);
    }
}