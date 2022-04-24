package uz.gita.contactappallayar.data.remote.models.response

import java.io.Serializable

data class VerificationResponse(
    val phone: String,
    val token: String
) : Serializable
