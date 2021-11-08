package com.panditprogrammer.sqlcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.panditprogrammer.sqlcrud.contactmodel.Contact;
import com.panditprogrammer.sqlcrud.database.DbHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listViewForContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // create database handler class object
        DbHandler dbHandler = new DbHandler(this);

        ArrayList<String> contactList = new ArrayList<>();
        listViewForContact = findViewById(R.id.contactListId);

        Log.i("total_records","Total Available records  are " + dbHandler.getRocordsCount());



        List<Contact> allContacts = dbHandler.getAllContacts();
        for(Contact contact : allContacts){
            Log.i("allContact","id "+ contact.getId() + "\nname "+contact.getName() + "\nPhone "+ contact.getPhoneNumber());
            contactList.add(contact.getName() + "  " + contact.getPhoneNumber());
        }

        // adapter for list view
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,contactList);
        // show in listView
        listViewForContact.setAdapter(arrayAdapter);
    }

}