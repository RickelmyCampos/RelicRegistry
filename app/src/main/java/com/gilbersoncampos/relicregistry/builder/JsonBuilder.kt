package com.gilbersoncampos.relicregistry.builder

class JsonBuilder {
    private var data:String=""
    fun add(key:String,value:Any):JsonBuilder{
        return this
    }
    fun addMessage(message:String):JsonBuilder{
        data = data.plus("\n$message")
        return this
    }
    fun build():String{
        return data
    }
    companion object{
        fun Builder():JsonBuilder{
            return JsonBuilder()
        }
    }
}