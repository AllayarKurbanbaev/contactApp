package uz.gita.contactappallayar.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.contactappallayar.R
import uz.gita.contactappallayar.data.remote.models.response.ContactResponse
import uz.gita.contactappallayar.databinding.DialogEditContactRemoteBinding
import uz.gita.contactappallayar.utils.addListener
import uz.gita.contactappallayar.utils.myApply
import uz.gita.contactappallayar.utils.values

class EditContactDialog(private val response: ContactResponse) :
    DialogFragment(R.layout.dialog_edit_contact_remote) {
    private val binding by viewBinding(DialogEditContactRemoteBinding::bind)
    private var editContactListener: ((ContactResponse) -> Unit)? = null
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
            isCorrectFirstname = it.length >= 3 && it[0].isUpperCase()
            check()
        }
        textInputLastName.addListener {
            isCorrectLastname = it.length >= 3 && it[0].isUpperCase()
            check()
        }
        textInputNumber.addListener {
            isCorrectPhoneNumber = it.length == 13 && it.startsWith("+998")
            check()
        }


        textInputFirstName.setText(response.firstName)
        textInputLastName.setText(response.lastName)
        textInputNumber.setText(response.phone)

        buttonSave.setOnClickListener {
            editContactListener?.invoke(
                ContactResponse(
                    id = response.id,
                    firstName = textInputFirstName.values(),
                    lastName = textInputLastName.values(),
                    phone = textInputNumber.values()
                )
            )
            dismiss()
        }
    }


    private fun check() = with(binding) {
        buttonSave.isEnabled = isCorrectFirstname && isCorrectLastname && isCorrectPhoneNumber
    }

    fun setEditContactListener(block: (ContactResponse) -> Unit) {
        editContactListener = block
    }
}