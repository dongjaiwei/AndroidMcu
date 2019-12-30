package com.dongjiawei.nodemcu.mcu_manager.connect

import android.content.Context
import android.hardware.usb.UsbManager
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import cn.wch.ch34xuartdriver.CH34xUARTDriver
import com.dongjiawei.nodemcu.Log.Log
import com.dongjiawei.nodemcu.Log.VLog
import com.dongjiawei.nodemcu.mcu_manager.McuConnect
import com.dongjiawei.nodemcu.mcu_manager.entity.ConnectConfig
import com.dongjiawei.nodemcu.mcu_manager.entity.OtgConnectConfig

class OtgConnect private constructor() : McuConnect {
    private var it: MutableLiveData<List<Log>>? = null
    private var logLive = false
    private val ACTION_USB_PERMISSION = "cn.wch.wchusbdriver.USB_PERMISSION"
    lateinit var cH34xUARTDriver: CH34xUARTDriver
    var otgConnectConfig: OtgConnectConfig?=null

    override fun connect(connectConfig: ConnectConfig) {
        otgConnectConfig = connectConfig as OtgConnectConfig
        reConnect()
    }

    private fun reConnect() {
        cH34xUARTDriver = CH34xUARTDriver(
            otgConnectConfig?.context?.getSystemService(Context.USB_SERVICE) as UsbManager,
            otgConnectConfig?.context,
            ACTION_USB_PERMISSION
        )
        val resumeUsbList = cH34xUARTDriver.ResumeUsbList()
        if (resumeUsbList) {
            if (cH34xUARTDriver.isConnected) {
                cH34xUARTDriver.SetConfig(115200, 8, 1, 0, 0)
            }

        }
    }

    override fun isLife(): Boolean {
        return cH34xUARTDriver.isConnected
    }

    override fun addLogObservable(it: MutableLiveData<List<Log>>?) {
        this.it = it
        startThread(it)

    }

    private fun startThread(it: MutableLiveData<List<Log>>?) {
        if (it != null) {
            Thread(Runnable {
                while (cH34xUARTDriver.isConnected) {
                    logLive = true
                    val buffer = ByteArray(4096)
                    Thread.sleep(200)
                    val length: Int = cH34xUARTDriver.ReadData(buffer, 4096)
                    val recv = String(buffer, 0, length)
                    if (!TextUtils.isEmpty(recv)) {
                        var value = it.value  as MutableList<Log>?
                        if(value == null){
                            value = ArrayList<Log>()
                        }
                        value.add(VLog(recv))
                        it.postValue(value)
                    }
                    Thread.sleep(100)
                }
                logLive = false
            }).start()
        }
    }

    override fun sendCmd(cmd: String) {
        if(!logLive){
            reConnect()
            startThread(it)
        }
        cH34xUARTDriver.WriteData(cmd.toByteArray(charset = Charsets.UTF_8), cmd.length)
    }

    override fun sendNodeCmd(crlf: String) {
        sendCmd(addCRLF(crlf))
    }

    override fun sendCmdList(cmdList: ArrayList<String>, contentList: ArrayList<String>) {
        val sendCmd = ArrayList<String>()
        sendCmd.addAll(cmdList)
        for (s in sendCmd) {
            sendCmd(addCR(s))
        }
        val s = "_up(${getSize(contentList)},${getSize(contentList)},${contentList.size})"
        sendCmd(addCRLF(s))

        for (index in contentList.indices) {
            sendCmd(contentList[index])
        }
        sendCmd(addCRLF("_up=nil"))
    }

    private fun getSize(contentList: java.util.ArrayList<String>): Int {
        var int = 0
        for (s in contentList) {
            int += s.length
        }
        return int
    }

    override fun sendCmdList(cmdList: java.util.ArrayList<String>) {
        for (s in cmdList) {
            sendCmd(addCR(s))
        }
    }


    fun addCR(s: String): String {
        var r = s
        r += 13.toChar()
        return r
    }

    fun addCRLF(s: String): String {
        var r = s
        r += 13.toChar()
        r += 10.toChar()
        return r
    }

    companion object{
        fun getInstance(otgConnectConfig: OtgConnectConfig):OtgConnect{
            val otgConnect = OtgConnect()
            otgConnect.connect(otgConnectConfig)
            return otgConnect
        }
    }


}