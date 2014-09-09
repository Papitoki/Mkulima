package com.mkulima.advisor;

import java.util.ArrayList;

import com.mkulima.advisor.model.RetrievingFromDB;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ProfileFragment extends ListFragment 
{
	private TextView userName;
	private TextView location;
	
	private String farmerName;
	private String farmerLocation;
	
	private RetrievingFromDB retrieveFromDB;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        
        retrieveFromDB = new RetrievingFromDB(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
    	View rootView = inflater.inflate(R.layout.fragment_home, container, false);
    	
    	userName = (TextView) rootView.findViewById(R.id.farmer_name);
    	location = (TextView) rootView.findViewById(R.id.location_name);
    	
    	Cursor dbCursorUserName = retrieveFromDB.readUserName();
    	farmerName = retrieveFromDB.obtainCursorDetails(dbCursorUserName).toString();
    	
    	Cursor dbCursorLocation = retrieveFromDB.readUserLocation();
    	farmerLocation = retrieveFromDB.obtainCursorDetails(dbCursorLocation).toString();
    	
    	if(farmerName != null || !farmerName.equals("N/A"))
    	{
    		userName.setText(farmerName);
    	}
    	
    	if(farmerLocation != null || !farmerLocation.equals("N/A"))
    	{
    		location.setText(farmerLocation);
    	}
    	
    	Cursor dbCursorCrops = retrieveFromDB.readCropsTable();
    	ArrayList<String> crops = new ArrayList<String>();
    	crops = retrieveFromDB.obtainCursorDetails(dbCursorCrops);
    	 
    	if(crops.isEmpty())
    	{
    		crops.add("No history found");
    	}

    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
    	        android.R.layout.simple_list_item_1, crops);
    	 setListAdapter(adapter);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) 
    {
        super.onAttach(activity);
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
    {
    	inflater.inflate(R.menu.profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	int id = item.getItemId();
        if (id == R.id.edit_profile)
        {
        	DialogFragment dialog = new EditProfileDialogFragment();
        	dialog.show(getFragmentManager(), null);
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

}