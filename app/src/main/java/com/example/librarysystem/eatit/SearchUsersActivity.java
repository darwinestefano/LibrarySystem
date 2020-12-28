package com.example.librarysystem.eatit;
/**
 * SearchUserActivity.java
 * This class display all user and allo them to be updated and deleted.
 * @author: Darwin Machado
 *
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchUsersActivity extends AppCompatActivity {

    private ListView listView;
    private UserDAO dao;
    private List<User> users;
    private List<User> usersFilter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);

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



    }
    //Search btn
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.main_menu, menu);

        //Search for an book
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchBook(s);
                return false;
            }
        });

        return true;
    }

    //Search for Books - use filtered books list
    public void searchBook(String title){
        usersFilter.clear();
        for(User b: users){
            if(b.getFirstName().toLowerCase().contains(title.toLowerCase())){
                usersFilter.add(b);
            }
        }
        listView.invalidateViews();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.context_user_menu, menu);
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

        Intent it = new Intent(this,  AddUserActivity.class);
        it.putExtra("user", updateUser);
        startActivity(it);
    }

    //Resume the current list of books when modified
    @Override
    public void onResume(){
        super.onResume();
        users = dao.selectAll();
        usersFilter.clear();
        usersFilter.addAll(users);
        listView.invalidateViews();
    }

}