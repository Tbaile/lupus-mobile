package it.bailettitommaso.lupus.models

import com.google.gson.annotations.SerializedName

data class ErrorMessage(
    @field:SerializedName("message") val message: String
)
