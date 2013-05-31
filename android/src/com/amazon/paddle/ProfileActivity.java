package com.amazon.paddle;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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
        //TODO: send to new activity where you grab all existing users except yourself and list them.
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
