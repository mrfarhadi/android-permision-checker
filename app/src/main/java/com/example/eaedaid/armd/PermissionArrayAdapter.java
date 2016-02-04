package com.example.eaedaid.armd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emazimo on 10/22/2015.
 */
public class PermissionArrayAdapter extends ArrayAdapter<Permission> {

    private LayoutInflater inflater;

    public PermissionArrayAdapter(Context context, List<Permission> planetList) {
        super(context, R.layout.simplerow, R.id.rowTextView, planetList);
        //Cache the LayoutInflate to avoid asking for a new one each time.
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Permission permission = (Permission) this.getItem(position);
        CheckBox checkBox;
        TextView textView;

        // Create a new row view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.simplerow, null);

            textView = (TextView) convertView.findViewById(R.id.rowTextView);
            checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);

            convertView.setTag(new PermissionViewHolder(textView, checkBox));

            // If CheckBox is toggled, update the permission it is tagged with.
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Permission planet = (Permission) cb.getTag();
                    planet.setChecked(cb.isChecked());
                }
            });
        }
        // Re-use existing row view
        else {

            PermissionViewHolder viewHolder = (PermissionViewHolder) convertView
                    .getTag();
            checkBox = viewHolder.getCheckBox();
            textView = viewHolder.getTextView();
        }

        checkBox.setTag(permission);

        // Display permission data
        checkBox.setChecked(permission.isChecked());
        textView.setText(permission.getName());

        return convertView;
    }
}

