package com.smg.mylist;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import contentprovider.*;
import database.*;


@SuppressLint("NewApi")
public class Main extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	
	//private static final int ACTIVITY_CREATE = 0;
	//private static final int ACTIVITY_EDIT = 1;
	private static final int DELETE_ID = Menu.FIRST + 1;
	// private Cursor cursor;
	private SimpleCursorAdapter adapter;

	/** Called when the activity is first created. */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.item_list);
	    this.getListView().setDividerHeight(2);
	    fillData();
	    registerForContextMenu(getListView());
	  }

	  // Create the menu based on the XML definition
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.listmenu, menu);
	    return true;
	  }

	  // Reaction to the menu selection
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.insert:
	      createItem();
	      return true;
	    }
	    return super.onOptionsItemSelected(item);
	  }

	  @Override
	  public boolean onContextItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case DELETE_ID:
	      AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
	          .getMenuInfo();
	      Uri uri = Uri.parse(MyListContentProvider.CONTENT_URI + "/"
	          + info.id);
	      getContentResolver().delete(uri, null, null);
	      fillData();
	      return true;
	    }
	    return super.onContextItemSelected(item);
	  }

	  private void createItem() {
	    Intent i = new Intent(this, Detail.class);
	    startActivity(i);
	  }

	  // Opens the second activity if an entry is clicked
	  @Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
	    super.onListItemClick(l, v, position, id);
	    Intent i = new Intent(this, Detail.class);
	    Uri todoUri = Uri.parse(MyListContentProvider.CONTENT_URI + "/" + id);
	    i.putExtra(MyListContentProvider.CONTENT_ITEM_TYPE, todoUri);

	    startActivity(i);
	  }

	  @SuppressLint("NewApi")
	private void fillData() {

	    // Fields from the database (projection)
	    // Must include the _id column for the adapter to work
	    String[] from = new String[] { MyListTable.COLUMN_TASK };
	    // Fields on the UI to which we map
	    int[] to = new int[] { R.id.label };

	    getLoaderManager().initLoader(0, null, this);
	    adapter = new SimpleCursorAdapter(this, R.layout.item_row, null, from, to, 0);

	    setListAdapter(adapter);
	  }

	  @Override
	  public void onCreateContextMenu(ContextMenu menu, View v,
	      ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.add(0, DELETE_ID, 0, R.string.menu_delete);
	  }

	  // Creates a new loader after the initLoader () call
	  @Override
	  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
	    String[] projection = { MyListTable.COLUMN_ID, MyListTable.COLUMN_TASK };
	    CursorLoader cursorLoader = new CursorLoader(this,
	        MyListContentProvider.CONTENT_URI, projection, null, null, null);
	    return cursorLoader;
	  }

	@Override
	  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
	    adapter.swapCursor(data);
	  }

	  @Override
	  public void onLoaderReset(Loader<Cursor> loader) {
	    // data is not available anymore, delete reference
	    adapter.swapCursor(null);
	  }
    
}
