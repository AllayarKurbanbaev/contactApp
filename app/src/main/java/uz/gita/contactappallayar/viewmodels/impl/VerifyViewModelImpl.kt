package uz.gita.contactappallayar.viewmodels.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.contactappallayar.data.local.SharedPref
import uz.gita.contactappallayar.data.remote.api.ContactsApi
import uz.gita.contactappallayar.data.remote.models.request.VerificationRequest
import uz.gita.contactappallayar.data.remote.network.ApiClient
import uz.gita.contactappallayar.utils.isConnected
import uz.gita.contactappallayar.utils.myEnqueue
import uz.gita.contactappallayar.viewmodels.VerifyViewModel

class VerifyViewModelImpl : ViewModel(), VerifyViewModel {
    override val errorLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()
    override val openContactFragmentLiveData = MutableLiveData<Unit>()
    override val notConnectionLiveData = MutableLiveData<Unit>()

    private val api: ContactsApi = ApiClient.getApi()
    private val sharedPref: SharedPref = SharedPref.getSharedPref()

    override fun verifyUser(data: VerificationRequest) {
        if (!isConnected()) {
            notConnectionLiveData.value = Unit
            return
        }

        progressLiveData.value = true
        api.verifyUser(data).myEnqueue(
            { response ->
                progressLiveData.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("TTT", "Verify succes")
                        Log.d("TTT", "Token verify ${it.token}")
                        sharedPref.token = it.token
                        openContactFragmentLiveData.value = Unit
                    }
                } else {
                    Log.d("TTT", "Verify error")
                    errorLiveData.value = "Error"
                }
            },
            {
                progressLiveData.value = false
                errorLiveData.value = it.message
            })
    }
}