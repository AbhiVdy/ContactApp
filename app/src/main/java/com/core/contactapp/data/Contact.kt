package com.core.contactapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    var firstName: String?,
    var lastName: String?,
    var number: String?,
    var profilePictureUrl: String?,
    var isFavorite: Boolean?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
