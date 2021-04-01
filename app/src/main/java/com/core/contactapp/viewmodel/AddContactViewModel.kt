package com.core.contactapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.contactapp.data.Contact
import com.core.contactapp.data.ContactDbRepository
import kotlinx.coroutines.launch

class AddContactViewModel constructor(private val repository: ContactDbRepository) :
    ViewModel() {

    var firstName = ""
    var lastName = ""
    var phoneNumber = ""
    var isFavorite: Boolean = false

    fun insertContact(contact: Contact) {
        viewModelScope.launch {
            repository.insertContact(contact)
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            repository.updateContact(contact)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }

    fun getContactById(contactId: Int) = repository.getContactById(contactId)
}