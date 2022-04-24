package uz.gita.contactappallayar.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import uz.gita.contactappallayar.data.local.SharedPref


class App : Application(){
    companion object {
        lateinit var instance : App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}