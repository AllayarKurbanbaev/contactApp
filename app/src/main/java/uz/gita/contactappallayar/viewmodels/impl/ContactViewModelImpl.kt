package uz.gita.contactappallayar.viewmodels.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.contactappallayar.data.local.SharedPref
import uz.gita.contactappallayar.data.remote.api.ContactsApi
import uz.gita.contactappallayar.data.remote.models.request.ContactRequest
import uz.gita.contactappallayar.data.remote.models.response.ContactResponse
import uz.gita.contactappallayar.data.remote.network.ApiClient
import uz.gita.contactappallayar.utils.isConnected
import uz.gita.contactappallayar.utils.myEnqueue
import uz.gita.contactappallayar.viewmodels.ContactViewModel

class ContactViewModelImpl : ViewModel(), ContactViewModel {

    private val api: ContactsApi = ApiClient.getApi()
    private val sharedPref: SharedPref = SharedPref.getSharedPref()


    private val token = sharedPref.token


    override val contactListLiveData: MutableLiveData<List<ContactResponse>> by lazy { MutableLiveData<List<ContactResponse>>() }
    override val eventContactDialogLiveData: MutableLiveData<ContactResponse> by lazy { MutableLiveData<ContactResponse>() }
    override val addContactDialogLiveData: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    override val editContactDialogLiveData: MutableLiveData<ContactResponse> by lazy { MutableLiveData<ContactResponse>() }
    override val deleteContactDialogLiveData: MutableLiveData<ContactResponse> by lazy { MutableLiveData<ContactResponse>() }
    override val progressLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    override val errorLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    override val notConnectionLiveData: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }

    private val contactList: MutableList<ContactResponse> by lazy { ArrayList() }



    init {
        Log.d("TTT", "Token contact $token")
        get()
    }

    override fun addContactDialog() {
        addContactDialogLiveData.value = Unit
    }

    override fun eventContactDialog(data: ContactResponse) {
        eventContactDialogLiveData.value = data
    }

    override fun editContactDialog(data: ContactResponse) {
        editContactDialogLiveData.value = data
    }

    override fun deleteContactDialog(data: ContactResponse) {
        deleteContactDialogLiveData.value = data
    }

    override fun get() {
        if (!isConnected()) {
            notConnectionLiveData.value = Unit
            return
        }
        token.let { token ->

            progressLiveData.value = true
            api.getAllContacts(token).myEnqueue(
                { response ->
                    progressLiveData.value = false
                    if (response.isSuccessful) {
                        Log.d("TTT", "Token $token")
                        Log.d("TTT", "Contact succes")
                        response.body().let {
                            contactList.clear()
                            contactList.addAll(it!!)
                            contactListLiveData.value = contactList
                        }
                    } else errorLiveData.value = "Error"

                },
                { t: Throwable ->
                    progressLiveData.value = false
                    errorLiveData.value = t.message
                })
        }
    }

    override fun insert(data: ContactRequest) {
        if (!isConnected()) {
            notConnectionLiveData.value = Unit
            return
        }
        token.let { token ->
            progressLiveData.value = true
            api.postContact(token, data).myEnqueue(
                { response ->
                    progressLiveData.value = false
                    if (response.isSuccessful) {
                        response.body().let {
                            contactList.add(it!!)
                        }
                    } else errorLiveData.value = "Error"
                },
                { t: Throwable ->
                    progressLiveData.value = false
                    errorLiveData.value = t.message

                })

        }
    }

    override fun update(data: ContactResponse) {
        if (!isConnected()) {
            notConnectionLiveData.value = Unit
            return
        }
        token.let { token ->
            progressLiveData.value = true
            api.updateContact(token, data).myEnqueue(
                { response ->
                    progressLiveData.value = false
                    if (response.isSuccessful) {
                        response.body().let {
                            contactList.set(data.id.toInt(), data)
                            contactListLiveData.value = contactList
                        }
                    } else errorLiveData.value = "Error"
                },
                { t: Throwable ->
                    progressLiveData.value = false
                    errorLiveData.value = t.message
                })
        }
    }

    override fun delete(id: Long) {
        token.let { token ->
            progressLiveData.value = true
            api.deleteContact(token, id).myEnqueue(
                { response ->
                    progressLiveData.value = false
                    if (response.isSuccessful) {
                        response.body().let { deleteResponse ->
                            val i = contactList.indexOfFirst { it.id == deleteResponse?.id }
                            if (i > -1) {
                                contactList.removeAt(i)
                                contactListLiveData.value = contactList
                            } else errorLiveData.value = "Bunday contact yo'q"
                        }
                    }

                },
                { t: Throwable ->
                    progressLiveData.value = false
                    errorLiveData.value = t.message

                })

        }
    }

    override fun onRefresh() {
        contactListLiveData.value = contactList
    }
}