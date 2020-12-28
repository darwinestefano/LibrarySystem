package com.example.librarysystem.eatit;
/**
 * SerarchLoanedBooksActivity.java
 * This class is responsible for list all books loaned by it respective user.
 * @author Darwin Machado
 */

import android.content.DialogInterface;
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


public class SearchLoanedBooksActivity extends AppCompatActivity {

    private ListView listView;
    private LoanDAO dao;
    private BookDAO bdao;
    private List<Loan> loans;
    private List<Loan> loansFilter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_loans);

        listView = findViewById(R.id.loans_list);
        dao = new LoanDAO(this);
        bdao= new BookDAO(this);

        //add all books to my list
        loans = dao.selectAll();
        loansFilter.addAll(loans);

        //Create an Adapter
        LoanAdapter adapter = new LoanAdapter(this, loansFilter);

        //add adpter to List view
        listView.setAdapter(adapter);

        //When clicked show up options
        registerForContextMenu(listView);

    }
    //Show inflate menu when click on item from the list
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.context_return_menu, menu);
    }

    //Return a book and remove it from the list
    public void returnBook(MenuItem item){
        //Get position of info in the list
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)  item.getMenuInfo();
        final Loan returnBook = loansFilter.get(menuInfo.position);


        //Confirm if user really wants to perform task
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Attention")
                .setMessage("Do you really want to return this book?")
                .setNegativeButton("NO", null)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        int qty = returnBook.getQuantity();
                        qty++;
                        loansFilter.remove(returnBook);
                        loans.remove(returnBook);
                        dao.removeLoan(returnBook);
                        Integer id = returnBook.getBookId();
                        bdao.updateOneBook(id,qty);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
        Toast.makeText(this,"Book has been returned!",Toast.LENGTH_SHORT).show();
    }

    //Resume the current list of books when modified
    @Override
    public void onResume(){
        super.onResume();
        loans = dao.selectAll();
        loansFilter.clear();
        loansFilter.addAll(loans);
        listView.invalidateViews();
    }
}
