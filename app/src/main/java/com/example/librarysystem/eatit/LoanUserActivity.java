package com.example.librarysystem.eatit;
/**
 * LoanUserActivity.java
 * This class allow user to loan an book from the library system
 * @author: Darwin Machado
 *
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LoanUserActivity extends AppCompatActivity {

    private UserDAO dao;
    private LoanDAO ldao;
    private Book book;
    private User user;
    private ListView listView;
    private List<User> users;
    private List<User> usersFilter = new ArrayList<>();

    //Method on create --> invoke all variable
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);

        //List all Users to be assigned to a Book
        listView = findViewById(R.id.users_list);
        dao = new UserDAO(this);
        //add all books to my list
        users = dao.selectAll();
        usersFilter. addAll(users);

        //Create an Adapter
        UserAdapter adapter = new UserAdapter(this, usersFilter);
        //add adpter to List view
        listView.setAdapter(adapter);

        //When clicked show up options
        registerForContextMenu(listView);

        //Update user - if it already exists
        Intent it = getIntent();

        //Create an LoanDAO
        ldao = new LoanDAO( this);

        if(it.hasExtra("book")){
            book = (Book) it.getSerializableExtra("book");
            book.getTitle();
            book.getId();
            book.getAuthor();
            book.getGenre();
            book.getQuantity();
        }

    }
    //Inflate the context_menu containing UPDATE, LOAN and REMOVE
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.context_menu, menu);
    }

    //Delete a book from the list
    public void remove(MenuItem item){
        //Get position of info in the list
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)  item.getMenuInfo();
        final User deleteUser = usersFilter.get(menuInfo.position);

        //Confirm if user really wants to perform task
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Attention")
                .setMessage("Do you really want to delete this book?")
                .setNegativeButton("NO", null)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        usersFilter.remove(deleteUser);
                        users.remove(deleteUser);
                        dao.removeBook(deleteUser);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    //Update a book in the list
    public void update(MenuItem item){
        //Get position of info in the list
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)  item.getMenuInfo();

        final User updateUser = usersFilter.get(menuInfo.position);
        user.setId(updateUser.getId());
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setPhone(updateUser.getPhone());
        user.setEmail(updateUser.getEmail());

        Intent it = new Intent(this,  AddUserActivity.class);
        it.putExtra("user", updateUser);
        startActivity(it);
    }

    //Loan a book to an assigned user
    public void loan(MenuItem item){
        //Get position of info in the list
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)  item.getMenuInfo();

        final User userLoan = usersFilter.get(menuInfo.position);

        ldao.insert(book, userLoan);
        Toast.makeText(this,"The book has been loaned",Toast.LENGTH_SHORT).show();

        Intent I = new Intent(this, MainActivity.class);
        startActivity(I);


    }
}


