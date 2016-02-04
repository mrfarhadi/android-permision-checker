package com.example.eaedaid.armd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ARMD extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armd);
        setTitle(getString(R.string.title));
        initializeMainList();
    }

    private void initializeMainList() {
        listView = (ListView) findViewById(R.id.list);
        String[] mainList = {"Application Permissions", "Risky Permissions"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(listView.getContext(), R.layout.support_simple_spinner_dropdown_item, mainList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listView.getItemAtPosition(position);
                if(itemValue.equalsIgnoreCase("Application Permissions")) {
                    Intent intent = new Intent(getApplicationContext(), PermissionSelectionActivity.class);
                    startActivity(intent);
                } else if (itemValue.equalsIgnoreCase("Risky Permissions")){
                    Intent intent = new Intent(getApplicationContext(), RiskyPermissionsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_armd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
