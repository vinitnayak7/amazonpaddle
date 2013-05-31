package com.amazon.paddle;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.amazon.paddle.credential.User;
import com.amazon.paddle.global.Global;
import com.amazon.paddle.web.WebRequest;

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

    @Override
    public void onResume() {
        super.onResume();
        new GetUsersTask().execute(Global.current_user);
    }
    
    private class GetUsersTask extends AsyncTask<User, Void, Boolean> {
        ProgressDialog progressDialog;
        String response;
        ArrayList<User> userList = new ArrayList<User>(); 
        
        @Override
        protected Boolean doInBackground(User... userArray) {
            User user = userArray[0];
            if (Global.current_user.username.length() == 0 || Global.current_user.password.length() == 0) {
                return false;
            }

            String urlParameters =
                "id=" + user.id;
     
            response = WebRequest.executeGet(Global.base_url + "getAllUsers.php?" + urlParameters, "");
            if (response != null) {
                Log.d("ALL USERS", response);
                String[] individualUsers = response.split("\n");
                for (String s : individualUsers) {
                    String[] userAttributes = s.split(",");
                    User u = new User();
                    u.username = userAttributes[0];
                    u.email = userAttributes[1];
                    u.id = Integer.parseInt(userAttributes[2]);
                    userList.add(u);
                }
                Log.d("ALL USERS", Integer.toString(userList.size()));
            } else {
                Log.d("ALL USERS", "no response!");
            }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.cancel();
            
            if (result == false) {
                Toast.makeText(UsersActivity.this, "Wrong username or password", Toast.LENGTH_LONG).show();  
                return;
            } 
            
        }
        
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(
                    UsersActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    private Button back;
    private ListView allUsers;
}
