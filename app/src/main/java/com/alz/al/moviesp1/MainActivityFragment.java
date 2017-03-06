package com.alz.al.moviesp1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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

import com.alz.al.moviesp1.models.MoviesTable;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private static final int MOVIE_LOADER = 0;
    private static MovieCursorAdapter mMovieAdapter;
    private boolean isFavorite;
    private boolean isPopular;

    private GridView gridView;

    public static MovieCursorAdapter getMovieAdapter() {
        return mMovieAdapter;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i("MainActivityFragment", " onCreateLoader");

        String selection;
        String[] values;

        final String[] MOVIE_COLUMNS = {
                MoviesTable.FIELD__ID,
                MoviesTable.FIELD_ID,
                MoviesTable.FIELD_POSTER_PATH,
                MoviesTable.FIELD_TITLE,
                MoviesTable.FIELD_OVERVIEW,
                MoviesTable.FIELD_VOTEAVERAGE,
                MoviesTable.FIELD_POPULARITY,
                MoviesTable.FIELD_RELEASEDATE,
                MoviesTable.FIELD_ISPOPULAR
        };

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_sort_order_key), getString(R.string.pref_sort_order_popularity));

        // Distinsuishing the 3 settings: Favorites, Popular, High Rated
        //
        if (sortOrder.equals(getString(R.string.pref_sort_order_favorites)) ) {
            isFavorite = true;
            selection = MoviesTable.FIELD_FAVORITE + " = ?";
            values = new String[] {String.valueOf(isFavorite)};

        }else {
            isFavorite = false;

            if (sortOrder.equals(getString(R.string.pref_sort_order_popularity)))
                isPopular = true;
            else
                isPopular = false;

            selection = MoviesTable.FIELD_FAVORITE + " = ? and " + MoviesTable.FIELD_ISPOPULAR + " = ?";
            values = new String[] {String.valueOf(isFavorite), String.valueOf(isPopular)};
        }
        return new CursorLoader(getActivity(),
                MoviesTable.CONTENT_URI,
                MOVIE_COLUMNS,
                selection,
                values,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mMovieAdapter.swapCursor(cursor);

//        gridView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                gridView.setSelection(0);
//                gridView.performItemClick(gridView.getChildAt(0), 0, 0);
//            }
//        }, 2000);
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

            getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
            Log.i("MainActivityFragment", "onActivityResult()");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mMovieAdapter = new MovieCursorAdapter(getActivity(), null , 0);

        // Get a reference to the GridView, and attach this adapter to it.
        gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(mMovieAdapter);

        Log.i("MainActivityFragment", "performItemClick0");


        Log.i("MainActivityFragment", "performItemClick3");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Cursor cursor = (Cursor) mMovieAdapter.getItem(position);

                Log.i("MainActivityFragmentz", Integer.toString(position));
                Log.i("MainActivityFragment", cursor.getString(cursor.getColumnIndex("id")));

                String movieId = cursor.getString(cursor.getColumnIndex("id"));

                if(MainActivity.ismTwoPane()){

                    Bundle args = new Bundle();
                    args.putString("DetailFragment", movieId);

                    DetailActivityFragment detailFragment = new DetailActivityFragment();
                    detailFragment.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.detail_container, detailFragment, DETAILFRAGMENT_TAG)
                            .commit();
                }
                else {
                    Context context = getActivity();
                    Intent detailIntent = new Intent(context, DetailActivity.class);
                    detailIntent.putExtra("movie", movieId);
                    startActivity(detailIntent);
                }
            }
        });
        return rootView;
    }

    private void update() {
        Log.i("MainActivityFrag update", getActivity().toString());
        if(isOnline()) {
            GetMovieData movieData = new GetMovieData(getActivity());
            movieData.updateMovies();
        }
    }

    private boolean isOnline() {
        ConnectivityManager mngr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mngr.getActiveNetworkInfo();

        return !(info == null || (info.getState() != NetworkInfo.State.CONNECTED));
    }
}


