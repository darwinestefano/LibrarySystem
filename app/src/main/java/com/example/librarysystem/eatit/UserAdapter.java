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

public class UserAdapter extends BaseAdapter {

    private List<User> users;
    private Activity activity;

    //Constructor
    public UserAdapter(Activity activity,List<User> users){
        this.activity = activity;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //create an item to be shown
        View view = activity.getLayoutInflater().inflate(R.layout.user_item, parent , false);
        TextView firstName = view.findViewById(R.id.firstName_lst);
        TextView lastName = view.findViewById(R.id.lastName_lst);
        TextView phone = view.findViewById(R.id.phone_lst);
        TextView email = view.findViewById(R.id.email_lst);

        //Get object at position i
        User user = users.get(position);

        //Set all values
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        phone.setText(user.getPhone());
        email.setText(user.getEmail());

        return view;
    }
}
