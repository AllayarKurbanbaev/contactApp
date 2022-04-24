package uz.gita.contactappallayar.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.contactappallayar.data.remote.models.request.RegisterRequest

interface SignUpViewModel {

    val progressLiveData: LiveData<Boolean>
    val openVerifyFragmentLiveData: LiveData<Unit>
    val errorLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<Unit>

    fun singUp(data: RegisterRequest)
}