package com.amazon.paddle;

import java.net.URLEncoder;

import com.amazon.paddle.credential.User;
import com.amazon.paddle.global.Global;
import com.amazon.paddle.web.WebRequest;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        
        final Resources res = this.getResources();
        final String pkg = this.getPackageName();
        login = (Button) findViewById(res.getIdentifier("loginID", "id", pkg));
        register = (Button) findViewById(res.getIdentifier("regID", "id", pkg));
        user = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(res.getIdentifier("passwordID", "id", pkg));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /** On-Click method for login button, to switch to ProfileActivity. */
    public void goToProfileActivity(View v) {
        User u = new User();
        u.username = user.getText().toString();
        u.password = password.getText().toString(); // TODO: md5 this
        
        if (this.login(u)) {
            Global.current_user = u;
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Wrong username or password", Toast.LENGTH_LONG).show();
        }
    }
    
    private boolean login(User user) {
        if (user.username.length() == 0 || user.password.length() == 0) {
            return false;
        }

        String urlParameters =
                "username=" + URLEncoder.encode(user.username) +
                "&password=" + URLEncoder.encode(user.password);

        String response = WebRequest.executeGet(Global.base_url + "login.php", urlParameters);

        if (response == null) {
            return false;
        }

        if (response.equals("OK")) {
            user.id = Integer.parseInt(response);
            return true;
        }

        return false;
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
