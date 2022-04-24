package uz.gita.contactappallayar.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.contactappallayar.R
import uz.gita.contactappallayar.data.remote.models.response.ContactResponse
import uz.gita.contactappallayar.databinding.FragmentContactBinding
import uz.gita.contactappallayar.ui.adapters.ContactAdapter
import uz.gita.contactappallayar.ui.dialogs.AddContactDialog
import uz.gita.contactappallayar.ui.dialogs.DeleteContactDialog
import uz.gita.contactappallayar.ui.dialogs.EditContactDialog
import uz.gita.contactappallayar.ui.dialogs.EventDialog
import uz.gita.contactappallayar.utils.myApply
import uz.gita.contactappallayar.utils.showToast
import uz.gita.contactappallayar.viewmodels.ContactViewModel
import uz.gita.contactappallayar.viewmodels.impl.ContactViewModelImpl

class ContactsFragment : Fragment(R.layout.fragment_contact) {
    private val viewModel: ContactViewModel by viewModels<ContactViewModelImpl>()
    private val binding by viewBinding(FragmentContactBinding::bind)
    private val adapter: ContactAdapter = ContactAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        buttonAdd.setOnClickListener {
            viewModel.addContactDialog()
        }

        adapter.setOnClickOptionButton {
            viewModel.eventContactDialog(it)
        }

        buttonRefresh.setOnClickListener {
            viewModel.onRefresh()
        }

        with(viewModel) {
            contactListLiveData.observe(viewLifecycleOwner, contactListObserver)
            addContactDialogLiveData.observe(viewLifecycleOwner, addContactDialogObserver)
            eventContactDialogLiveData.observe(viewLifecycleOwner, eventContactDialogObserver)
            deleteContactDialogLiveData.observe(viewLifecycleOwner, deleteContactDialogObserver)
            editContactDialogLiveData.observe(viewLifecycleOwner, editContactDialogObserver)
            progressLiveData.observe(viewLifecycleOwner, progressObserver)
            errorLiveData.observe(viewLifecycleOwner, errorObserver)
            notConnectionLiveData.observe(viewLifecycleOwner, notConnectionObserver)
        }
    }

    private val contactListObserver = Observer<List<ContactResponse>> {
        adapter.submitList(it)
    }
    private val addContactDialogObserver = Observer<Unit> {
        val dialog = AddContactDialog()
        dialog.setAddContactListener { request ->
            viewModel.insert(request)
        }
        dialog.show(childFragmentManager, "AddContactDialog")
    }

    private val eventContactDialogObserver = Observer<ContactResponse> {
        val dialog = EventDialog(it)
        dialog.setOnClickEditListener { viewModel.deleteContactDialog(it) }
        dialog.setOnClickEditListener { viewModel.editContactDialog(it) }
        dialog.show(childFragmentManager, "EventDialog")
    }
    private val deleteContactDialogObserver = Observer<ContactResponse> {
        val dialog = DeleteContactDialog()
        dialog.setDeleteContactListener {
            viewModel.delete(it.id)
        }
        dialog.show(childFragmentManager, "DeleteContactDialog")
    }
    private val editContactDialogObserver = Observer<ContactResponse> {
        val dialog = EditContactDialog(it)
        dialog.setEditContactListener {
            viewModel.editContactDialog(it)
        }
        dialog.show(childFragmentManager, "EditContactDialog")
    }
    private val progressObserver = Observer<Boolean> {
        when (it) {
            true -> binding.progress.show()
            else -> binding.progress.hide()
        }
    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }

    private val notConnectionObserver = Observer<Unit> {
        showToast("Not internet connection")
    }

}