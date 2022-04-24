package uz.gita.contactappallayar.data.remote.models.response

data class UpdateResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phone: String
)