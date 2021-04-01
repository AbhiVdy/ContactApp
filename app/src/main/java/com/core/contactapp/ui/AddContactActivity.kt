package com.core.contactapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.core.contactapp.R
import com.core.contactapp.data.Contact
import com.core.contactapp.databinding.ActivityAddContactBinding
import com.core.contactapp.viewmodel.AddContactViewModel
import com.core.contactapp.viewmodel.ViewModelProviderFactory

class AddContactActivity : AppCompatActivity() {

    private val REQUEST_IMAGE = 100
    private lateinit var binding: ActivityAddContactBinding
    private lateinit var viewModel: AddContactViewModel
    var profilePicturePath: String? = null
    private var id = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_contact)
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(application)
        ).get(AddContactViewModel::class.java)

        if (intent.hasExtra("id"))
            id = intent.getIntExtra("id", -1)

        viewModel.getContactById(id).observe(this, Observer {
            binding.contact = it
            binding.contact?.isFavorite?.let { isFav ->
                if (isFav) {
                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite_selected)
                    viewModel.isFavorite = true
                } else {
                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite_unselected)
                    viewModel.isFavorite = false
                }
            }
        })

        init()
    }

    private fun init() {
        binding.uploadProfilePictureImageView.setOnClickListener {
            selectProfilePicture()
        }

        if (id == -1) {
            binding.ivDelete.visibility = View.GONE
        } else {
            binding.ivDelete.visibility = View.VISIBLE
        }

        binding.btnSave.setOnClickListener {
            if (checkValidation()) {
                if (id == -1) {
                    viewModel.insertContact(
                        Contact(
                            viewModel.firstName,
                            viewModel.lastName,
                            viewModel.phoneNumber,
                            profilePicturePath,
                            viewModel.isFavorite
                        )
                    )
                } else {
                    viewModel.updateContact(
                        Contact(
                            viewModel.firstName,
                            viewModel.lastName,
                            viewModel.phoneNumber,
                            profilePicturePath,
                            viewModel.isFavorite,
                            id
                        )
                    )
                }

                onBackPressed()
            }
        }

        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }

        binding.ivDelete.setOnClickListener {
            binding.contact?.let { obj ->
                viewModel.deleteContact(obj)
                onBackPressed()
            }
        }

        binding.edFirstName.doAfterTextChanged {
            viewModel.firstName = it.toString()
        }

        binding.edLastName.doAfterTextChanged {
            viewModel.lastName = it.toString()
        }

        binding.edPhoneNo.doAfterTextChanged {
            viewModel.phoneNumber = it.toString()
        }

        binding.ivFavorite.setOnClickListener {
            binding.contact?.isFavorite?.let { bool ->
                viewModel.isFavorite = !bool
                if (viewModel.isFavorite) {
                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite_selected)
                    viewModel.isFavorite = true
                } else {
                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite_unselected)
                    viewModel.isFavorite = false
                }
            }
        }
    }

    private fun checkValidation(): Boolean {
        return when {
            binding.edFirstName.text.toString() == "" -> {
                binding.edFirstName.error = getString(R.string.ed_validation)
                binding.edFirstName.requestFocus()
                false
            }
            binding.edLastName.text.toString() == "" -> {
                binding.edLastName.error = getString(R.string.ed_validation)
                binding.edLastName.requestFocus()
                false
            }
            binding.edPhoneNo.toString() == "" -> {
                binding.edPhoneNo.error = getString(R.string.ed_validation)
                binding.edPhoneNo.requestFocus()
                false
            }
            else -> {
                true
            }
        }
    }

    private fun selectProfilePicture() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data

            profilePicturePath = imageUri.toString()
            binding.uploadProfilePictureImageView.setImageURI(data?.data)
        }
    }
}