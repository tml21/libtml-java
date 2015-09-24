package com.example.mk.tmlexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity {
    protected int id;

    //activity for editing an item or inserting a new item to the database
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_item);

        if(getIntent().getIntExtra("id", -1) != -1) {
            EditText editTitle= (EditText) findViewById(R.id.editTitle);
            EditText editBody = (EditText) findViewById(R.id.editBody);

            editTitle.setText(getIntent().getStringExtra("title"));
            editBody.setText(getIntent().getStringExtra("body"));
            id = getIntent().getIntExtra("id", -1);
        }

        //option for an up-button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /* onClick callback for save-button in input_item view.
     * @param View view - not used, can be null
     */
    public void saveItem(View v) {
        String title = ((EditText) findViewById(R.id.editTitle)).getText().toString();
        String body = ((EditText) findViewById(R.id.editBody)).getText().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("title", title);
        returnIntent.putExtra("body", body);
        if(id != -1) {
            returnIntent.putExtra("id", id);
            setResult(RESULT_OK, returnIntent);
        } else {
            setResult(RESULT_CANCELED,returnIntent);
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //the save-option does the same as the save-button
            case R.id.action_save:
                saveItem(null);
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar- if it is present.
        getMenuInflater().inflate(R.menu.menu_input, menu);
        return true;
    }

}
