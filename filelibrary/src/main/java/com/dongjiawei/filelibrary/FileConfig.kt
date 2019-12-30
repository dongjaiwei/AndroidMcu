package com.dongjiawei.filelibrary

class FileConfig private constructor(builder: Builder) {
    var fileName = builder.fileName
    //是否过滤后缀
    var suffix = builder.suffix


    fun newBuilder(): Builder {
        val builder = Builder()
        builder.fileName = fileName
        builder.suffix = suffix
        return builder
    }


    class Builder {
        var fileName: String = ""
        var suffix:String = ""
        fun fileName(fileName: String): Builder {
            this.fileName = fileName
            return this
        }
        fun suffix(suffix: String): Builder {
            this.suffix = suffix
            return this
        }

        fun builder():FileConfig{
            return FileConfig(this)
        }

    }
}