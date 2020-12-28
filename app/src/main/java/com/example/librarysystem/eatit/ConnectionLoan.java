package com.example.librarysystem.eatit;
/**
 * ConnectionLoan.java
 * This class establish the connection with DB
 * @author: Darwin Machado
 *
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectionLoan extends SQLiteOpenHelper {

    private static final String name = "loan.db";
    private static final int version = 1;

    public ConnectionLoan(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table loan (id integer primary key autoincrement," +
                "bookId integer NOT NULL," +
                "userId integer NOT NULL, " +
                "bookTitle varchar (250) NOT NULL ," +
                "userName varchar(250) NOT NULL," +
                "quantity integer NOT NULL," +
                "FOREIGN KEY(bookId) REFERENCES book(id),"+
                "FOREIGN KEY(userId) REFERENCES user(id))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}