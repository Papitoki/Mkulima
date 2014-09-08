package com.mkulima.advisor.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

public class RetrievingFromDB
{
	private SQLDatabase sqldatabase;
	private Context context;
	
	public RetrievingFromDB(Context context)
	{
		this.context = context;
		sqldatabase = new SQLDatabase(context);
	}

	public ArrayList<String> obtainCursorDetails(Cursor cursor)
	{
		ArrayList<String> cursorDetails = new ArrayList<String>();
		cursorDetails.clear();
		
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
		{
			cursorDetails.add(cursor.getString(0));
		}
		
		return cursorDetails;
	}
	
	public Cursor readCropsTable() 
	{
		return sqldatabase.getReadableDatabase().rawQuery("select * from " + SQLDatabase.TABLE_CROP_PLANTED+ "", null);
	}

	public Cursor readUserName() 
	{
		return sqldatabase.getReadableDatabase().rawQuery("select " + SQLDatabase.USERNAME + " from " +  SQLDatabase.TABLE_PROFILE_DETAILS+ "", null);
	}
	
	public Cursor readUserLocation()
	{
		return sqldatabase.getReadableDatabase().rawQuery("select " + SQLDatabase.LOCATION + " from " +  SQLDatabase.TABLE_PROFILE_DETAILS+ "", null);
	}
}