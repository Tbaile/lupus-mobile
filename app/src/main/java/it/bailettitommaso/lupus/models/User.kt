package it.bailettitommaso.lupus.models

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("email") val email: String
)