package com.example.al.moviesp1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private static boolean mTwoPane;

    public static boolean ismTwoPane() {
        return mTwoPane;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.

            Log.i("main", "mTwoPane = true");

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, new DetailActivity.PlaceholderFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            Log.i("main", "mTwoPane = false");
            mTwoPane = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
