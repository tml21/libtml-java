package com.example.mk.tmlexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_settings);

        //setting the texts of  the inputs, for what is presently saved there
        EditText editProfileid = (EditText) findViewById(R.id.editProfileId);
        EditText editIp = (EditText) findViewById(R.id.editIp);
        EditText editPort = (EditText) findViewById(R.id.editPort);

        editIp.setText(getIntent().getStringExtra("ip"));
        editPort.setText(getIntent().getStringExtra("port"));
        editProfileid.setText(getIntent().getStringExtra("profileid"));

        //option for an up-button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }


    public void saveSettings(View v) {
        //get 'new' input from user for ip, port and profilename
        String newIp = ((EditText) findViewById(R.id.editIp)).getText().toString();
        String newPort = ((EditText) findViewById(R.id.editPort)).getText().toString();
        String newProfileid = ((EditText) findViewById(R.id.editProfileId)).getText().toString();

        //send it as intent back to MainActivity
        Intent returnIntent = new Intent();
        returnIntent.putExtra("ip", newIp);
        returnIntent.putExtra("port", newPort);
        returnIntent.putExtra("profileid", newProfileid);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
