package com.example.al.moviesp1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;

import models.DBMovieTable;
import models.MoviesTable;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

//    private static MovieAdapter mMovieAdapter;
    private static final int MOVIE_LOADER = 0;
    private static MovieCursorAdapter mMovieAdapter;
    private boolean isFavorite;
    private boolean isPopular;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i("MainActivityFragment", " onCreateLoader");

        return new CursorLoader(getActivity(),
                MoviesTable.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mMovieAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieAdapter.swapCursor(null);
    }


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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_sort_order_key), getString(R.string.pref_sort_order_popularity));
        Log.i("MainActiv onCreateView", sortOrder);

        final String[] MOVIE_COLUMNS = {
                MoviesTable.FIELD_POSTER_PATH,
                MoviesTable.FIELD_TITLE ,
                MoviesTable.FIELD_OVERVIEW,
                MoviesTable.FIELD_VOTEAVERAGE,
                MoviesTable.FIELD_POPULARITY,
                MoviesTable.FIELD_RELEASEDATE,
                MoviesTable.FIELD_ISPOPULAR
        };

        if (sortOrder.equals(getString(R.string.pref_sort_order_favorites)) )
            isFavorite = true;
        else
            isFavorite = false;

        if (sortOrder.equals(getString(R.string.pref_sort_order_popularity)) )
            isPopular = true;
        else
            isPopular = false;

//        final Cursor cursor = getActivity().getContentResolver().query(
//                MoviesTable.CONTENT_URI,
//                MOVIE_COLUMNS,
//                MoviesTable.FIELD_ID + " = ?",
//                new String[] String.valueOf(isPopular)
//                null);

        final Cursor cursor = getActivity().getContentResolver().query(MoviesTable.CONTENT_URI, null, null, null, null);

        Log.i("MainActiv onCreateView", "cursor works");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mMovieAdapter = new MovieCursorAdapter(getActivity(), cursor, 0);

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(mMovieAdapter);

        // Creating the intent to launch detailed view
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

     //           cursor.moveToFirst();

                Cursor cursor1 = (Cursor) mMovieAdapter.getItem(position);

                Log.i("MainActivityFragment", Integer.toString(position));
                Log.i("MainActivityFragment", cursor1.getString(cursor.getColumnIndex("id")));

                String movieId = cursor1.getString(cursor1.getColumnIndex("id"));

                Context context = getActivity();
                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra("movie", movieId);
                startActivity(detailIntent);
            }
        });
        return rootView;
    }

    private void update() {
        Log.i("MainActivityFrag update", getActivity().toString());
        GetMovieData movieData = new GetMovieData(getActivity());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_sort_order_key), getString(R.string.pref_sort_order_popularity));
        Log.i("MainActivityFrag update", sortOrder);

        movieData.updateMovies();
    }
}


