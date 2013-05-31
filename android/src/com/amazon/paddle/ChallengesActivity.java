package com.amazon.paddle;

import java.util.ArrayList;

import com.amazon.paddle.credential.User;
import com.amazon.paddle.global.Global;
import com.amazon.paddle.web.WebRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ChallengesActivity extends Activity {

    private ArrayList<Game> gameList = new ArrayList<Game>();
    private ArrayList<String> gameDisplayList = new ArrayList<String>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);
        
        initializeElements();
        /*
        challenges.setOnItemClickListener(new OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                Intent i = new Intent(ChallengesActivity.this, ChallengeAcceptRejectActivity.class);
                i.putExtra("ID", Integer.toString(userList.get(arg2).id));
                startActivity(i);
            }
            
        });
        */
    }
    
    @Override
    public void onResume() {
        super.onResume();
        gameList.clear();
        gameDisplayList.clear();
        new GetGamesTask().execute();
    }
    
    private class GetGamesTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        String response = "";
        
        @Override
        protected void onPostExecute(Void param) {
            progressDialog.cancel();
            populateGameList(gameDisplayList);
            return;
        }
        
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(
                    ChallengesActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            StringBuilder sb = new StringBuilder();
            sb.append("id=");
            sb.append(Global.current_user.id);
            String urlParameters = sb.toString();
                   
                response = WebRequest.executeGet(Global.base_url + "getChallenges.php?" + urlParameters, "");
                if (response != null && !response.trim().equals("failure")) {
                    String[] individualGames = response.split("\n");
                    int i = 0;
                    for (String s : individualGames) {
                        Log.d("s", s);
                        String[] gameAttributes = s.split(",");
                        if (gameAttributes.length > 0) {
                            Game g = new Game();
                            try {
                            g.id = Integer.parseInt(gameAttributes[0]);
                            } catch (Exception e) {
                                
                            }
                            g.name = gameAttributes[1];
                            g.p1 = gameAttributes[2];
                            g.p2 = gameAttributes[3];
                            try {
                                g.accepted = Integer.parseInt(gameAttributes[4]);
                            } catch (Exception e) {
                            }
                            g.comments = gameAttributes[5];
                            gameList.add(g);
                            gameDisplayList.add(g.p1 +"\n" + g.comments);
                        } else {
                        }
                        i++;
                    }
               
                } else {
                    gameDisplayList.add("No challenges found.");
                }
            return null;
        }
    }
    
    private void populateGameList(ArrayList<String> list) {
        ArrayAdapter<String> arrayAdapter =      
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
        challenges.setAdapter(arrayAdapter);
        challenges.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(ChallengesActivity.this);
            	builder.setMessage(R.string.dialog_acceptDecline);
            	builder.setPositiveButton("Bring it on!", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
            	});
            	builder.setNegativeButton("I'm Scared...", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
            	});
            	builder.create();
				builder.show();
			}
        });
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
    
    private ListView challenges;
    private Button back;
    
}
