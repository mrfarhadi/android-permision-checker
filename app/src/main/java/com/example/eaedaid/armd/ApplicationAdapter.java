package com.example.eaedaid.armd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ApplicationAdapter extends ArrayAdapter<ApplicationInfo> {
	private List<ApplicationInfo> appsList = null;
	private Context context;
	private PackageManager packageManager;


	public ApplicationAdapter(Context context, int textViewResourceId,
			List<ApplicationInfo> appsList) {
		super(context, textViewResourceId, appsList);
		this.context = context;
		this.appsList = appsList;
		packageManager = context.getPackageManager();
	}

	@Override
	public int getCount() {
		return ((null != appsList) ? appsList.size() : 0);
	}

	@Override
	public ApplicationInfo getItem(int position) {
		return ((null != appsList) ? appsList.get(position) : null);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static String[] getPermissionsByApp(PackageManager packageManager, ApplicationInfo data) {

		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		final List pkgAppsList = packageManager.queryIntentActivities(mainIntent, 0);
		String[] requestedPermissions = null;
		HashMap<Object ,String[] > appPermissonsMap = new HashMap<Object,String[]>();


		for (Object obj : pkgAppsList) {

			ResolveInfo resolveInfo = (ResolveInfo) obj;
			PackageInfo packageInfo = null;
			try {
				packageInfo = packageManager.getPackageInfo(resolveInfo.activityInfo.packageName, PackageManager.GET_PERMISSIONS);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			requestedPermissions = packageInfo.requestedPermissions;
			appPermissonsMap.put(obj, requestedPermissions);
		}
		return requestedPermissions;
	}

	public static List<String> createPermissionAppMap(PackageManager packageManager, String selectedPermission) {
		List<String> permissions = new ArrayList<String>();
		permissions.add(selectedPermission);
		HashMap<String, List<String>> tempMap = createPermissionAppMap(packageManager, permissions);
		List<String> applist = new ArrayList<String>();
		for(String key : tempMap.keySet()){
			applist.addAll(tempMap.get(key));
		}
		HashSet<String> temp = new HashSet<String>();
		temp.addAll(applist);
		applist.clear();
		applist.addAll(temp);
		return applist;
	}

	public static HashMap<String, List<String>> createPermissionAppMap(PackageManager packageManager, String[] selectedPermissions) {
		List<String> permissions = Arrays.asList(selectedPermissions);
		return createPermissionAppMap(packageManager, permissions );
	}

	public static HashMap<String, List<String>> createPermissionAppMap(PackageManager packageManager, List<String> selectedPermissions) {
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		final List pkgAppsList = packageManager.queryIntentActivities(mainIntent, 0);
		String[] requestedPermissions = null;
		HashMap<String ,List<String>> permissionAppMap = new HashMap<String,List<String>>();
		List<String> appList = new ArrayList<String>();

		HashMap<String, List<String>> results = new HashMap<String, List<String>>();
		String[] allPerms = PermissionManager.ALL_PERMISSIONS;

		for( String perm: allPerms) {
			results.put("android.permission."+perm, new ArrayList<String>());
		}

		for (Object obj : pkgAppsList) {
			ResolveInfo resolveInfo = (ResolveInfo) obj;
			PackageInfo packageInfo = null;
			try {
				packageInfo = packageManager.getPackageInfo(resolveInfo.activityInfo.packageName, PackageManager.GET_PERMISSIONS);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			requestedPermissions = packageInfo.requestedPermissions;


			try {
				for (String perm : requestedPermissions) { //fucked up code
					if (results.get(perm) != null)
						results.get(perm).add(packageInfo.applicationInfo.loadLabel(packageManager).toString());
				}
			}
			catch (NullPointerException e) {
				continue;
			}

			/*if(requestedPermissions != null) {
				for (String permission : requestedPermissions) {
					appList.add(packageInfo.applicationInfo.loadLabel(packageManager).toString());
					permissionAppMap.put(permission, appList);
				}
			}*/
		}
		return trimSelectedPermissionMap(results, selectedPermissions);
	}

	private static HashMap<String, List<String>> trimSelectedPermissionMap(HashMap<String, List<String>> permissionAppMap, List<String> selectedPermissions) {
		HashMap<String, List<String>> trimmedMap = new HashMap<String, List<String>>();
		for(String permission : selectedPermissions){
			if(permissionAppMap.containsKey("android.permission."+ permission)){
				trimmedMap.put(permission, permissionAppMap.get("android.permission."+ permission));
			}
		}
        for(String key : trimmedMap.keySet()){
            List<String> tempList = new ArrayList<String>(trimmedMap.get(key));
            HashSet<String> tempSet = new HashSet<String>();
            tempSet.addAll(tempList);
            tempList.clear();
            tempList.addAll(tempSet);
            trimmedMap.put(key, tempList);
        }
		return trimmedMap;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (null == view) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.snippet_list_row, null);
		}

		ApplicationInfo data = appsList.get(position);

		if (null != data) {
			TextView appName = (TextView) view.findViewById(R.id.app_name);
			TextView packageName = (TextView) view.findViewById(R.id.app_paackage);
			ImageView iconview = (ImageView) view.findViewById(R.id.app_icon);

			appName.setText(data.loadLabel(packageManager));
			packageName.setText(data.packageName);
			iconview.setImageDrawable(data.loadIcon(packageManager));
		}



		return view;
	}
};