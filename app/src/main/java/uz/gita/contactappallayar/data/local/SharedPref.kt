package uz.gita.contactappallayar.data.local

import android.content.Context
import uz.gita.contactappallayar.app.App

class SharedPref private constructor(){

    companion object {
        private val instance = SharedPref()
        private val pref = App.instance.getSharedPreferences("Contact", Context.MODE_PRIVATE)

        fun getSharedPref(): SharedPref {
            return instance
        }
    }

    var token: String
        get() = pref.getString("TOKEN", "")!!
        set(value) = pref.edit().putString("TOKEN", value).apply()
}