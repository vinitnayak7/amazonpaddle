package com.amazon.paddle;

import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import com.amazon.paddle.credential.User;
import com.amazon.paddle.global.Global;
import com.amazon.paddle.web.WebRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        //TODO: Need to populate TextViews: name, record, email (do this once, so not in onResume)?
        loadUserInfo();
    }
    
    private void loadUserInfo() {
        new LoadUserInfoTask().execute();
        //name.setText(Global.current_user.first_name);
    }
    
    /** SendChallengeTask is an asynctask making an api call for sendChallenge.php. */
    private class SendChallengeTask extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog progressDialog;
        String response;
        
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(
                    ProfileActivity.this);
            progressDialog.setMessage("Sending Challenge...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        
        @Override
        protected Boolean doInBackground(Void... params) {
            String  urlParameters = null;
                urlParameters =
                    "gameName=" + URLEncoder.encode("randomname") +
                    "&playerOne=" + URLEncoder.encode(String.valueOf(Global.current_user.id)) +
                    "&playerTwo=" + URLEncoder.encode(getIntent().getExtras().getString("ID"))+
                    "&comments=" + URLEncoder.encode("randomcomment");
                
           
            response = WebRequest.executeGet(Global.base_url + "sendChallenge.php?" + urlParameters, "");
            return true;
        }
        
        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.cancel();
            
            
                //Toast.makeText(RegistrationActivity.this, "Unknown Error has occurred", Toast.LENGTH_LONG).show(); 
            
        }
    }
    
    private class LoadUserInfoTask extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog progressDialog;
        String winloss;
        String uname;
        String uemail;
        
        @Override
        protected Boolean doInBackground(Void... params) {
            // Hack to convert int to string
            StringBuilder sb = new StringBuilder();
            sb.append("");
            if (isMyself) {
                sb.append(Global.current_user.id);
            } else {
                Log.d("Id", getIntent().getExtras().getString("ID"));
                sb.append(getIntent().getExtras().getString("ID"));
            }
            String urlParameters =
                    "id=" + URLEncoder.encode(sb.toString());
         
            winloss = WebRequest.executeGet(Global.base_url + "getWinLosses.php?" + urlParameters, "");
            uname = WebRequest.executeGet(Global.base_url + "getNameFromID.php?" + urlParameters, "");
            uemail = WebRequest.executeGet(Global.base_url + "getAllUsers.php?" + urlParameters, "");
            return true;
        }
        
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(
                    ProfileActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        
        @Override
        protected void onPostExecute(Boolean result) {
            Log.d("profile response", winloss);
            if (winloss.equals("BAD")) {
                record.setText("W-L: N/A");
            } else {
                record.setText("W-L: " + winloss.replace(':', '-'));
            }
            
            if (uname.equals("BAD")) {
                name.setText("Name: N/A");
            } else {
                name.setText("Name: " + uname);
            }
            
            if (uemail.equals("BAD")) {
                email.setText("Email: N/A");  
            } else {
                String[] csv = uemail.split(",");
                email.setText("Email: " + csv[1]);
            }
            progressDialog.cancel();
        }
    }

    /** Modularized initializing Elements just in case we change layout later. */
    private void initializeElements() {
        profilePicture = (QuickContactBadge) findViewById(R.id.quickContactBadge1);
        name = (TextView) findViewById(R.id.profileNameID);
        record = (TextView) findViewById(R.id.profileRecordID);
        email = (TextView) findViewById(R.id.profileEmailID);
        findUsers = (Button) findViewById(R.id.profileUsersID);
        challenges = (Button) findViewById(R.id.profileChallengesID);
        if (!isMyself) {
            findUsers.setText("Head2Head");
            challenges.setText("Challenge ME");
            findUsers.setOnClickListener(new Button.OnClickListener() {
               @Override
               public void onClick(View v) {
                   //TODO: go to HHActivity
               }
            });
            challenges.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                	AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                	builder.setMessage(R.string.dialog_sendChallenge_message);
                	builder.create();
                	builder.show();
                	Toast.makeText(ProfileActivity.this, "launches", Toast.LENGTH_SHORT);
                }
             });
        } else {
            challenges.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ProfileActivity.this, ChallengesActivity.class);
                    startActivity(i);
                }
             });
        }
        recentHistory = (ListView) findViewById(R.id.profileHistoryID);
    }
    
    /** On-Click method for findUsers, takes you to UsersActivity. */
    public void goToUsersActivity(View v) {
        Intent i = new Intent(this, UsersActivity.class);
        startActivity(i);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }
    
    @Override
    public void onResume() {
        //TODO: Need to pull recent activity and populate/update the ListView recentHistory
        isMyself = getIntent().getExtras() == null ? true : false;
        initializeElements();
        super.onResume();
    }
    
    
    private boolean isMyself;
    private QuickContactBadge profilePicture;
    private TextView name;
    private TextView record;
    private TextView email;
    private Button findUsers;
    private Button challenges;
    private ListView recentHistory;

}
