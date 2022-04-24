package uz.gita.contactappallayar.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.contactappallayar.data.remote.models.request.ContactRequest
import uz.gita.contactappallayar.data.remote.models.request.UpdateRequest
import uz.gita.contactappallayar.data.remote.models.response.ContactResponse

interface ContactViewModel {

    val contactListLiveData: LiveData<List<ContactResponse>>
    val eventContactDialogLiveData: LiveData<ContactResponse>
    val addContactDialogLiveData: LiveData<Unit>
    val editContactDialogLiveData: LiveData<ContactResponse>
    val deleteContactDialogLiveData: LiveData<ContactResponse>
    val progressLiveData: LiveData<Boolean>
    val errorLiveData: LiveData<String>
    val notConnectionLiveData : LiveData<Unit>

    fun addContactDialog()
    fun eventContactDialog(data: ContactResponse)
    fun editContactDialog(data: ContactResponse)
    fun deleteContactDialog(data: ContactResponse)

    fun get()
    fun insert(data: ContactRequest)
    fun update(data: ContactResponse)
    fun delete(id: Long)
    fun onRefresh()
}