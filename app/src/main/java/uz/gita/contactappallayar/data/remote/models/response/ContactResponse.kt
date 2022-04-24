package uz.gita.contactappallayar.data.remote.models.response

import java.io.Serializable

data class ContactResponse(
    val id : Long,
    val firstName : String,
    val lastName : String,
    val phone : String
) : Serializable