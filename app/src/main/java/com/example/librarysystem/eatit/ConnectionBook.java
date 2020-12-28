package com.example.librarysystem.eatit;
/**
 * ConnectionBook.java
 * This class establish the connection with DB
 * @author: Darwin Machado
 *
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectionBook extends SQLiteOpenHelper {

    private static final String name = "banco.db";
    private static final int version = 1;

    public ConnectionBook(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table book(id integer primary key autoincrement," +
                "title varchar(250)," +
                "author varchar(250), " +
                "genre varchar(250)," +
                "quantity varchar(40) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
