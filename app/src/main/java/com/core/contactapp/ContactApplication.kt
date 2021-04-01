package com.core.contactapp

import android.app.Application
import com.core.contactapp.data.ContactDatabase
import com.core.contactapp.data.ContactDbRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ContactApplication : Application() {

    companion object {
        lateinit var instance: ContactApplication
    }

    val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { ContactDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ContactDbRepository(database.contactDao()) }
}