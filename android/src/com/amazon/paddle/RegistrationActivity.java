package com.amazon.paddle;

import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazon.paddle.credential.User;
import com.amazon.paddle.global.Global;
import com.amazon.paddle.web.WebRequest;

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
        //TODO: set up the intent, register user info into server, start ProfileActivity
        final String user = username.getText().toString();
        final String emailaddr = email.getText().toString();
        final String pw = password.getText().toString();
        
        if (user.length() == 0 || emailaddr.length() == 0 || pw.length() == 0) {
            Toast.makeText(RegistrationActivity.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
        } else {
            //TODO: ASYNCTask for Registering user
            User u = new User();
            u.username = user;
            try {
                u.password = MenuActivity.md5hash(pw);
            } catch (NoSuchAlgorithmException nsae) {
                //gg no algorithm
            }
            Global.current_user = u;
            new RegisterTask().execute();
            Intent i = new Intent(RegistrationActivity.this, ProfileActivity.class);
            startActivity(i);
        }
    }
    
    /** RegisterTask that is an asynctask making an api call for register.php. */
    private class RegisterTask extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog progressDialog;
        String response;
        
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(
                    RegistrationActivity.this);
            progressDialog.setMessage("Registering...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        
        @Override
        protected Boolean doInBackground(Void... params) {
            String  urlParameters = null;
            try {
                urlParameters =
                    "username=" + URLEncoder.encode(username.getText().toString()) +
                    "&password=" + URLEncoder.encode(MenuActivity.md5hash(password.getText().toString())) +
                    "&email=" + URLEncoder.encode(email.getText().toString());
            } catch (NoSuchAlgorithmException nsae) {
                //gg can't hash
            }
            response = WebRequest.executeGet(Global.base_url + "register.php?" + urlParameters, "");
            return true;
        }
        
        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.cancel();
            
            try {
                Integer.parseInt(response.trim());
            } catch (NumberFormatException nfe) {
                Toast.makeText(RegistrationActivity.this, "Unknown Error has occurred", Toast.LENGTH_LONG).show(); 
            }
        }
    }
    
    private Button register;
    private EditText username;
    private EditText email;
    private EditText password;
}
