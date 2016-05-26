package com.example.al.moviesp1;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import models.DBMovieTable;
import models.MoviesTable;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public static class PlaceholderFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

        private static final int MOVIE_LOADER = 0;
//        private static MovieCursorAdapter mMovieAdapter;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            getLoaderManager().initLoader(MOVIE_LOADER, null, this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Log.i("DetailActivity", " onCreateLoader");

            return new CursorLoader(getActivity(),
                    MoviesTable.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
     //       MainActivityFragment.mMovieAdapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
    //        MainActivityFragment.mMovieAdapter.swapCursor(null);
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Creating the intent to rcv data from the Main Activity
            // and attaching the data to the appropriate Views in Detail Activity
            Log.i("DetailActivity", " onCreateView1");

            Intent intent = getActivity().getIntent();
            final View rootView = inflater.inflate(R.layout.fragment_detail_activity, container, false);

            if(intent != null) {

                Log.i("DetailActivity", " onCreateView2");

                final String movieId = intent.getStringExtra("movie");
                final String[] movieIdSelectionArg = new String[] {movieId};

                final String[] MOVIE_COLUMNS = { MoviesTable.FIELD_TITLE ,
                                                MoviesTable.FIELD_OVERVIEW,
                                                MoviesTable.FIELD_POSTER_PATH,
                                                MoviesTable.FIELD_RELEASEDATE,
                                                MoviesTable.FIELD_VOTEAVERAGE };

                final Cursor cursor = getActivity().getContentResolver().query(
                        MoviesTable.CONTENT_URI,
                        MOVIE_COLUMNS, MoviesTable.FIELD_ID + " = ?",
                        movieIdSelectionArg,
                        null);

                cursor.moveToFirst();
                Log.i("DetailActivity", cursor.getString(2));

                ((TextView)rootView.findViewById(R.id.title_text)).setText(cursor.getString(0));
                ((TextView)rootView.findViewById(R.id.plot_text)).setText(cursor.getString(1));
                ((TextView)rootView.findViewById(R.id.releaseDate_text)).setText(cursor.getString(3));
                ((TextView)rootView.findViewById(R.id.userRating_text)).setText(cursor.getString(4));

// //               ((TextView)rootView.findViewById(R.id.review_text)).setText(movieObj.mReviews);
//
                ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_image);

                Picasso.with(getContext())
                        .load("http://image.tmdb.org/t/p/w185/" + cursor.getString(2))
                        .placeholder(R.drawable.user_placeholder)
                        .error(R.drawable.user_placeholder_error)
                        .into(imageView);

                final ToggleButton tB = (ToggleButton) rootView.findViewById(R.id.favorites_ToggleButton);
                tB.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(tB.isChecked()){
                            Log.i("DetailActivityFragment", " Yes");

//                            DBMovieTable movieRow = new DBMovieTable(
//                                    "true",
//                                    movieObj.mId,
//                                    movieObj.mPosterPath,
//                                    movieObj.mTitle,
//                                    movieObj.mPlot,
//                                    movieObj.mUserRating,
//                                    movieObj.mPopularity,
//                                    movieObj.mReleaseDate
//                            );
//
//                            getContext().getContentResolver().insert(MoviesTable.CONTENT_URI, MoviesTable.getContentValues(movieRow,false));

                        }
                        else
                            Log.i("DetailActivityFragment", " No");


                    }
                });

//                (rootView.findViewById(R.id.trailer_text)).setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View view) {
//                        if(movieObj.mTrailerPath0 != null)
//                                    LauncheTrailer(movieObj.mTrailerPath0);
//                                else
//                                    ((TextView)rootView.findViewById(R.id.trailer_text)).setText("Trailer Not Available");
//                    }
//                });
            }
            return rootView;
        }
        private void LauncheTrailer(String trailerPath) {

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+trailerPath)));
        }

        public interface FragmentCallback {
            public void onTaskDone();
        }

    }
}
