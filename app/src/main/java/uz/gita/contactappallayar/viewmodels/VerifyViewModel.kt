package uz.gita.contactappallayar.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.contactappallayar.data.remote.models.request.VerificationRequest

interface VerifyViewModel {

    val errorLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    val openContactFragmentLiveData: LiveData<Unit>
    val notConnectionLiveData: LiveData<Unit>

    fun verifyUser(data: VerificationRequest)

}