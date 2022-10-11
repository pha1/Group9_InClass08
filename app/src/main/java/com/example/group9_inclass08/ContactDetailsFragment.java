package com.example.group9_inclass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.group9_inclass08.databinding.FragmentContactDetailsBinding;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactDetailsFragment extends Fragment {

    FragmentContactDetailsBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_CONTACT = "contact";

    // TODO: Rename and change types of parameters
    private Contact contact;

    public ContactDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param contact Parameter 1.
     * @return A new instance of fragment ContactDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactDetailsFragment newInstance(Contact contact) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_CONTACT, contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contact = getArguments().getParcelable(ARG_PARAM_CONTACT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView detailsName = binding.textViewName;
        TextView detailsPhone = binding.textViewPhone;
        TextView detailsEmail = binding.textViewEmail;
        TextView detailsPhoneType = binding.textViewPhoneType;
        TextView detailsCid = binding.textViewCid;

        detailsName.setText(contact.name);
        detailsPhone.setText(contact.phone);
        detailsCid.setText(String.valueOf(contact.cid));
        detailsEmail.setText(contact.email);
        detailsPhoneType.setText(contact.phoneType);

        binding.buttonDeleteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactDetailsListener.deleteDetails(contact);
            }
        });

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactDetailsListener.back();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ContactDetailsListener) {
            contactDetailsListener = (ContactDetailsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ContactDetailsListener.");
        }
    }

    ContactDetailsListener contactDetailsListener;

    public interface ContactDetailsListener {
        void back();
        void deleteDetails(Contact contact);
    }
}