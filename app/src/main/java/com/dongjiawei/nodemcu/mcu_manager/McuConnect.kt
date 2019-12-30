package com.dongjiawei.nodemcu.mcu_manager

import androidx.lifecycle.MutableLiveData
import com.dongjiawei.nodemcu.Log.Log
import com.dongjiawei.nodemcu.mcu_manager.entity.ConnectConfig
import kotlin.collections.ArrayList

interface McuConnect {
    fun connect(connectConfig: ConnectConfig)
    fun isLife(): Boolean
    fun addLogObservable(it: MutableLiveData<List<Log>>?)
    fun sendCmd(cmd: String)
    fun sendNodeCmd(addCRLF: String)
    fun sendCmdList(
        cmdList: ArrayList<String>,
        contentList: ArrayList<String>
    )

    fun sendCmdList(cmdList: java.util.ArrayList<String>)
}