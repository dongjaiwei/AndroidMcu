package com.dongjiawei.filelibrary

import android.os.Environment
import android.text.TextUtils
import java.io.File

object FileUtils {
    fun open(fileConfig: FileConfig): Array<out File>? {
        val file = makeFile(fileConfig)
        return file.listFiles()
    }

    private fun makeFile(fileConfig: FileConfig): File {
        if (TextUtils.isEmpty(fileConfig.fileName)){
           return File(Environment.getExternalStorageState())
        }
        return File(fileConfig.fileName)
    }

}