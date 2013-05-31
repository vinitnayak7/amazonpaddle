package com.amazon.paddle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class HHActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hh);
    }

    /** Modularize initializing elements just in case we change them later. */
    public void initializeElements() {
        hhBack = (Button) findViewById(R.id.hhback);
        p1Name = (TextView) findViewById(R.id.hhP1);
        p2Name = (TextView) findViewById(R.id.hhP2);
        hhRecord = (TextView) findViewById(R.id.hhRecord);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hh, menu);
        return true;
    }

    @Override
    public void onResume() {
        initializeElements();
        super.onResume();
    }
    
    private Button hhBack;
    private TextView p1Name;
    private TextView p2Name;
    private TextView hhRecord;
}
