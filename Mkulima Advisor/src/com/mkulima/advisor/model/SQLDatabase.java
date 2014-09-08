package com.mkulima.advisor.model;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

public class SQLDatabase extends SQLiteOpenHelper
{
	private static final String DB_NAME = "semausikike.db";
	private static final int DB_VERSION = 1;
	static final String _ID = BaseColumns._ID;

	public static final String TABLE_CROP_PLANTED = "crop_planted";
	public static final String TABLE_PROFILE_DETAILS  = "profile_details";
	public static final String TABLE_SOIL_DETAILS = "soil_details";
	
	public static final String CROP = "planted_crop";
	public static final String TIMESTAMP = "current_timestamp";
	
	public static final String USERNAME = "username";
	public static final String LOCATION = "location";
	public static final String UNIQUE = "identifier";
	
	public static final String SOIL_PH = "soilph";

	Context context;
	ContentValues values = new ContentValues();
	Date currentDate;

	public SQLDatabase(Context context) 
	{
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
		
		currentDate = new Date();
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		String sqlTableCropPlantedStructure = "create table " + TABLE_CROP_PLANTED 
				+ " (" + CROP + " text)";
		db.execSQL(sqlTableCropPlantedStructure);
		
		String sqlTableProfileDetails = "create table " + TABLE_PROFILE_DETAILS 
				+ " (" + USERNAME + " text , " + LOCATION + " text , " + UNIQUE + " text unique)";
		db.execSQL(sqlTableProfileDetails);
		
		String sqlTableSoilDetails = "create table " + TABLE_SOIL_DETAILS 
				+ " (" + SOIL_PH + " text)";
		db.execSQL(sqlTableSoilDetails);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("drop table if exists " + TABLE_CROP_PLANTED);
		db.execSQL("drop table if exists " + TABLE_PROFILE_DETAILS);
		db.execSQL("drop table if exists " + TABLE_SOIL_DETAILS);
		onCreate(db);
	}

	public void saveCropInfo(String crop) 
	{
		SQLiteDatabase db;

		try 
		{
			db = this.getWritableDatabase();

			values.clear();
			values.put(CROP, crop);
			long check = db.insertOrThrow(TABLE_CROP_PLANTED, null, values);
			successOrFail(check);
			db.close();
		}

		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void saveProfileDetails(String username, String location)
	{
		SQLiteDatabase db;

		try 
		{
			db = this.getWritableDatabase();

			values.clear();
			values.put(UNIQUE, 1);
			values.put(USERNAME, username);
			values.put(LOCATION, location);
			long check = db.replace(TABLE_PROFILE_DETAILS, null, values);
			successOrFail(check);
			db.close();
		}

		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void saveSoilDetails(String soilPH)
	{
		SQLiteDatabase db;

		try 
		{
			db = this.getWritableDatabase();

			values.clear();
			values.put(SOIL_PH, 7);
			long check = db.insert(TABLE_SOIL_DETAILS, null, values);
			successOrFail(check);
			db.close();
		}

		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void successOrFail(long value) 
	{
		if (value != 0) {
			 Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, "Error Occurred", Toast.LENGTH_LONG).show();
		}
	}
}
