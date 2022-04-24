package uz.gita.contactappallayar.data.remote.models.request

import java.io.Serializable

data class ContactRequest(
    val firstName: String,
    val lastName: String,
    val phone: String
) : Serializable
