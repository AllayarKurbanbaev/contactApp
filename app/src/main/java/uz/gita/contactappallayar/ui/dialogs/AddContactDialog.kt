package uz.gita.contactappallayar.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.contactappallayar.R
import uz.gita.contactappallayar.data.remote.models.request.ContactRequest
import uz.gita.contactappallayar.databinding.DialogAddContactRemoteBinding
import uz.gita.contactappallayar.utils.addListener
import uz.gita.contactappallayar.utils.myApply
import uz.gita.contactappallayar.utils.values

class AddContactDialog : DialogFragment(R.layout.dialog_add_contact_remote) {
    private val binding by viewBinding(DialogAddContactRemoteBinding::bind)
    private var addContactListener: ((ContactRequest) -> Unit)? = null

    private var isCorrectFirstname = false
    private var isCorrectLastname = false
    private var isCorrectPhoneNumber = false

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        buttonCancel.setOnClickListener { dismiss() }
        textInputFirstName.addListener {
            isCorrectFirstname = it.length >= 3
            check()
        }
        textInputLastName.addListener {
            isCorrectLastname = it.length >= 3
            check()
        }
        textInputNumber.addListener {
            isCorrectPhoneNumber = it.length == 13 && it.startsWith("+998")
            check()
        }
        buttonSave.setOnClickListener {
            addContactListener?.invoke(
                ContactRequest(
                    textInputFirstName.values(),
                    textInputLastName.values(),
                    textInputNumber.values()
                )
            )
            dismiss()
        }
    }

    private fun check() = with(binding) {
        buttonSave.isEnabled = isCorrectFirstname && isCorrectLastname && isCorrectPhoneNumber
    }

    fun setAddContactListener(block: (ContactRequest) -> Unit) {
        addContactListener = block
    }

}