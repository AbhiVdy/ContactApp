package com.core.contactapp.ui

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.core.contactapp.R
import com.core.contactapp.data.Contact
import com.mikhaellopez.circularimageview.CircularImageView
import java.io.File


class ContactAdapter(
    private val contactList: ArrayList<Contact>?,
    private val clickListener: ContactClick
) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_contact, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        contactList?.let {
            holder.bind(it[position], clickListener)
        }
    }

    override fun getItemCount(): Int {
        contactList?.let {
            return it.size
        }
        return 0
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var cvContact: CardView = itemView.findViewById(R.id.cvContact);
        private var tvName: TextView = itemView.findViewById(R.id.tvName);
        private var contactImage: CircularImageView = itemView.findViewById(R.id.contactImage);
        private var ivCall: ImageView = itemView.findViewById(R.id.ivCall);

        fun bind(contact: Contact, clickListener: ContactClick) {
            tvName.text = contact.firstName + " " + contact.lastName

            /*contact.profilePictureUrl?.let {
                val uri = Uri.parse(it)
                contactImage.setImageURI(uri)
            }*/

            cvContact.setOnClickListener { clickListener.onContactClick(contact.id) }
            ivCall.setOnClickListener {
                contact.number?.let {
                    clickListener.onCallClicked(it)
                }
            }
        }
    }

    interface ContactClick {
        fun onContactClick(id: Int)
        fun onCallClicked(phone: String)
    }
}