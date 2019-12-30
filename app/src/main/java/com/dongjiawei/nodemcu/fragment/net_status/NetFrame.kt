package com.dongjiawei.nodemcu.fragment.net_status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dongjiawei.nodemcu.R
import com.dongjiawei.nodemcu.fragment.base.BaseFragment
import com.dongjiawei.nodemcu.fragment.net_status.view_model.NetViewModel
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.frame_net.*

class NetFrame: BaseFragment<NetViewModel>() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(container?.context).inflate(R.layout.frame_net,container,false)
    }



    override fun dataLode(field: NetViewModel?) {
        field?.netstatu?.observe(this, Observer {
            status.setText(it.status)
            ip.setText(it.ip)
        })
    }
}