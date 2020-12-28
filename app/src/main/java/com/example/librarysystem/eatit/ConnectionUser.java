package com.example.librarysystem.eatit;
/**
 * ConnectionUser.java
 * This class establish the connection with DB
 * @author: Darwin Machado
 *
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectionUser  extends SQLiteOpenHelper {

    private static final String name = "user.db";
    private static final int version = 1;

    public ConnectionUser(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user (id integer primary key autoincrement," +
                "firstName varchar(250)," +
                "lastName varchar(250), " +
                "phone varchar(250) NOT NULL UNIQUE," +
                "email varchar(250) NOT NULL UNIQUE)"
                 );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
