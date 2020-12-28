package com.example.librarysystem.eatit;
/**
 * ConnectionBook.java
 * This is the main class where it is displayed on the first screen of the application
 * @author: Darwin Machado
 *
 */

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnLoan, btnAddUser, btnAdd,btnSearch,btnLstUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // LOAN  SETTINGS
        btnLoan = findViewById(R.id.loan_btn);
        btnLoan.setOnClickListener((v) -> {
            Intent intent = new Intent( MainActivity.this, SearchLoanedBooksActivity.class);
            startActivity(intent);
        });


        // ADD SETTINGS
        btnAdd = findViewById(R.id.add_btn);
        btnAdd.setOnClickListener((v) -> {
            Intent intent = new Intent( MainActivity.this, AddBookActivity.class);
            startActivity(intent);
        });

        // CREATE A NEW USER SETTINGS
        btnAddUser = findViewById(R.id.addUser_btn);
        btnAddUser.setOnClickListener((v) -> {
            Intent intent = new Intent( MainActivity.this, AddUserActivity.class);
            startActivity(intent);
        });

        // LIST OF USER SETTINGS
        btnLstUser = findViewById(R.id.user_lst_btn);
        btnLstUser.setOnClickListener((v) -> {
            Intent intent = new Intent( MainActivity.this, SearchUsersActivity.class);
            startActivity(intent);
        });

        // SEARCH SETTINGS
        btnSearch = findViewById(R.id.search_btn);
        btnSearch.setOnClickListener((v) -> {
            Intent intent = new Intent( MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });
        //Drawable imgSearch = btnSearch.getContext().getResources().getDrawable( R.drawable.search_btn );
        //btnSearch.setCompoundDrawablesWithIntrinsicBounds( imgSearch, null, null, null);
    }
}