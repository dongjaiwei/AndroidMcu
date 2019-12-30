package com.dongjiawei.nodemcu.fragment.log.adapter.view_holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dongjiawei.nodemcu.Log.Log
import com.dongjiawei.nodemcu.R

class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val contentShow: TextView = itemView.findViewById(R.id.text)
    fun setLog(log: Log) {
        contentShow.text = log.logContent()
    }
}