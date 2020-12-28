package com.example.librarysystem.eatit;
/**
 * UserAdapter.java
 * This class is manages the users in the DB
 * @author: Darwin Machado
 *
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private ConnectionUser connectionUser;
    private SQLiteDatabase db;

    //establish connection with database
    public UserDAO(Context context){
        connectionUser = new ConnectionUser(context);
        db = connectionUser.getWritableDatabase();
    }

    // insert values into DB
    public long insert(User user){
        ContentValues values = new ContentValues();
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());
        values.put("phone", user.getPhone());
        values.put("email", user.getEmail());
        return db.insert("user", null, values);
    }

    //List all books in DB
    public List<User> selectAll(){
        List<User>  users = new ArrayList<>();
        Cursor cursor = db.query("user",new String[]{"id","firstName","lastName", "phone", "email"},
                null,null,null,null ,null);
        while(cursor.moveToNext()){
            User u = new User();
            u.setId(cursor.getInt(0));
            u.setFirstName(cursor.getString(1)); ;
            u.setLastName(cursor.getString(2));
            u.setPhone(cursor.getString(3));
            u.setEmail(cursor.getString(4));
            users.add(u);
        }
        return users;
    }

    //Remove an user from Database
    public void removeBook(User u){
        db.delete("user", "id=?", new String[]{u.getId().toString()});
    }

    //Update a book if already exists in the DB
    public void update(User user){
        ContentValues values = new ContentValues();
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());
        values.put("phone", user.getPhone());
        values.put("email", user.getEmail());
        db.update("user", values, "id=?", new String[]{user.getId().toString()});
    }
}
