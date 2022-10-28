package it.bailettitommaso.lupus.models.responses

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @field:SerializedName("token") val token: String
)
