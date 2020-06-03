package com.hackeru.watsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int READ_CONTACT_REQUEST = 340;

    RecyclerView contactList;
    ContactAdapter adapter = new ContactAdapter();
    Button getContactsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = findViewById(R.id.list);
        contactList.setAdapter(adapter);
        getContactsBtn = findViewById(R.id.button);

        getContactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
//                    adapter.setList(getContacts());
//                } else {
//                    //ask for permission
//                    requestReadContactsPermission();
//                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case READ_CONTACT_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    adapter.setList(getContacts());
                } else {
                    //request permission again
                    requestReadContactsPermission();
                }
                break;
            default:
                break;
        }

    }

    private void requestReadContactsPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACT_REQUEST);
    }

    private ArrayList<Contact> getContacts() {
        Toast.makeText(this, "getContacts", Toast.LENGTH_SHORT).show();
        ArrayList<Contact> list = new ArrayList<>();
        //we will find the contacts here
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if(!cur.moveToFirst()) return list;
        while (cur.moveToNext()) {
            int namePosition = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String name = cur.getString(namePosition);

            int hasPhonePosition = cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            boolean hasPhone = cur.getInt(hasPhonePosition) > 0;
            Contact contact = new Contact(name, "no phone number");
//            if(hasPhone){
//                int phonePosition = cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//                if (phonePosition > 0) {
//                    String phone = cur.getString(phonePosition);
//                    contact.setNumber(phone);
//                }
//            }
            list.add(contact);
        }

        return list;
    }
}
