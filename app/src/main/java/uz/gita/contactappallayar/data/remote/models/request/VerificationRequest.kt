package uz.gita.contactappallayar.data.remote.models.request

import java.io.Serializable

data class VerificationRequest(
    val code: String,
    val phone: String
) : Serializable
