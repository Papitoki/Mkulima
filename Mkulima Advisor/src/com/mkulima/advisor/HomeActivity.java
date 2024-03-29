package com.mkulima.advisor;

import java.util.ArrayList;

import com.mkulima.advisor.model.SQLDatabase;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class HomeActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks 
{  
    //Fragment managing the behaviors, interactions and presentation of the navigation drawer. 
    private NavigationDrawerFragment mNavigationDrawerFragment;

    //Used to store the last screen title. For use in {@link #restoreActionBar()}.
    private CharSequence mTitle;
    
    private SQLDatabase sqlDatabase; 

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        //Dummy Data
        sqlDatabase = new SQLDatabase(this);
        sqlDatabase.saveProfileDetails("Rose", "Nairobi");

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        switch(position)
        {
        case 0:
        	getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ProfileFragment())
                    .commit();
        	break;
        	
        case 1:
        	//WhatToPlantFragment.prices.add("No suggestions");
        	getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, WhatToPlantFragment.newInstance(WhatToPlantFragment.prices))
            .commit();
        	break;
        	
        	default:
        		getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ProfileFragment())
                .commit();
        		
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.profile);
                break;
            case 2:
                mTitle = getString(R.string.top_trending);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        if (!mNavigationDrawerFragment.isDrawerOpen()) 
        {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) 
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
