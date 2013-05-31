package com.amazon.paddle;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazon.paddle.web.WebRequest;
import com.amazon.paddle.credential.User;
import com.amazon.paddle.global.*;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
       
        initializeElements();
    }

    /** Modularized initializing Elements in case we change layout later.*/
    private void initializeElements() {
        login = (Button) findViewById(R.id.loginID);
        register = (Button) findViewById(R.id.regID);
        user = (EditText) findViewById(R.id.usernameID);
        password = (EditText) findViewById(R.id.passwordID);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    /** On-Click method for login button, to switch to ProfileActivity. 
     * @throws NoSuchAlgorithmException 
     * @throws InterruptedException */
    public void goToProfileActivity(View v) throws NoSuchAlgorithmException, InterruptedException {
        User u = new User();
        u.username = user.getText().toString();
        
        String plaintext = password.getText().toString();
        u.password = md5hash(plaintext);
        Global.current_user = u;
        new LoginTask().execute(u);
    }
    
    public static String md5hash(final String input) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(input.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while(hashtext.length() < 32 ){
          hashtext = "0"+hashtext;
        }
        return hashtext;
    }
    private class LoginTask extends AsyncTask<User, Void, Boolean> {
        ProgressDialog progressDialog;
        String response;
        
        @Override
        protected Boolean doInBackground(User... userArray) {
            User user = userArray[0];
            if (Global.current_user.username.length() == 0 || Global.current_user.password.length() == 0) {
                return false;
            }

            String urlParameters =
                "username=" + URLEncoder.encode(user.username) +
                "&password=" + URLEncoder.encode(user.password);
     
            response = WebRequest.executeGet(Global.base_url + "login.php?" + urlParameters, "");

            return true;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.cancel();
            
            if (result == false) {
                Toast.makeText(MenuActivity.this, "Wrong username or password", Toast.LENGTH_LONG).show();  
                return;
            } 
            
            boolean isId = false;
            
            try {
                Global.current_user.id = Integer.parseInt(response.trim());
                isId = true;
            } catch (NumberFormatException e) {
                // do nothing
            }
   
            if (isId) {
                Intent i = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(MenuActivity.this, "Wrong username or password", Toast.LENGTH_LONG).show();
            }
        }
        
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(
                    MenuActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }
    
    /** On-Click method for register button (THIS register), to switch to RegistrationActivity. */
    public void goToRegistrationActivity(View v) {
        //TODO: set up the intent, start RegistrationActivity
        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);
    }
    
    @Override
    public void onPause() {
        user.setText(null);
        password.setText(null);
        super.onPause();
    }
    
    /**All the relevant items in the activity_menu layout that should respond to user input. */
    private Button login;
    private Button register;
    private EditText user;
    private EditText password;
}
