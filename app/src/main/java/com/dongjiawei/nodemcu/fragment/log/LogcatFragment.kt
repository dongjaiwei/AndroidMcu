package com.dongjiawei.nodemcu.fragment.log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dongjiawei.nodemcu.Log.Log
import com.dongjiawei.nodemcu.R
import com.dongjiawei.nodemcu.fragment.base.BaseFragment
import com.dongjiawei.nodemcu.fragment.log.adapter.LogAdapter
import com.dongjiawei.nodemcu.fragment.log.view_model.LogViewModel
import kotlinx.android.synthetic.main.logcat_fragment.*
import kotlin.math.log

class LogcatFragment : BaseFragment<LogViewModel>() {
    val logAdapter = LogAdapter()
    var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.logcat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.adapter = logAdapter
        linearLayoutManager = LinearLayoutManager(context)
        recycler.layoutManager = linearLayoutManager
    }

    fun setData(it: List<Log>?) {
        var isBottom = false
        val findLastVisibleItemPosition = linearLayoutManager?.findLastVisibleItemPosition()
        if(findLastVisibleItemPosition == logAdapter.itemCount-1){
            isBottom = true
        }
        logAdapter.setData(it)
        if (isBottom) it?.size?.let { it1 -> recycler.smoothScrollToPosition(it1) }

    }
}