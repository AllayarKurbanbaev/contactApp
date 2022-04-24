package uz.gita.contactappallayar.data.remote.api

import retrofit2.Call
import retrofit2.http.*
import uz.gita.contactappallayar.data.remote.models.request.*
import uz.gita.contactappallayar.data.remote.models.response.*

interface ContactsApi {
    @GET("/api/v1/contact")
    fun getAllContacts(@Header("token") token: String): Call<List<ContactResponse>>

    @DELETE("/api/v1/contact")
    fun deleteContact(@Header("token") token: String, @Query("id") id: Long): Call<DeleteResponse>

    @PUT("/api/v1/contact")
    fun updateContact(
        @Header("token") token: String,
        @Body contactResponse: ContactResponse
    ): Call<ContactResponse>

    @POST("/api/v1/contact")
    fun postContact(
        @Header("token") token: String,
        @Body contactRequest: ContactRequest
    ): Call<ContactResponse>


    @POST("/api/v1/register")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("/api/v1/register/verify")
    fun verifyUser(@Body verificationRequest: VerificationRequest): Call<VerificationResponse>

    @POST("/api/v1/contact")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

}