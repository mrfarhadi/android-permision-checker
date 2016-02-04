package com.example.eaedaid.armd;

/**
 * Created by emazimo on 10/22/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PermissionSelectionActivity extends AppCompatActivity {

    EditText inputSearch;
    private ListView mainListView = null;
    private Permission[] permissions = null;
    private ArrayAdapter<Permission> listAdapter = null;
    private ApplicationAdapter listadaptor = null;
    private PackageManager packageManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permissions_view);
        setTitle("Application Permissions");
        mainListView = (ListView) findViewById(R.id.mainListView);
        inputSearch = (EditText) findViewById(R.id.inputSearch);



        // When item is tapped, toggle checked properties of CheckBox and
        // Planet.
        mainListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View item,
                                            int position, long id) {
                        Permission permission = listAdapter.getItem(position);
                        permission.toggleChecked();
                        PermissionViewHolder viewHolder = (PermissionViewHolder) item
                                .getTag();
                        viewHolder.getCheckBox().setChecked(permission.isChecked());
                    }
                });


        // Create and populate permissions.
        String[] allPermissions = PermissionManager.ALL_PERMISSIONS;
        permissions = new Permission[allPermissions.length];
        for (int i = 0; i < permissions.length; i++) {
            permissions[i] = new Permission(allPermissions[i]);
        }

        final ArrayList<Permission> permissionsList = new ArrayList<Permission>();
        permissionsList.addAll(Arrays.asList(permissions));

        // Set our custom array adapter as the ListView's adapter.
        listAdapter = new PermissionArrayAdapter(this, permissionsList);
        mainListView.setAdapter(listAdapter);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                PermissionSelectionActivity.this.listAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        final Button button = (Button) findViewById(R.id.scanBtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<String> selectedPermissions = new ArrayList<String>();
                for (Permission per : permissionsList) {
                    if (per.isChecked()) {
                        selectedPermissions.add(per.getName());
                    }
                }
                packageManager = getPackageManager();
                PermissionManager.applicationMap = listadaptor.createPermissionAppMap(packageManager, selectedPermissions);
                Intent intent = new Intent(getApplicationContext(), PermissionsActivity.class);
                startActivity(intent);
            }
        });
    }
}

