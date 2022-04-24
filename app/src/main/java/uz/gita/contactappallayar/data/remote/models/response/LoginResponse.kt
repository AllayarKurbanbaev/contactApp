package uz.gita.contactappallayar.data.remote.models.response

import java.io.Serializable

data class LoginResponse(
    val token: String,
    val phone: String
) : Serializable
