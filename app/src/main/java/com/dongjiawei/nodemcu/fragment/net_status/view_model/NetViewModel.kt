package com.dongjiawei.nodemcu.fragment.net_status.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongjiawei.nodemcu.fragment.net_status.dto.NetStatus

class NetViewModel : ViewModel() {
    val netstatu: MutableLiveData<NetStatus> by lazy {
        MutableLiveData<NetStatus>().also {

        }
    }
}


