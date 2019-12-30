package com.dongjiawei.nodemcu.fragment.log.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dongjiawei.nodemcu.Log.Log
import com.dongjiawei.nodemcu.R
import com.dongjiawei.nodemcu.fragment.log.adapter.view_holder.LogViewHolder

class LogAdapter : RecyclerView.Adapter<LogViewHolder>() {
    val arrayList = ArrayList<Log>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        return LogViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.log_item,parent,false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        holder.setLog(arrayList[position])
    }

    fun setData(it: List<Log>?) {
        if (it != null) {
            arrayList.clear()
            arrayList.addAll(it)
            notifyDataSetChanged()
        }
    }


}
