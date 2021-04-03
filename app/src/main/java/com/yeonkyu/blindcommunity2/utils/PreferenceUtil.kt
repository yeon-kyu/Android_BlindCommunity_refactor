package com.yeonkyu.blindcommunity2.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {

    companion object{
        const val Id = "USER_ID"
        const val Pw = "USER_PASSWORD"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name",Context.MODE_PRIVATE)

    fun setId(id:String){
        prefs.edit().putString(Id,id).apply()
    }
    fun getId():String{
        return prefs.getString(Id,"").toString()
    }

    fun setPw(pw:String){
        prefs.edit().putString(Pw,pw).apply()
    }
    fun getPw(id:String):String{
        return prefs.getString(id,"").toString()
    }

    fun removeUserData(){
        prefs.edit().remove(Id).apply()
        prefs.edit().remove(Pw).apply()
    }
}