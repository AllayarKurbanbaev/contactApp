package uz.gita.contactappallayar.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.contactappallayar.R
import uz.gita.contactappallayar.databinding.DialogDeleteContactBinding
import uz.gita.contactappallayar.utils.myApply

class DeleteContactDialog : DialogFragment(R.layout.dialog_delete_contact) {

    private val binding by viewBinding(DialogDeleteContactBinding::bind)
    private var deleteContactListener: (() -> Unit)? = null

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        buttonCancel.setOnClickListener { dismiss() }
        buttonOk.setOnClickListener {
            deleteContactListener?.invoke()
            dismiss()
        }
    }

    fun setDeleteContactListener(block: () -> Unit) {
        deleteContactListener = block
    }
}