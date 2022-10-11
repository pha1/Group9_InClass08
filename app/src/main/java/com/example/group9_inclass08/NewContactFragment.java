/**
 * In Class 08
 * Group9_InClass08
 * Phi Ha
 * Srinath Dittakavi
 */
package com.example.group9_inclass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group9_inclass08.databinding.FragmentNewContactBinding;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewContactFragment extends Fragment {

    FragmentNewContactBinding binding;

    public NewContactFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    String name;
    String phone;
    String email;
    String phoneType = "";

    EditText editTextName;
    EditText editTextPhone;
    EditText editTextEmail;
    RadioGroup groupPhoneType;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getResources().getString(R.string.new_contact));

        editTextName = binding.editTextName;
        editTextPhone = binding.editTextPhone;
        editTextEmail = binding.editTextEmail;
        groupPhoneType = binding.groupPhoneType;

        // Patterns used to match entries for validation
        String name_pattern = "^[a-zA-z ]*$";
        String phone_pattern = "^\\d{3}-\\d{3}-\\d{4}$";
        String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        /**
         * RadioGroup to select what kind of Phone Type
         * Must be selected to create a Contact
         */
        groupPhoneType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (R.id.radioButtonHome == i) {
                    phoneType = "HOME";
                }
                else if (R.id.radioButtonCell == i) {
                    phoneType = "CELL";
                }
                else if (R.id.radioButtonOffice == i) {
                    phoneType = "OFFICE";
                }
            }
        });

        /**
         * When clicking on "Submit", the application will check if the entries are valid
         * if so, it will send the entries to the Main Activity
         */
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = editTextName.getText().toString();
                phone = editTextPhone.getText().toString();
                email = editTextEmail.getText().toString();

                if (name.equals("") || !name.matches(name_pattern)) {
                    Toast.makeText(getActivity(), "Please enter a valid name.", Toast.LENGTH_SHORT).show();
                } else if (email.equals("") || !email.matches(email_pattern)) {
                    Toast.makeText(getActivity(), "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                } else if (phone.equals("") || !phone.matches(phone_pattern)) {
                    Toast.makeText(getActivity(), "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else if (phoneType.equals("")) {
                    Toast.makeText(getActivity(), "Please enter a valid phone type", Toast.LENGTH_SHORT).show();
                } else {
                    newContactListener.createContact(name, email, phone, phoneType);
                }
            }
        });

        /**
         * Cancel Button
         */
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newContactListener.cancel();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NewContactListener) {
            newContactListener = (NewContactListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement NewContactListener.");
        }
    }

    NewContactListener newContactListener;

    public interface NewContactListener {
        void createContact(String name, String email, String phone, String phoneType);
        void cancel();
    }
}