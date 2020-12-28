package com.example.librarysystem.eatit;
/**
 * BookDAO.java
 * This class is responsible for manage  the interaction between objects and the DB
 * @author: Darwin Machado
 *
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private ConnectionBook connection;
    private SQLiteDatabase db;

    //establish connection with database
    public BookDAO(Context context){
        connection = new ConnectionBook(context);
        db = connection.getWritableDatabase();
    }

    // insert values into DB Book
    public long insert(Book book){
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle());
        values.put("author", book.getAuthor());
        values.put("genre", book.getGenre());
        values.put("quantity", book.getQuantity());
         return db.insert("book", null, values);
    }

    //List all books in DB
    public List<Book> selectAll(){
        List<Book>  books = new ArrayList<>();
        Cursor cursor = db.query("book",new String[]{"id","title","author", "genre", "quantity"},
                null,null,null,null ,null);
        while(cursor.moveToNext()){
            Book b = new Book();
            b.setId(cursor.getInt(0));
            b.setTitle(cursor.getString(1)) ;
            b.setAuthor(cursor.getString(2));
            b.setGenre(cursor.getString(3));
            b.setQuantity(cursor.getInt(4));
            books.add(b);
        }
        return books;
    }

    //Remove book from DB
    public void removeBook(Book b){
        db.delete("book", "id=?", new String[]{b.getId().toString()});
    }

    //Update a book if already exists in the DB
    public void update(Book book){
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle());
        values.put("author", book.getAuthor());
        values.put("genre", book.getGenre());
        values.put("quantity", book.getQuantity());
        db.update("book", values, "id=?", new String[]{book.getId().toString()});
    }
    //Update a book, but decrement quantity
    public void updateBook(Book book, int quantity){
        ContentValues values = new ContentValues();
        values.put("quantity", quantity);
        db.update("book", values, "id=?", new String[]{book.getId().toString()});
    }

    //Update a book based on id collect from loaned book and increment quantity
    public void updateOneBook(Integer id, Integer quantity){
        ContentValues values = new ContentValues();
        values.put("quantity", quantity);
        db.update("book", values, "id=?", new String[]{id.toString()});
    }

}
