package com.example.librarysystem.eatit;
/**
 * SerachActivity.java
 * This class list all the books in the library and allow user to remove, loan and update them
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ListView listView;
    private BookDAO dao;
    private List<Book> books;
    private List<Book> booksFilter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = findViewById(R.id.books_list);
        dao = new BookDAO(this);
        //add all books to my list
        books = dao.selectAll();
        booksFilter. addAll(books);

        //Create an Adapter
        //ArrayAdapter<Book> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, booksFilter);
        BookAdapter adapter = new BookAdapter(this, booksFilter);
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
        booksFilter.clear();
        for(Book b: books){
            if(b.getTitle().toLowerCase().contains(title.toLowerCase())){
                booksFilter.add(b);
            }
        }
        listView.invalidateViews();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.context_menu, menu);
    }

    //Delete a book from the list
    public void remove(MenuItem item){
        //Get position of info in the list
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)  item.getMenuInfo();
        final Book deleteBook = booksFilter.get(menuInfo.position);

        //Confirm if user really wants to perform task
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Attention")
                .setMessage("Do you really want to delete this book?")
                .setNegativeButton("NO", null)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        booksFilter.remove(deleteBook);
                        books.remove(deleteBook);
                        dao.removeBook(deleteBook);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
        Toast.makeText(this,"Book has been removed!",Toast.LENGTH_SHORT).show();
    }

    //Update a book in the list
    public void update(MenuItem item){
        //Get position of info in the list
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)  item.getMenuInfo();

        final Book updateBook = booksFilter.get(menuInfo.position);

        Intent it = new Intent(this, AddBookActivity.class);
        it.putExtra("book", updateBook);
        startActivity(it);
    }

    //Loan a book
    public void loan(MenuItem item){
        //Get position of info in the list
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)  item.getMenuInfo();
        final Book loanBook = booksFilter.get(menuInfo.position);

        if (loanBook.getQuantity()>=1){
            Intent it = new Intent(this, LoanUserActivity.class);
            it.putExtra("book", loanBook);
            //When loan a book decrease it from quantity
            int qty = loanBook.getQuantity();
            qty--;
            dao.updateBook(loanBook, qty);
            startActivity(it);

        }else{
            //Confirm if user really wants to perform task
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Attention")
                    .setMessage("This book is fully booked! Please, try it later.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            listView.invalidateViews();
                        }
                    }).create();
            dialog.show();
        }

    }

    //Resume the current list of books when modified
    @Override
    public void onResume(){
        super.onResume();
        books = dao.selectAll();
        booksFilter.clear();
        booksFilter.addAll(books);
        listView.invalidateViews();
    }

}