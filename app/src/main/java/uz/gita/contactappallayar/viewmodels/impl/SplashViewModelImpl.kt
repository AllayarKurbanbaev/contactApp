package uz.gita.contactappallayar.viewmodels.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.contactappallayar.viewmodels.SplashViewModel
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SplashViewModelImpl : ViewModel(), SplashViewModel {
    override val openSignInFragmentLiveData = MutableLiveData<Unit>()

    init {
        val thread = Executors.newSingleThreadExecutor()
        thread.execute {
            thread.awaitTermination(2, TimeUnit.SECONDS)
            openSignInFragmentLiveData.postValue(Unit)
            thread.shutdown()
        }
    }

}