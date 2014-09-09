package com.mkulima.advisor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mkulima.advisor.model.SQLDatabase;

public class EditProfileDialogFragment extends DialogFragment 
{
	private EditText userName;
	
	private Button btnSubmit;
	private Button btnCancel;
	
	private Spinner cropsSpinners;
	private Spinner location;
	
	private SQLDatabase sqlDatabase;
	
	private String farmerName;
	private String farmLocation;
	private String cropPlanted;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
		
		sqlDatabase = new SQLDatabase(getActivity());
		
		getDialog().setTitle("Edit Profile");
		
		userName = (EditText) view.findViewById(R.id.editusername);
		
		location = (Spinner) view.findViewById(R.id.editlocation);
		cropsSpinners = (Spinner) view.findViewById(R.id.spinnercrops);
		
		btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				farmerName = userName.getText().toString();
				farmLocation = location.getSelectedItem().toString();
				cropPlanted = cropsSpinners.getSelectedItem().toString();
				
				if(farmerName.equals("") && farmLocation.equals("") && cropPlanted.equals("N/A"))
				{
					Toast.makeText(getActivity(), "Fields are empty", Toast.LENGTH_LONG).show();
				}
				else if(cropPlanted.equals("N/A"))
				{
					if(farmLocation.equals(""))
					{
						farmLocation = "N/A";
					}
					else if(farmerName.equals(""))
					{
						farmerName = "N/A";
					}
					sqlDatabase.saveProfileDetails(farmerName, farmLocation);
					getDialog().dismiss();
				}
				
				if(!cropPlanted.equals("N/A"))
				{
					sqlDatabase.saveCropInfo(cropPlanted);
					getDialog().dismiss();
					new CommunicationManager().execute();
				}
				
				getFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment())
	            .commit();
				
				
			}
		});
		
		btnCancel = (Button) view.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				getDialog().cancel();
			}
		});
		
		return view;
	}
	
	private class CommunicationManager extends AsyncTask<String, Void, String>
	{
		ApiConnector api = new ApiConnector(getActivity());

		private ProgressDialog pDialog;
		
		String url = "http://192.34.59.123:8080/index.php";
		
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) 
		{
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
			nameValuePair.add(new BasicNameValuePair("recentCrop", cropPlanted));
			nameValuePair.add(new BasicNameValuePair("soilph", String.valueOf(7)));
			
			return api.GetAllCustomers(url, ApiConnector.POST,nameValuePair);
		}

		@Override
		protected void onPostExecute(String obj) 
		{
			pDialog.dismiss();
			
			//Testing
			System.out.println(obj);
			
			JSONObject json_a = null;
			
			try 
			{
				if(obj.equals(""))
				{
					
				}else
				{
					JSONObject json = new JSONObject(obj);
					json_a = json.getJSONObject("data");
				}
				
				ArrayList<String> array = new ArrayList<String>();
				
				Iterator iter = json_a.keys();
				 while(iter.hasNext())
				 {
				   String key = (String)iter.next();
				   
				   String priceData = key + "\n Kshs: " + json_a.getString(key);
				   array.add(priceData);
				 }
				 
				new WhatToPlantFragment();
				WhatToPlantFragment.newInstance(array);
			} 
			catch (JSONException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
