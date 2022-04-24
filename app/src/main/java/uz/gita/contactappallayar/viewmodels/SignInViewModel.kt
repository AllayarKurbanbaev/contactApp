package uz.gita.contactappallayar.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.contactappallayar.data.remote.models.request.LoginRequest

interface SignInViewModel {

    val progressLiveData: LiveData<Boolean>
    val notConnectionLiveData: LiveData<Unit>
    val openSignUpFragmentLiveData: LiveData<Unit>
    val openContractFragmentLiveData: LiveData<Unit>
    val errorLiveData: LiveData<String>


    fun signInUser(data: LoginRequest)
    fun signUpUser()
}