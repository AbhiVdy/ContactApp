package com.core.contactapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.core.contactapp.data.Contact
import com.core.contactapp.data.ContactDbRepository

class ContactViewModel constructor(private val repository: ContactDbRepository) :
    ViewModel() {

    val getAllContacts = repository.getAllContacts()

    fun getFavoriteContacts(): LiveData<List<Contact>> {
        return repository.getFavoriteContacts()
    }
}