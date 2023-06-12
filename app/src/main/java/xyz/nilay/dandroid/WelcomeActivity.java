package xyz.nilay.dandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class WelcomeActivity extends AppCompatActivity {

    private DatabaseHelper_SQLite dbHelper;
    TextView showName, showAge, showUpdatedName, showUpdatedAge;
    Button goBackButton, updateButton, deleteButton;
    String user_name, user_age;
    private int user_age_int;
    private long demo_newRowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        showName = findViewById(R.id.showName);
        showAge = findViewById(R.id.showAge);
        showUpdatedName = findViewById(R.id.showUpdatedName);
        showUpdatedAge = findViewById(R.id.showUpdatedAge);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        goBackButton = findViewById(R.id.goBackButton);

        user_name = getIntent().getStringExtra("user_name");
        showName.setText("Welcome "+user_name);
        user_age = getIntent().getStringExtra("user_age");
        showAge.setText("Age: "+user_age);

        dbHelper = new DatabaseHelper_SQLite(this);

        //Logging Database file path (use in dev environment only | can be removed)
        File dbFile = getDatabasePath(dbHelper.DATABASE_NAME);
        String dbPath = dbFile.getAbsolutePath();
        Log.d(WelcomeActivity.class.getSimpleName(), "Database Path: " + dbPath);

        if(user_name.isEmpty() || user_age.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Invalid Data Received! Data couldn't be stored!", Toast.LENGTH_LONG).show();
        }
        else {
            try {
                user_age_int = Integer.parseInt(user_age);
            }
            catch (NumberFormatException e) {
                Log.e(WelcomeActivity.class.getSimpleName(), "Unable to parse Age to int: "+user_age, e);
            }

            long newRowId = dbHelper.insertRecord(user_name, user_age_int);
            demo_newRowId = newRowId;

            Cursor cursor = dbHelper.getRecordById(newRowId);
            if(cursor.moveToFirst()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int age = cursor.getInt(2);

                Toast.makeText(getApplicationContext(), "ID: " + id + " | Name: " + name + " | Age: " + age, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Data successfully stored!", Toast.LENGTH_LONG).show();
            }

            // To Read All Records
            /* Cursor cursorAll = dbHelper.getAllRecords();
            if(cursorAll.moveToFirst()) {
                do {
                    int id = cursorAll.getInt(0);
                    String name = cursorAll.getString(1);
                    int age = cursorAll.getInt(2);

                    Toast.makeText(getApplicationContext(), "ID: " + id + " | Name: " + name + " | Age: " + age, Toast.LENGTH_LONG).show();
                } while (cursorAll.moveToNext());

                Toast.makeText(getApplicationContext(), "Data successfully stored!", Toast.LENGTH_LONG).show();
            } */
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.updateRecord(demo_newRowId, "Droid", 10);
                Cursor cursorUpdate = dbHelper.getRecordById(demo_newRowId);
                if(cursorUpdate.moveToFirst()) {
                    int id = cursorUpdate.getInt(cursorUpdate.getColumnIndexOrThrow(dbHelper.COLUMN_ID));
                    String name = cursorUpdate.getString(cursorUpdate.getColumnIndexOrThrow(dbHelper.COLUMN_NAME));
                    int age = cursorUpdate.getInt(cursorUpdate.getColumnIndexOrThrow(dbHelper.COLUMN_AGE));

                    showUpdatedName.setText("Updated Name: "+name);
                    showUpdatedAge.setText("Updated Age: "+age);
                    showUpdatedName.setVisibility(View.VISIBLE);
                    showUpdatedAge.setVisibility(View.VISIBLE);

                    Toast.makeText(getApplicationContext(), "ID: " + id + " | Name: " + name + " | Age: " + age, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Data successfully updated!", Toast.LENGTH_LONG).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted = dbHelper.deleteRecordById(demo_newRowId);
                if(isDeleted) {
                    Toast.makeText(getApplicationContext(), "Data successfully dele! (ID: " + demo_newRowId + ")", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Date couldn't be deleted! (ID: " + demo_newRowId + ")", Toast.LENGTH_LONG).show();
                }
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}