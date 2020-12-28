package com.example.librarysystem.eatit;
/**
 * ConnectionBook.java
 * Representation of the user list
 * @author: Darwin Machado
 *
 */
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LoanAdapter extends BaseAdapter {

    private List<Loan> loans;
    private Activity activity;

    //Constructor
    public LoanAdapter(Activity activity,List<Loan> loans){
        this.activity = activity;
        this.loans = loans;
    }

    @Override
    public int getCount() {
        return loans.size();
    }

    @Override
    public Object getItem(int position) {
        return loans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //create an item to be shown
        View view = activity.getLayoutInflater().inflate(R.layout.loan_item, parent , false);
        TextView bookName = view.findViewById(R.id.bookName_lst);
        TextView userName = view.findViewById(R.id.userName_lst);

        //Get object at position i
        Loan loan = loans.get(position);

        bookName.setText(loan.getBookTitle());
        userName.setText(loan.getUserName());

        return view;
    }
}
