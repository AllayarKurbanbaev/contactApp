package uz.gita.contactappallayar.data.remote.models.request

import java.io.Serializable

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val password: String,
    val phone: String
) : Serializable
