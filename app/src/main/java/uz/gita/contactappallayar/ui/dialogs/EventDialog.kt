package uz.gita.contactappallayar.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.contactappallayar.R
import uz.gita.contactappallayar.data.remote.models.response.ContactResponse
import uz.gita.contactappallayar.databinding.EventDialogBinding
import uz.gita.contactappallayar.utils.myApply

class EventDialog(private val data: ContactResponse) : BottomSheetDialogFragment() {
    private var onClickEditListener: (() -> Unit)? = null
    private var onClickDeleteListener: (() -> Unit)? = null
    private val binding by viewBinding(EventDialogBinding::bind)

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.event_dialog_remote, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        lineEdit.setOnClickListener {
            onClickEditListener?.invoke()
            dismiss()
        }
        lineDelete.setOnClickListener {
            onClickDeleteListener?.invoke()
            dismiss()
        }
    }

    fun setOnClickEditListener(block: () -> Unit) {
        onClickEditListener = block
    }

    fun setOnClickDeleteListener(block: () -> Unit) {
        onClickDeleteListener = block
    }
}