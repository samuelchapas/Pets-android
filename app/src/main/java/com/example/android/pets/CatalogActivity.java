/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.pets.data.PetContracts;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PET_LOADER = 0;

    PetCursorAdapter mCursorAdapter;

    /** Database helper that will provide us access to the database */
    //private PetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        //
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        // mDbHelper = new PetDbHelper(this);

        // Find the ListView which will be populated with the pet data
        ListView petListView = (ListView) findViewById(R.id.text_view_pet);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);

        // Setup and Adapter to create a list item for each row of pet in the Cursor
        // There is no pet data yet (until the loader finishes) no pass in null for the Cursor
        mCursorAdapter = new PetCursorAdapter(this, null);
        petListView.setAdapter(mCursorAdapter);

        //Setup on item click listener
        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Create new intent to go to Editor Activity
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);

                //Form the content URI that represents the specific pet that was clicked on,
                //by appending the "id" (passed as input to this method) onto the
                // PetEntry.Content_URI
                // For example, the URI would be "content://com.example.android.pets/pets/2"
                //if the pet with ID 2 was clicked on.
                Uri currentPetUri = ContentUris.withAppendedId(PetContracts.PetEntry.CONTENT_URI, id);

                //Set the URI on the data field of the intent
                intent.setData(currentPetUri);

                // Launch the Editor Activity to display the data for the current pet
                startActivity(intent);
            }
        });
        // kick off the loader only use V4 support with getSupportLoaderManager
        getSupportLoaderManager().initLoader( PET_LOADER, null, this);
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.*/


    //private void displayDatabaseInfo() {

        //Define a projection that specifies which columns from the database
        //you will actually use after this query

        // Create and/or open a database to read from it
        //SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        //Cursor cursor = db.rawQuery("SELECT * FROM " + PetContracts.PetEntry.TABLE_NAME, null);
        /** For this example, assume that
         * public static final int GENDER_MALE = 0;
         * public static final int GENDER_FEMALE = 1;
         **/

       // String[] projection = {
        //        PetContracts.PetEntry._ID,
        //        PetContracts.PetEntry.COLUMN_PET_NAME,
       //         PetContracts.PetEntry.COLUMN_PET_BREED,
       //         PetContracts.PetEntry.COLUMN_PET_GENDER,
       //         PetContracts.PetEntry.COLUMN_PET_WEIGHT };
        //String selection = PetContracts.PetEntry.COLUMN_PET_GENDER + "=?";

        //String[] selectionArgs = new String[] { String.valueOf(PetContracts.PetEntry.GENDER_FEMALE) };

        /**
         * Before we were performing a query on the pets table
         * but this is not a recommended way to request a query, we have to
         * apply the content provider
         * Cursor cursor = db.query(
         * PetEntry.TABLE_NAME,  // the table to query
         * projection,  // the columns to return
         * null,  // the columns for the WHERE clause
         * null,  // The values for the WHERE clause
         * null,  // Don't groups by rows
         * null,  // Don't filter by row groups
         * null  //The sort order
         * );
         */

        // Perform a query on the provider using the ContentResolver, this is the correct way to do it
        // Use the Content_URI to access the pet data

      //  Cursor cursor = getContentResolver().query(
     //           PetContracts.PetEntry.CONTENT_URI, // the content URI of the words table
      //          projection,                        // the columns to return for each row
      //          null,                              // selection criteria
      //          null,                              // selection criteria
      //          null);                             // The sort order for the retuned rows

      //  ListView displayView = (ListView) findViewById(R.id.text_view_pet);

       // try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            /**
            displayView.setText("Number of rows in pets database table: " + cursor.getCount());

            //this is the way to put text inside the textView
            displayView.append(PetContracts.PetEntry._ID + " - " + PetContracts.PetEntry.COLUMN_PET_NAME + "\n");

            int idColumnIndex = cursor.getColumnIndex(PetContracts.PetEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetContracts.PetEntry.COLUMN_PET_NAME);

            while(cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                displayView.append("\n" + currentID + " - " + currentName);
            }
             */
     //       mCursorAdapter = new PetCursorAdapter(this, cursor);
     //       displayView.setAdapter(mCursorAdapter);
    //    } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
    //        cursor.close();
    //    }
    //}


    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    // this is the last time that we were working inserting data inside the data base
    /**
    private void insertPet() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(PetContracts.PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetContracts.PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetContracts.PetEntry.COLUMN_PET_GENDER, PetContracts.PetEntry.GENDER_MALE);
        values.put(PetContracts.PetEntry.COLUMN_PET_WEIGHT, 7);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(PetContracts.PetEntry.TABLE_NAME, null, values);
    }
    */

    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertPet() {
        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(PetContracts.PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetContracts.PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetContracts.PetEntry.COLUMN_PET_GENDER, PetContracts.PetEntry.GENDER_MALE);
        values.put(PetContracts.PetEntry.COLUMN_PET_WEIGHT, 7);

        // Insert a new row for Toto into the provider using the ContentResolver.
        // Use the {@link PetEntry#CONTENT_URI} to indicate that we want to insert
        // into the pets database table.
        // Receive the new content URI that will allow us to access Toto's data in the future.
        Uri newUri = getContentResolver().insert(PetContracts.PetEntry.CONTENT_URI, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                int rowsDeleted = getContentResolver().delete(PetContracts.PetEntry.CONTENT_URI, null, null);

                // Show a toast message depending on whether or not the delete was successful.
                if (rowsDeleted == 0) {
                    // If no rows were deleted, then there was an error with the delete.
                    Toast.makeText(this, getString(R.string.editor_delete_pet_failed),
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the delete was successful and we can display a toast.
                    Toast.makeText(this, getString(R.string.editor_delete_pet_successful),
                            Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //define a projection that specifies the columns from the table we care about

        String[] projection = {
                PetContracts.PetEntry._ID,
                PetContracts.PetEntry.COLUMN_PET_NAME,
                PetContracts.PetEntry.COLUMN_PET_BREED};
        //This loader will execute the ContentProvider a query method on a background thread
        return new CursorLoader(this,                   //Parent activity context
                PetContracts.PetEntry.CONTENT_URI,      //Provider content URI to query
                projection,                             //Columns to include in the resulting cursor
                null,                                   // no selection clause
                null,                                   // no selection arguments
                null);                                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // update with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}
