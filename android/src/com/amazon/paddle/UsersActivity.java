package com.amazon.paddle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class UsersActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        
        initializeElements();
    }

    /** Modularize the initialization of elements just in case we change them */
    private void initializeElements() {
        back = (Button) findViewById(R.id.usersBack);
        allUsers = (ListView) findViewById(R.id.usersList);
    }
    
    /** just finish() when we are done looking through list of registered users and did not challenge.*/
    public void goBack(View v) {
        finish();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.users, menu);
        return true;
    }

    private Button back;
    private ListView allUsers;
}
