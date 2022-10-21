package it.bailettitommaso.lupus.models.requests

import com.google.gson.annotations.SerializedName

data class PostLogin(
    @field:SerializedName("email") val email: String,
    @field:SerializedName("password") val password: String
)