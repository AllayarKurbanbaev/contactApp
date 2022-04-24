package uz.gita.contactappallayar.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.contactappallayar.R
import uz.gita.contactappallayar.databinding.ItemContactBinding
import uz.gita.contactappallayar.data.remote.models.response.ContactResponse
import uz.gita.contactappallayar.ui.adapters.ContactAdapter.ViewHolder

class ContactAdapter : ListAdapter<ContactResponse, ViewHolder>(ContactDiffUtil) {

     var onClickOptionButton: ((ContactResponse) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.buttonMore.setOnClickListener {
                onClickOptionButton?.invoke(getItem(adapterPosition))
            }
        }

        fun populateModel(model: ContactResponse) {
            binding.apply {
                textName.text = model.firstName
                textNumber.text = model.phone
            }
        }
    }

    private object ContactDiffUtil : DiffUtil.ItemCallback<ContactResponse>() {
        override fun areItemsTheSame(oldItem: ContactResponse, newItem: ContactResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContactResponse, newItem: ContactResponse): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(ItemContactBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(getItem(position))
    }

    @JvmName("setOnClickOptionButton1")
    fun setOnClickOptionButton(data: (ContactResponse) -> Unit) {
        onClickOptionButton = data
    }
}