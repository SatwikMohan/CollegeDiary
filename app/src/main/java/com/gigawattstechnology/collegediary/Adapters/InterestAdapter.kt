package com.gigawattstechnology.collegediary.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.gigawattstechnology.collegediary.CustomClasses.InterestData
import com.gigawattstechnology.collegediary.Interfaces.InterestClickListner
import com.gigawattstechnology.collegediary.R

class InterestAdapter(val context:Context,val items: ArrayList<InterestData>,val interestClickListner: InterestClickListner) : BaseAdapter(){
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView: View? =convertView
        var show=0
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.layout_interest, parent, false);
        }
        val interestData=items.get(position)
        val text=listItemView?.findViewById<TextView>(R.id.InterestField)
        text?.text=interestData.hobby
        val image=listItemView?.findViewById<ImageView>(R.id.InterestImage)
        image?.setImageResource(interestData.icon)
        val layout=listItemView?.findViewById<LinearLayout>(R.id.InterestLayout)
        layout?.setOnClickListener{
            if(show==0){
                layout.setBackgroundResource(R.drawable.outline_shape2)
                text?.setTextColor(Color.parseColor("#FFFFFFFF"))
                show=1
                interestClickListner.onInterestTaken(text?.text.toString())
            }else{
                layout.setBackgroundResource(R.drawable.outline_shape)
                text?.setTextColor(Color.parseColor("#FF000000"))
                show=0
                interestClickListner.onInterestDiscarded(text?.text.toString())
            }
        }
        return listItemView!!
    }
}