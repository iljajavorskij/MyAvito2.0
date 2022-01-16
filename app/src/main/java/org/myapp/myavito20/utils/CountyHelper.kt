package org.myapp.myavito20.utils

import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

object CountyHelper {
    fun getAllCity(context: Context):ArrayList<String>{
        val arrayList = ArrayList<String>()
        try {
            val inputStream:InputStream = context.assets.open("jsonformatter.json")
            val size = inputStream.available()
            val byteArray = ByteArray(size)
            inputStream.read(byteArray)
            val jsonCity = String(byteArray)
            val jsonObject = JSONObject(jsonCity)
            val countryName = jsonObject.names()
            if (countryName != null){
                for (i in 0 until countryName.length()){
                    arrayList.add(countryName.getString(i))
                }
            }

        }catch (e:IOException){
            Log.d("error",e.message.toString())
        }
     return arrayList
    }

    fun filterSearchText(list:ArrayList<String>,text:String?):ArrayList<String>{
        val tempList = ArrayList<String>()
        tempList.clear()
        if (text == null){
            tempList.add("result")
            return tempList
        }
        for (selected :String in list){
            if (selected.lowercase().startsWith(text.lowercase())){
                tempList.add(selected)
            }
        }
        if (tempList.size == 0){
            tempList.add("no result")
        }
        return tempList
    }
}