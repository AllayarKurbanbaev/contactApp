package uz.gita.contactappallayar.viewmodels.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.contactappallayar.data.remote.api.ContactsApi
import uz.gita.contactappallayar.data.remote.models.request.RegisterRequest
import uz.gita.contactappallayar.data.remote.network.ApiClient
import uz.gita.contactappallayar.utils.isConnected
import uz.gita.contactappallayar.utils.myEnqueue
import uz.gita.contactappallayar.viewmodels.SignUpViewModel

class SignUpViewModelImpl : ViewModel(), SignUpViewModel {

    private val api: ContactsApi = ApiClient.getApi()

    override val progressLiveData = MutableLiveData<Boolean>()
    override val openVerifyFragmentLiveData = MutableLiveData<Unit>()
    override val errorLiveData = MutableLiveData<String>()
    override val notConnectionLiveData = MutableLiveData<Unit>()

    override fun singUp(data: RegisterRequest) {
        if (!isConnected()) {
            notConnectionLiveData.value = Unit
            return
        }
        progressLiveData.value = true
        api.registerUser(data).myEnqueue(
            { response ->
                progressLiveData.value = false
                if (response.isSuccessful) {
                    openVerifyFragmentLiveData.value = Unit
                } else {
                    errorLiveData.value = "Error"
                }
            },
            {
                progressLiveData.value = false
                errorLiveData.value = it.message
            })

    }
}