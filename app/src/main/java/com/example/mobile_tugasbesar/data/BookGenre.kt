package com.example.mobile_tugasbesar.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "genres")
data class BookGenre(@PrimaryKey
    @SerializedName("id_genre") val id: String,

    @SerializedName("nama_genre") val name: String,

    @SerializedName("deskripsi_genre") val description: String? = null
)