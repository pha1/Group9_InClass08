package com.example.group9_inclass08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.util.Log;
import android.view.textclassifier.TextLinks;

import com.example.group9_inclass08.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements ContactsListFragment.ContactsListListener, NewContactFragment.NewContactListener, ContactDetailsFragment.ContactDetailsListener, ContactRecyclerViewAdapter.IContactRecycler {

    private final OkHttpClient client = new OkHttpClient();
    ActivityMainBinding binding;
    final String TAG = "test";

    public static ArrayList<Contact> contacts = new ArrayList<>();
    ContactsListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(getResources().getString(R.string.contacts_title));

        getContacts();

    }

    public void getContacts() {
        
        Log.d(TAG, "getContacts: Start");

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contacts/json")
                .build();

        Log.d(TAG, "getContacts: " + request);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    contacts = new ArrayList<>();
                    Log.d(TAG, "onResponse: " + Thread.currentThread().getId());
                    try {
                        JSONObject json = new JSONObject((response.body().string()));
                        JSONArray contactsJson = json.getJSONArray("contacts");

                        for (int i = 0; i < contactsJson.length(); i++) {
                            JSONObject contactJsonObject = contactsJson.getJSONObject(i);

                            Contact contact = new Contact();
                            contact.cid = contactJsonObject.getInt("Cid");
                            contact.name = contactJsonObject.getString("Name");
                            contact.email = contactJsonObject.getString("Email");
                            contact.phone = contactJsonObject.getString("Phone");
                            contact.phoneType = contactJsonObject.getString("PhoneType");
                            Log.d(TAG, "onResponse: " + contact.name);
                            contacts.add(contact);
                        }

                        Log.d(TAG, "onResponse: Contacts: " + contacts.size());
                        showContacts(contacts);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "onResponse: " + response);
                }

            }
        });
        Log.d(TAG, "getContacts: Finish");
    }

    public void showContacts(ArrayList<Contact> contacts) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ContactsListFragment.newInstance(contacts), "Contacts List")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void newContact() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new NewContactFragment(), "New Contact")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void createContact(String name, String email, String phone, String phoneType) {

        FormBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("email", email)
                .add("phone", phone)
                .add("type", phoneType)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/json/create")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                getContacts();
            }
        });
    }

    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void viewContact(Contact contact) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ContactDetailsFragment.newInstance(contact), "Details")
                .addToBackStack(null)
                .commit();
    }

    /**
     *
     * @param contact
     */
    @Override
    public void deleteContact(Contact contact) {

        FormBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(contact.cid))
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/json/delete")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                getContacts();
            }
        });
    }

    @Override
    public void back() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void deleteDetails(Contact contact) {
        FormBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(contact.cid))
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/json/delete")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                getContacts();
            }
        });
    }
}