package com.example.librarysystem.eatit;
/**
 * BookAdapter.java
 * This class is the visualization of Books in a collection list
 * @author: Darwin Machado
 *
 */
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends BaseAdapter {

    private List<Book> books;
    private Activity activity;

    //Constructor
    public BookAdapter(Activity activity,List<Book> books){
        this.activity = activity;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return books.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //create an item to be shown
        View view = activity.getLayoutInflater().inflate(R.layout.item, parent , false);
        TextView title = view.findViewById(R.id.title_lst);
        TextView author = view.findViewById(R.id.author_lst);
        TextView genre = view.findViewById(R.id.genre_lst);
        TextView quantity = view.findViewById(R.id.quantity_lst);

        //Get object at position i
        Book book = books.get(position);

        //Set all values
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        genre.setText(book.getGenre());
        int value = book.getQuantity();
        String finalValue = String.valueOf(value);
        quantity.setText(finalValue);

        return view;
    }
}
