package com.example.demoeventsbooking.homeSection.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demoeventsbooking.R
import com.example.demoeventsbooking.databinding.LayoutEventItemBinding
import com.example.demoeventsbooking.homeSection.dataManager.MasterListEventModel

class EventAdapter(private val mContext:Context?,private var mEventList:ArrayList<MasterListEventModel>?) : RecyclerView.Adapter<EventAdapter.MyViewHolder>(){

    fun updateList(eventList:ArrayList<MasterListEventModel>?) {
        mEventList = eventList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.MyViewHolder {
        val layoutEventItemBinding : LayoutEventItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.layout_event_item,parent,false)
        return MyViewHolder(layoutEventItemBinding)
    }

    override fun getItemCount(): Int = mEventList?.size ?: 0

    override fun onBindViewHolder(holder: EventAdapter.MyViewHolder, position: Int) {
        val eventData = mEventList?.get(position)
        holder.itemBinding.model = eventData
        holder.itemBinding.eventPrice.text = if(eventData?.eventPriceDisplayString?.equals("0",ignoreCase = false) == true) "FREE" else eventData?.eventPriceDisplayString
        holder.itemBinding.eventDate.isSelected = true
        setImageWithGlide(holder.itemBinding.eventIv,eventData)
    }

    private fun setImageWithGlide(eventIv: ImageView, eventData: MasterListEventModel?) {
        if(mContext==null) {
            eventIv.setImageResource(R.drawable.ic_launcher_background)
            return
        }

        eventData?.horizontalCoverImage?.let {
            Glide
                .with(mContext)
                .load(it)
                .placeholder(R.drawable.ic_launcher_background)
                .into(eventIv)
        }
    }

    inner class MyViewHolder(val itemBinding: LayoutEventItemBinding) : RecyclerView.ViewHolder(itemBinding.root)
}