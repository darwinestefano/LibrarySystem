package com.example.librarysystem.eatit;
/**
 * LoanAdapter.java
 * This class manages the insertion, deletion of data in DB
 * @author: Darwin Machado
 *
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class LoanDAO {
    private ConnectionLoan connection;
    private SQLiteDatabase db;

    //establish connection with database
    public LoanDAO(Context context){
        connection = new ConnectionLoan(context);
        db = connection.getWritableDatabase();
    }

    // Insert into DB the book loaned by user
    public long insert(Book book, User user){
        ContentValues values = new ContentValues();
        values.put("bookId", book.getId());
        values.put("userId", user.getId());
        values.put("bookTitle", book.getTitle());
        values.put("userName", user.getFirstName());
        values.put("quantity",book.getQuantity());
        return db.insert("loan", null, values);
    }

    //Remove the loan from DB
    public void removeLoan(Loan l){
        db.delete("loan", "id=?", new String[]{l.getId().toString()});
    }

    //List all books in DB
    public List<Loan> selectAll(){

        //Create a list of loan that stores the books loaned by user
        List <Loan>  loans = new ArrayList<>();

        //Access DB loan
        Cursor cursor = db.query("loan",new String[]{"id","bookId","userId", "bookTitle", "userName","quantity"},
                null,null,null,null ,null);

        //Assign the values to the List
        while(cursor.moveToNext()){
            Loan l = new Loan();
            l.setId(cursor.getInt(0));
            l.setBookId(cursor.getInt(1));
            l.setUserId(cursor.getInt(2));
            l.setBookTitle(cursor.getString(3));
            l.setUserName(cursor.getString(4));
            l.setQuantity(cursor.getInt(5));
            loans.add(l);
        }
        cursor.close();
        return loans;
    }

}
