package com.dongjiawei.nodemcu.fragment.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel

open class BaseFragment<V :ViewModel>:Fragment() {
    var viewModel: V? = null
        set(value) {
            field = value
            dataLode(field)
        }

    open fun dataLode(field: V?) {

    }

    fun insert(activity:FragmentActivity,id:Int) {
        val beginTransaction = activity.supportFragmentManager.beginTransaction()
        beginTransaction.add(id,this)
        beginTransaction.commit()
    }

    fun remove(activity: FragmentActivity){
        val beginTransaction = activity.supportFragmentManager.beginTransaction()
        beginTransaction.remove(this)
        beginTransaction.commit()
    }




}