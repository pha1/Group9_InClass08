/**
 * In Class 08
 * Group9_InClass08
 * Phi Ha
 * Srinath Dittakavi
 */
package com.example.group9_inclass08;

import android.graphics.drawable.Icon;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder> {

    final static String TAG = "test";

    ArrayList<Contact> contacts;
    IContactRecycler iContactRecycler;

    public ContactRecyclerViewAdapter(ArrayList<Contact> data, IContactRecycler iContactRecycler) {
        this.contacts = data;
        this.iContactRecycler = iContactRecycler;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row_item,parent, false);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view, iContactRecycler);

        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        Contact contact = contacts.get(position);

        holder.textViewName.setText(contact.name);
        holder.textViewPhone.setText(contact.phone);

        holder.contacts = contacts;
        holder.contact = contact;
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }

    public interface IContactRecycler {
        void viewContact(Contact contact);
        void deleteContact(Contact contact);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewPhone;
        Button delete;

        View rootView;
        IContactRecycler iContactRecycler;

        int position;
        ArrayList<Contact> contacts;
        Contact contact;

        public ContactViewHolder(@NonNull View itemView, IContactRecycler iContactRecycler) {
            super(itemView);

            rootView = itemView;
            this.iContactRecycler = iContactRecycler;

            delete = itemView.findViewById(R.id.buttonDelete);
            textViewName = itemView.findViewById(R.id.textViewRowName);
            textViewPhone = itemView.findViewById(R.id.textViewRowPhone);

            // On click for each list item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: " + position);
                    Log.d(TAG, "onClick: " + contact.name);
                    // Interface
                    iContactRecycler.viewContact(contact);
                }
            });

            // On click for Delete Button in the Recycler View
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: " + position);
                    contact = contacts.get(position);
                    // Interface
                    iContactRecycler.deleteContact(contact);
                }
            });
        }
    }
}