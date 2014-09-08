package com.mkulima.advisor;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class WhatToPlantFragment extends ListFragment 
{
	private static ArrayList<String> prices;
	
    public static WhatToPlantFragment newInstance(ArrayList<String> data) 
    {
    	WhatToPlantFragment fragment = new WhatToPlantFragment();

    	prices = data; 
    	Bundle args = new Bundle();
    	args.putStringArrayList("one", data);
        fragment.setArguments(args);
        return fragment;
    }

    public WhatToPlantFragment() {
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
    	View rootView = inflater.inflate(R.layout.fragment_what_to_plant, container, false);
    	
 
    	ArrayList<String> values = new ArrayList<String>();
    	
    	
    	if(prices.isEmpty())
    	{
    		
    	}
    	else{
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
    	        android.R.layout.simple_list_item_1, prices);
    	 setListAdapter(adapter);
    	}
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) 
    {
        super.onAttach(activity);
    }
}