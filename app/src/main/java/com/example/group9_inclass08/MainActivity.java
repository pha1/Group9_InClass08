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
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements ContactsListFragment.ContactsListListener, NewContactFragment.NewContactListener, ContactDetailsFragment.ContactDetailsListener, ContactRecyclerViewAdapter.IContactRecycler {

    private final OkHttpClient client = new OkHttpClient();

    ActivityMainBinding binding;
    final String TAG = "test";

    ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(getResources().getString(R.string.contacts_title));

        Log.d(TAG, "onCreate: Main Thread ID: " + Thread.currentThread().getId());

        getContacts();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, ContactsListFragment.newInstance(contacts), "Contacts List")
                .addToBackStack(null)
                .commit();

    }

    public void getContacts() {
        Log.d(TAG, "getContacts: Start");
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contacts/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + Thread.currentThread().getId());
                    /*
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

                            contacts.add(contact);
                            Log.d(TAG, "onResponse: " + contact.name);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                     */
                } else {
                    Log.d(TAG, "onResponse: " + response);
                }
            }
        });
        Log.d(TAG, "getContacts: Finish");
    }

    @Override
    public void newContact() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new NewContactFragment(), "New Contact")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    // TODO Check if this is needed?
    /**
     *
     * @param contact
     */
    @Override
    public void viewContact(Contact contact) {
    }

    @Override
    public void back() {
        getSupportFragmentManager().popBackStack();
    }
}