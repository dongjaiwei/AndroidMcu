package com.dongjiawei.nodemcu.Log

class VLog(val content:String): Log() {
    override fun logContent(): String {
        return content
    }
}