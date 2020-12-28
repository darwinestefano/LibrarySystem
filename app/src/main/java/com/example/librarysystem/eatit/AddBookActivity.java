package com.example.librarysystem.eatit;
/**
 * AddBookActivity.java
 * This class allow owner to add a new book to the collection of books
 * @author: Darwin Machado
 *
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity  extends AppCompatActivity {

    private EditText title, author, genre, quantity;
    private BookDAO dao;
    private Book book = null;

    //Method on create --> invoke all variable
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title = findViewById(R.id.title_input);
        author = findViewById(R.id.author_input);
        genre = findViewById(R.id.genre_input);
        quantity = findViewById(R.id.quantity_input);
        dao = new BookDAO( this);


        //Update book - if it already exists
        Intent it = getIntent();
        if(it.hasExtra("book")){
            book = (Book) it.getSerializableExtra("book");
            title.setText(book.getTitle());
            author.setText(book.getAuthor());
            genre.setText(book.getGenre());
            int value = book.getQuantity();
            String finalValue = String.valueOf(value);
            quantity.setText(finalValue);
        }
    }

    //Method save a new book
    public void  save (View view){
        // if book does NOT exists create a new one
        if( book == null) {
            book = new Book();
            //Set variables name
            book.setTitle(title.getText().toString());
            book.setAuthor(author.getText().toString());
            book.setGenre(genre.getText().toString());
            String value = quantity.getText().toString();
            int finalValue = Integer.parseInt(value);
            book.setQuantity(finalValue);

            //Save new book on DB
            long id = dao.insert(book);
            Toast.makeText(this,"New book added",Toast.LENGTH_SHORT).show();
            Intent I =new Intent(this, MainActivity.class);
            startActivity(I);
        } else{
            //Set variables name
            book.setTitle(title.getText().toString());
            book.setAuthor(author.getText().toString());
            book.setGenre(genre.getText().toString());
            String value = quantity.getText().toString();
            int finalValue = Integer.parseInt(value);
            book.setQuantity(finalValue);
            //Update
            dao.update(book);
            Toast.makeText(this,"Book has been updated!",Toast.LENGTH_SHORT).show();
            Intent I =new Intent(this, MainActivity.class);
            startActivity(I);
        }


    }
}
