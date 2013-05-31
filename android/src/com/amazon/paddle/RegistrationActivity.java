package com.amazon.paddle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        
        initializeElements();
    }

    /** Modularize initializing elements just incase we decide to change them later.*/
    private void initializeElements() {
        register = (Button) findViewById(R.id.registerID);
        username = (EditText) findViewById(R.id.rUsernameID);
        email = (EditText) findViewById(R.id.rEmailID);
        password = (EditText) findViewById(R.id.rPasswordID);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registration, menu);
        return true;
    }


    /** On-Click method for login button, to switch to ProfileActivity. */
    public void goToProfileActivity(View v) {
        //TODO: set up the intent, putExtras([user's name]), start ProfileActivity
    }
    
    private Button register;
    private EditText username;
    private EditText email;
    private EditText password;
}
