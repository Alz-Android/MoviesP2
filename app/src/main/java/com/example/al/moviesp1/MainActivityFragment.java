package com.example.al.moviesp1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static MovieAdapter mMovieAdapter;

    public static void setMovieAdapter(ArrayList<MovieInfo> moviesObj) {
        mMovieAdapter.clear();
        mMovieAdapter.addAll(moviesObj);
        mMovieAdapter.notifyDataSetChanged();
        GetTrailer trailer = new GetTrailer();
        trailer.GetTrailer();



    }
    public static MovieAdapter getMovieAdapter() { return mMovieAdapter;  }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for this fragment to handle menu events
        setHasOptionsMenu(true);

        update();
        Log.i("MainActivityFragment", "  onCreate()");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(getContext(), SettingsActivity.class), 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            update();
            Log.i("MainActivityFragment", "onActivityResult()");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<MovieInfo>());

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(mMovieAdapter);

        // Creating the intent to launch detailed view
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                MovieInfo movieObj = mMovieAdapter.getItem(position);
                Context context = getActivity();
                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra("movie", movieObj);
                startActivity(detailIntent);
            }
        });
        return rootView;
    }

    private void update(){
        GetMovieData movieData = new GetMovieData(getActivity());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_sort_order_key), getString(R.string.pref_sort_order_popularity));
        movieData.updateMovies(sortOrder);

        Log.i("sort1", "end update" );

    }
}


