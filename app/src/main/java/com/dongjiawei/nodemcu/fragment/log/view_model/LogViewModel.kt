package com.dongjiawei.nodemcu.fragment.log.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongjiawei.nodemcu.Log.Log

class LogViewModel : ViewModel() {
    var it: MutableLiveData<List<Log>> ?= null
    private val logs: MutableLiveData<List<Log>> by lazy {
        MutableLiveData<List<Log>>().also {
            loadLog(it)
        }
    }


    fun getLogs(): LiveData<List<Log>> {
        return logs
    }

    fun loadLog(it: MutableLiveData<List<Log>>) {
       this.it = it
    }


}