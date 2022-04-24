package uz.gita.contactappallayar.data.remote.network

import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.contactappallayar.BuildConfig.BASE_URL
import uz.gita.contactappallayar.app.App
import uz.gita.contactappallayar.data.remote.api.ContactsApi

object ApiClient {


    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(ChuckInterceptor(App.instance)).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun getApi(): ContactsApi = retrofit.create(ContactsApi::class.java)
}