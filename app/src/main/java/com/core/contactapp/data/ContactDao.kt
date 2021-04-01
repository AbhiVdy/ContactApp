package com.core.contactapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact ORDER BY firstName ASC")
    fun getAllContacts(): LiveData<List<Contact>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact?)

    @Update
    suspend fun updateContact(contact: Contact)

    @Query("SELECT * from contact WHERE isFavorite = :isFav")
    fun getFavoriteContacts(isFav: Boolean = true): LiveData<List<Contact>>

    @Query("SELECT * FROM contact WHERE id = :contactId")
    fun getContactById(contactId: Int): LiveData<Contact?>
}