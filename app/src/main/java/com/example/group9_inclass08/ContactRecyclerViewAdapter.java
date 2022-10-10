package com.example.group9_inclass08;

import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.textViewCid.setText(contact.cid);
    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }

    public interface IContactRecycler {
        void viewContact(Contact contact);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewPhone;
        TextView textViewCid;

        View rootView;
        IContactRecycler iContactRecycler;

        int position;
        ArrayList<Contact> contacts;
        Contact contact;

        public ContactViewHolder(@NonNull View itemView, IContactRecycler iContactRecycler) {
            super(itemView);

            rootView = itemView;
            this.iContactRecycler = iContactRecycler;

            textViewName = itemView.findViewById(R.id.textViewRowName);
            textViewPhone = itemView.findViewById(R.id.textViewRowPhone);
            textViewCid = itemView.findViewById(R.id.textViewRowCid);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contact = contacts.get(position);
                    iContactRecycler.viewContact(contact);
                }
            });
        }
    }
}