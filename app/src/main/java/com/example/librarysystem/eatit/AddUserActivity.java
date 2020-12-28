package com.example.librarysystem.eatit;
/**
 * AddUserActivity.java
 * This class allow owner to add a new user to the collection of users
 * @author: Darwin Machado
 *
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddUserActivity extends AppCompatActivity {

    private EditText firstName, lastName, phone, email;
    private UserDAO dao;
    private User user = null;

    //Method on create --> invoke all variable
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firstName = findViewById(R.id.firstName_input);
        lastName = findViewById(R.id.lastName_input);
        phone = findViewById(R.id.phone_input);
        email = findViewById(R.id.email_input);
        dao = new UserDAO( this);


        //Update user - if it already exists
        Intent it = getIntent();
        if(it.hasExtra("user")){
            user = (User) it.getSerializableExtra("user");
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            phone.setText(user.getPhone());
            email.setText(user.getEmail());
        }
    }

    //Method save a new User
    public void  saveUser (View view){
        // if user does NOT exists create a new one
        if( user == null) {
            user = new User();
            //Set variables name
            user.setFirstName(firstName.getText().toString());
            user.setLastName(lastName.getText().toString());
            user.setPhone(phone.getText().toString());
            user.setEmail(email.getText().toString());

            //Save new book on DB
            long id = dao.insert(user);
            Toast.makeText(this,"New user has been added",Toast.LENGTH_SHORT).show();
            Intent I =new Intent(this, MainActivity.class);
            startActivity(I);
        } else{
            //Set variables name
            user.setFirstName(firstName.getText().toString());
            user.setLastName(lastName.getText().toString());
            user.setPhone(phone.getText().toString());
            user.setEmail(email.getText().toString());
            //Update
            dao.update(user);
            Toast.makeText(this,"User has been updated!",Toast.LENGTH_SHORT).show();
            Intent I =new Intent(this, MainActivity.class);
            startActivity(I);

        }
    }
}
