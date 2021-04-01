package com.core.contactapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.core.contactapp.R
import com.core.contactapp.data.Contact
import com.core.contactapp.databinding.ActivityMainBinding
import com.core.contactapp.viewmodel.ContactViewModel
import com.core.contactapp.viewmodel.ViewModelProviderFactory


class MainActivity : AppCompatActivity(), ContactAdapter.ContactClick {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var constactAdapter: ContactAdapter
    private var contactList: ArrayList<Contact>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        contactViewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(application)
        ).get(ContactViewModel::class.java)

        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                contactViewModel.getFavoriteContacts().observe(this, Observer {
                    it?.let {
                        contactList?.clear()
                        contactList?.addAll(it)
                        constactAdapter.notifyDataSetChanged()
                    }
                })
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private fun init() {
        contactViewModel.getAllContacts.observe(this, Observer {
            it?.let {
                if (it.isEmpty()) {
                    binding.rvContacts.visibility = View.GONE
                    binding.tvEmpty.visibility = View.VISIBLE
                } else {
                    binding.rvContacts.visibility = View.VISIBLE
                    binding.tvEmpty.visibility = View.GONE
                    contactList?.clear()
                    contactList = it as ArrayList<Contact>
                    constactAdapter = ContactAdapter(contactList, this)
                    binding.rvContacts.adapter = constactAdapter
                }
            }
        })

        binding.fbAddContact.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onContactClick(id: Int) {
        val intent = Intent(this, AddContactActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    override fun onCallClicked(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone))
        startActivity(intent)
    }
}