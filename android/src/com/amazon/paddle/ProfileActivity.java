package com.amazon.paddle;

import java.net.URLEncoder;

import com.amazon.paddle.credential.User;
import com.amazon.paddle.global.Global;
import com.amazon.paddle.web.WebRequest;

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
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

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
    
    private class LoadUserInfoTask extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog progressDialog;
        String response;
        
        @Override
        protected Boolean doInBackground(Void... params) {
            // Hack to convert int to string
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(Global.current_user.id);
            String urlParameters =
                    "id=" + URLEncoder.encode(sb.toString());
         
            response = WebRequest.executeGet(Global.base_url + "getWinLosses.php?" + urlParameters, "");
            
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
            Log.d("profile response", response);
            if (response.equals("BAD")) {
                record.setText("W-L: N/A");
            } else {
                record.setText("W-L: " + response);
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
            challenges.setText("Challenge");
            findUsers.setOnClickListener(new Button.OnClickListener() {
               @Override
               public void onClick(View v) {
                   //TODO: go to HHActivity
               }
            });
            challenges.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: go to ChallengeOpActivity
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
    
    /** On-Click method for challenges, takes you to ChallengesActivity. */
    public void goToChallengesActivity(View v) {
        //TODO: send to challenges activity, where you list all people who've challenged you
        Intent i = new Intent(this, ChallengesActivity.class);
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
