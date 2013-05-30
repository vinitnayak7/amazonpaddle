package com.amazon.paddle;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        
        final Resources res = this.getResources();
        final String pkg = this.getPackageName();
        login = (Button) findViewById(res.getIdentifier("loginID", "id", pkg));
        register = (Button) findViewById(res.getIdentifier("regID", "id", pkg));
        user = (EditText) findViewById(res.getIdentifier("username", "id", pkg));
        password = (EditText) findViewById(res.getIdentifier("passwordID", "id", pkg));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /** On-Click method for login button, to switch to ProfileActivity. */
    public void goToProfileActivity() {
        //TODO: set up the intent, putExtras([user's name]), start ProfileActivity
    }
    
    /** On-Click method for register button (THIS register), to switch to RegistrationActivity. */
    public void goToRegistrationActivity() {
        //TODO: set up the intent, start RegistrationActivity
    }
    
    /**All the relevant items in the activity_menu layout that should respond to user input. */
    private Button login;
    private Button register;
    private EditText user;
    private EditText password;
}
