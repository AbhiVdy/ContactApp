package com.core.contactapp.data

import androidx.lifecycle.LiveData

class ContactDbRepository(private val contactDao: ContactDao) {

    fun getAllContacts(): LiveData<List<Contact>?> = contactDao.getAllContacts()

    suspend fun insertContact(contact: Contact) = contactDao.insertContact(contact)

    suspend fun deleteContact(contact: Contact?) = contactDao.deleteContact(contact)

    suspend fun updateContact(contact: Contact) = contactDao.updateContact(contact)

    fun getFavoriteContacts(): LiveData<List<Contact>> = contactDao.getFavoriteContacts()

    fun getContactById(id: Int): LiveData<Contact?> = contactDao.getContactById(id)
}