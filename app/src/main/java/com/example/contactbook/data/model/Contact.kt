package com.example.contactbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "contact_table")
data class Contact(
    val created_at: String,
    val email: String,
    val favorite: Boolean,
    val first_name: String,
    val last_name: String,
    val phone_number: String,
    val profile_pic: String,
    val updated_at: String
):Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}
