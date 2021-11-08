package com.panditprogrammer.sqlcrud.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.panditprogrammer.sqlcrud.contactmodel.Contact;
import com.panditprogrammer.sqlcrud.databaseResources.DbResources;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    public DbHandler( Context context) {
        super(context, DbResources.DB_NAME, null, DbResources.DB_VERSION);
    }

    // create table in SQLite
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE "+ DbResources.TABLE_NAME + " ( " + DbResources.KEY_ID + " INTEGER PRIMARY KEY, " +
                DbResources.KEY_NAME + " TEXT," + DbResources.KEY_PHONE + " TEXT " + " )";
        //Log.i("mysqdb","this is query"+ create_table);
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    // adding contact  in database
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbResources.KEY_NAME,contact.getName());
        values.put(DbResources.KEY_PHONE,contact.getPhoneNumber());

        db.insert(DbResources.TABLE_NAME, null,values);
        db.close();
//        Log.i("dbInsert","Contact added successfully ");
    }
    // accessing all contacts
    public List<Contact> getAllContacts(){

        List <Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // select query for database
        String select_query = "SELECT * FROM "+ DbResources.TABLE_NAME;
        Cursor cursor = db.rawQuery(select_query, null);

        // accessing all contactList from databassssssssssssssssssss
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            }while (cursor.moveToNext());
        }
        // finally return the contactList
        return  contactList;
    }
    // update contact
    public int updateContact(Contact contact){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DbResources.KEY_NAME,contact.getName());
        values.put(DbResources.KEY_PHONE,contact.getPhoneNumber());
        // update query
        return database.update(DbResources.TABLE_NAME,values,DbResources.KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
    }

    // delete contact form database
    public void deleteContactById(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DbResources.TABLE_NAME,DbResources.KEY_ID + "=?", new String[]{String.valueOf(id)});
        database.close();
    }

    // delete contact by contact object
    public void deleteContact(Contact contact){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DbResources.TABLE_NAME,DbResources.KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
        database.close();
    }
    public int getRocordsCount(){
        String query = "SELECT * FROM "+ DbResources.TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        return cursor.getCount();
    }
}
