package com.example.contactbook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactbook.R
import com.example.contactbook.data.model.Contact


class ContactAdapter(context: Context, private val contactClickListener: ContactClickListener) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    private var contactList = emptyList<Contact>()
    private val v: LayoutInflater = LayoutInflater.from(context)

    interface ContactClickListener {
        fun onContactClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(v.inflate(R.layout.each_contact, parent, false))
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contactList[position]
        holder.name.text = contact.first_name

        holder.itemView.setOnClickListener {
            contactClickListener.onContactClick(
                contact.id
            )
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var name: TextView = v.findViewById(R.id.name)
    }

    internal fun setContacts(contacts: List<Contact>) {
        contactList = contacts
        notifyDataSetChanged()
    }
}
