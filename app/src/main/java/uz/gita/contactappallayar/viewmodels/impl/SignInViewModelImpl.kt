package uz.gita.contactappallayar.viewmodels.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.contactappallayar.data.local.SharedPref
import uz.gita.contactappallayar.data.remote.api.ContactsApi
import uz.gita.contactappallayar.data.remote.models.request.LoginRequest
import uz.gita.contactappallayar.data.remote.network.ApiClient
import uz.gita.contactappallayar.utils.isConnected
import uz.gita.contactappallayar.utils.myEnqueue
import uz.gita.contactappallayar.viewmodels.SignInViewModel

class SignInViewModelImpl : ViewModel(), SignInViewModel {
    override val progressLiveData = MutableLiveData<Boolean>()
    override val notConnectionLiveData = MutableLiveData<Unit>()
    override val openSignUpFragmentLiveData = MutableLiveData<Unit>()
    override val openContractFragmentLiveData = MutableLiveData<Unit>()
    override val errorLiveData = MutableLiveData<String>()

    private val sharedPref: SharedPref = SharedPref.getSharedPref()

    private val api: ContactsApi = ApiClient.getApi()

    override fun signInUser(data: LoginRequest) {
        if (!isConnected()) {
            notConnectionLiveData.value = Unit
            return
        }

        progressLiveData.value = true
        api.loginUser(data).myEnqueue(
            { response ->
                progressLiveData.value = false
                Log.d("TTT", response.body()!!.token)
                Log.d("TTT", response.body()!!.phone)
                if (response.isSuccessful) {
                    Log.d("TTT", "Login succes")
                    response.body()?.let {
                        sharedPref.token = it.token
                    }
                    openContractFragmentLiveData.value = Unit
                } else {
                    errorLiveData.value = "Error2"
                }
            },
            {
                progressLiveData.value = false
                errorLiveData.value = it.message
            })
    }

    override fun signUpUser() {
        openSignUpFragmentLiveData.value = Unit
    }
}