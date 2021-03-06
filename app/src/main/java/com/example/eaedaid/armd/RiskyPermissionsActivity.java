package com.example.eaedaid.armd;

import android.app.ActionBar;
import android.app.ExpandableListActivity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RiskyPermissionsActivity extends AppCompatActivity {

    private Switch cameraSwitch;
    private TextView switchStatus;
    private ListView listView;
    ExpandableListView expandableListView;
    private ApplicationAdapter listadaptor = null;
    private PackageManager packageManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.risky_activity_permissions);
        setTitle("Risky Permissions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        expandableListView =  (ExpandableListView)findViewById(R.id.exp_listview);

        packageManager = getPackageManager();
        String[] allPermissions = PermissionManager.RISKY_PERMISSIONS;
        HashMap<String, List<String>> applicationMap = listadaptor.createPermissionAppMap(packageManager, allPermissions);

        List<String> headings = new ArrayList<String>(applicationMap.keySet());
        HashMap<String,List<String>> childList = new HashMap<String,List<String>>() ;
        for(String key : applicationMap.keySet()){
            childList.put(key, applicationMap.get(key));
        }
        MyAdapter myAdapter = new MyAdapter(this, headings,childList);
        expandableListView.setAdapter(myAdapter);
    }

}
