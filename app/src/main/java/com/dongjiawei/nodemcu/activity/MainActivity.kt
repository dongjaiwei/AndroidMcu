package com.dongjiawei.nodemcu.activity

import android.app.ActivityOptions.makeSceneTransitionAnimation
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dongjiawei.nodemcu.NetUtils
import com.dongjiawei.nodemcu.R
import com.dongjiawei.nodemcu.fragment.net_status.NetFrame
import com.dongjiawei.nodemcu.fragment.net_status.dto.NetStatus
import com.dongjiawei.nodemcu.fragment.net_status.view_model.NetViewModel

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val netViewModel:NetViewModel= NetViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener { view ->
            val option =   makeSceneTransitionAnimation(this,fab,"fab")
            startActivity(Intent(this,FileActivity::class.java),option.toBundle())
        }
        val netFrame = NetFrame()
        netFrame.insert(this,R.id.net_frame)
        netFrame.viewModel = netViewModel
        initModelDate()


    }

    private fun initModelDate() {
        val ipAddress = NetUtils.getIpAddress(this)
        netViewModel.netstatu.postValue(NetStatus("ip",ipAddress))



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }




}
