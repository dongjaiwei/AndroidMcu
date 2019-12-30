package com.dongjiawei.nodemcu.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dongjiawei.nodemcu.R
import com.dongjiawei.nodemcu.fragment.log.LogcatFragment
import com.dongjiawei.nodemcu.fragment.log.view_model.LogViewModel
import com.dongjiawei.nodemcu.mcu_manager.connect.OtgConnect
import com.dongjiawei.nodemcu.mcu_manager.connect.OtgConnect.Companion.getInstance
import com.dongjiawei.nodemcu.mcu_manager.entity.OtgConnectConfig
import kotlinx.android.synthetic.main.activity_connect.*


class ConnectActivity : AppCompatActivity() {

    lateinit var otgConnect: OtgConnect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)
        otgConnect = getInstance(OtgConnectConfig(this))
        val logcatFragment = LogcatFragment()
        val logViewModel = ViewModelProviders.of(this)[LogViewModel::class.java]
        logViewModel.getLogs().observe(this, Observer {
            logcatFragment.setData(it)
        })

//        addFragment(R.id.webFrame,WebEditFragment())
        addFragment(R.id.frame,logcatFragment)


        otgConnect.addLogObservable(logViewModel.it)
        look.setOnClickListener {
            nodeListFiles()
        }
        info.setOnClickListener {
            otgConnect.sendNodeCmd("\r=node.restart()")
        }
        restart.setOnClickListener {
            upLoadFimeToMcu("init.lua", "mytimer = tmr.create()\n" +
                    "mytimer:register(1000, tmr.ALARM_AUTO, function() print(\"hey there\") end)\n" +
                    "mytimer:interval(3000) \n" +
                    "mytimer:start() ")
        }

        readFile.setOnClickListener {
            readFile("init.lua")
        }

    }

    private fun addFragment(webFrame: Int, webEditFragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(
                webFrame,
                webEditFragment
            )
            .commit()

    }

    private fun nodeListFiles() {
        val cmd = "_dir=function()\n" +
                "     local k,v,l\n" +
                "     print(\"~~~File \"..\"list START~~~\")\n" +
                "     for k,v in pairs(file.list()) do \n" +
                "          l = string.format(\"%-15s\",k) \n" +
                "          print(l..\" : \"..v..\" bytes\") \n" +
                "     end \n" +
                "     print(\"~~~File \"..\"list END~~~\")\n" +
                "end\n" +
                "_dir()\n" +
                "_dir=nil"
        otgConnect.sendCmd(cmd)

    }


    private fun upLoadFimeToMcu(uploadFileName: String, content: String) {
        val cmd = (
                  "file.remove(\"" + uploadFileName + "\")\n"
                + "_up=function(n,l,ll)\n"
                + "     local cs = 0\n"
                + "     local i = 0\n"
                + "     print(\">\"..\" \")\n"
                + "     uart.on(\"data\", l, function(b) \n"
                + "          i = i + 1\n"
                + "          file.open(\"" + uploadFileName + "\",'a+')\n"
                + "          file.write(b)\n"
                + "          file.close()\n"
                + "          cs=0\n"
                + "          for j=1, l do\n"
                + "               cs = cs + (b:byte(j)*20)%19\n"
                + "          end\n"
                + "          uart.write(0,\"~~~CRC-\"..i..\"START~~~\"..cs..\"~~~CRC-\"..ll..\"END~~~\")\n"
                + "          if i == n then\n"
                + "               uart.on(\"data\")\n"
                + "          end\n"
                + "          if i == ll then\n"
                + "               node.restart()\n"
                + "          end\n"
                + "          end,0)\n"
                + "end\n"
                )

        val cmdList = ArrayList<String>()
        cmdList.addAll(cmdPrep(cmd))
        val contentList = ArrayList<String>()
        contentList.addAll(cmdPrep(content+"\r\n"))
        otgConnect.sendCmdList(cmdList, contentList)

    }


    private fun readFile(fn: String) {
        val cmd = ("_view=function()\n"
                + "local _line\n"
                + "if file.open(\"" + fn + "\",\"r\") then \n"
                + "    print(\"--FileView start\")\n"
                + "    repeat _line = file.readline() \n"
                + "        if (_line~=nil) then \n"
                + "            print(_line) \n"
                + "        end \n"
                + "    until _line==nil\n"
                + "    file.close() \n"
                + "    print(\"--FileView done.\") \n"
                + "else\n"
                + "  print(\"\\r--FileView error: can't open file\")\n"
                + "end\n"
                + "end\n"
                + "_view()\n"
                + "_view=nil\n")
        otgConnect.sendCmdList(cmdPrep(cmd))
    }


    private fun cmdPrep(cmd: String): ArrayList<String> {
        val str = cmd.split("\n").toTypedArray()
        val s256 = ArrayList<String>()
        var i = 0
        s256.add("")
        for (subs in str) {
            if (s256[i].length + subs.trim { it <= ' ' }.length <= 250) {
                s256[i] = s256[i] + " " + subs.trim { it <= ' ' }
            } else {
                s256[i] = s256[i] + "\r"
                s256.add(subs)
                i++
            }
        }
        return s256
    }


}
