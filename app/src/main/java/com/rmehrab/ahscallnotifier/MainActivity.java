package com.rmehrab.ahscallnotifier;

import android.app.ActivityManager;
import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    ArrayList listItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this,AddActivity.class));
            }
        });

        ListView myListView = findViewById(R.id.users_list);
        listItems = getListData();
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);//new CustomAdapter(getActivity(), R.layout.row, myStringArray1);
        //myListView.setAdapter(adapter);

        myListView.setAdapter(new CustomListAdapter(this,listItems));

        /*SwitchCompat switchCompat = (SwitchCompat)findViewById(R.id.person_switch);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //ViewParent
            }
        });
        //switchCompat.*/


    }

    private ArrayList getListData(){
        ArrayList<PersonItem> results = new ArrayList<PersonItem>();
        Cursor cursor = getFullListFromDB();
        try{
            while (cursor.moveToNext()) {
                PersonItem personData = new PersonItem();
                personData.setPersonName(cursor.getString(1));
                personData.setPersonPhone(cursor.getString(3));
                personData.setPersonEmail(cursor.getString(2));
                personData.setPersonNotify(cursor.getString(4));
                results.add(personData);
            }
        } finally {
            cursor.close();
        }
        return results;
    }

    private Cursor getFullListFromDB() {
        SQLiteDatabase database = new SQLiteDBHelper(this).getReadableDatabase();
        String[] projection = {SQLiteDBHelper.PERSON_COLUMN_ID, SQLiteDBHelper.PERSON_COLUMN_NAME, SQLiteDBHelper.PERSON_COLUMN_EMAIL, SQLiteDBHelper.PERSON_COLUMN_PHONE, SQLiteDBHelper.PERSON_COLUMN_NOTIFY};
        String sortOrder = SQLiteDBHelper.PERSON_COLUMN_NAME;

        Cursor cursor = database.query(SQLiteDBHelper.PERSON_TABLE_NAME, projection, null, null, null, null, sortOrder);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        //dbHelper.close();
        super.onDestroy();
    }
}
