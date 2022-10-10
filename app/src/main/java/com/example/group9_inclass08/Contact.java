package com.example.group9_inclass08;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {

    int cid;
    String name;
    String email;
    String phone;
    String phoneType;

    /**
     * Default Constructor
     */
    public Contact(){}

    public Contact(int cid, String name, String email, String phone, String phoneType) {
        this.cid = cid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.phoneType = phoneType;
    }

    protected Contact(Parcel in) {
        cid = in.readInt();
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        phoneType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cid);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(phoneType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
