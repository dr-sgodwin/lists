package database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class MyListTable {

	  // Database table
	  public static final String TABLE_MYLIST = "mylist";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_CATEGORY = "category";
	  public static final String COLUMN_TASK = "task";
	  public static final String COLUMN_DESCRIPTION = "description";

	  // Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
	      + TABLE_MYLIST
	      + "(" 
	      + COLUMN_ID + " integer primary key autoincrement, " 
	      + COLUMN_CATEGORY + " text not null, " 
	      + COLUMN_TASK + " text not null," 
	      + COLUMN_DESCRIPTION
	      + " text not null" 
	      + ");";

	  public static void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
	      int newVersion) {
	    Log.w(MyListTable.class.getName(), "Upgrading database from version "
	        + oldVersion + " to " + newVersion
	        + ", which will destroy all old data");
	    database.execSQL("DROP TABLE IF EXISTS " + TABLE_MYLIST);
	    onCreate(database);
	  }
	} 
