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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group9_inclass08.databinding.FragmentContactsListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsListFragment extends Fragment{

    FragmentContactsListBinding binding;
    final String TAG = "test";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_CONTACTS = "contacts";

    private ArrayList<Contact> contacts = new ArrayList<>();

    public ContactsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param contacts Parameter 1.
     * @return A new instance of fragment ContactsListFragment.
     */
    public static ContactsListFragment newInstance(ArrayList<Contact> contacts) {
        ContactsListFragment fragment = new ContactsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM_CONTACTS, contacts);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contacts = getArguments().getParcelableArrayList(ARG_PARAM_CONTACTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentContactsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    RecyclerView recyclerViewContactsList;
    LinearLayoutManager layoutManager;
    ContactRecyclerViewAdapter adapter;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // RecyclerView
        recyclerViewContactsList = binding.recyclerViewContacts;
        recyclerViewContactsList.setHasFixedSize(true);

        // LinearLayoutManager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewContactsList.setLayoutManager(layoutManager);

        // Custom Adapter
        adapter = new ContactRecyclerViewAdapter(contacts, (ContactRecyclerViewAdapter.IContactRecycler) getActivity());

        // RecyclerView - line dividers
        recyclerViewContactsList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewContactsList.setAdapter(adapter);

        // Add Contact Button
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactsListListener.newContact();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // If the back button is used, the title is set back to "Contacts"
        getActivity().setTitle(getResources().getString(R.string.contacts_title));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ContactsListListener) {
            contactsListListener = (ContactsListListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ContactsListListener.");
        }
    }

    ContactsListListener contactsListListener;

    public interface ContactsListListener {
        void newContact();
    }

    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    public void updateArrayList(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

}