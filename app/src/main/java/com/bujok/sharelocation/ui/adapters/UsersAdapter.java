package com.bujok.sharelocation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bujok.sharelocation.R;
import com.bujok.sharelocation.models.User;

import java.util.ArrayList;

/**
 * Created by Buje on 26/10/2016.
 */

public class UsersAdapter extends ArrayAdapter<User> {
    public UsersAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(android.R.id.text1);

        // Populate the data into the template view using the data object
        tvName.setText(user.username.toString());
       // tvHome.setText(user.hometown);
        // Return the completed view to render on screen
        return convertView;
    }
}