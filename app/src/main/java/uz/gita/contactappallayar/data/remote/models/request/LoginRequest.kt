package uz.gita.contactappallayar.data.remote.models.request

import java.io.Serializable

data class LoginRequest(
    val phone: String,
    val password: String
) : Serializable
