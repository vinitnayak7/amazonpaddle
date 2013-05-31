package com.amazon.paddle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ChallengesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);
        
        initializeElements();
    }
 
    /** Modularize the initialization of elements just in case we change them */
    private void initializeElements() {
        back = (Button) findViewById(R.id.challengesBack);
        challenges = (ListView) findViewById(R.id.challengesList);
    }
    
    /** just finish() when we are done looking through list of challenges.*/
    public void goBack(View v) {
        finish();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.challenges, menu);
        return true;
    }

    @Override
    public void onResume() {
        //TODO: pull down all Challenges and populates ListView, challenges
        super.onResume();
    }
    
    private ListView challenges;
    private Button back;
    
}
