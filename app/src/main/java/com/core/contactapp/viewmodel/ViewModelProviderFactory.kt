package com.core.contactapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.core.contactapp.ContactApplication

class ViewModelProviderFactory(
    private val application: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            return ContactViewModel(
                (application as ContactApplication).repository
            ) as T
        }

        if (modelClass.isAssignableFrom(AddContactViewModel::class.java)) {
            return AddContactViewModel(
                (application as ContactApplication).repository
            ) as T
        }
        throw IllegalArgumentException("Something went wrong")
    }
}