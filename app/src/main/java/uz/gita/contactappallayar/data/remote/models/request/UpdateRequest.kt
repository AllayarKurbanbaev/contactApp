package uz.gita.contactappallayar.data.remote.models.request

data class UpdateRequest(
    val firstName: String,
    val id: Long,
    val lastName: String,
    val phone: String
)