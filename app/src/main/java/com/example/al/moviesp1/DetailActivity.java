package com.example.al.moviesp1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

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

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Creating the intent to rcv data from the Main Activity
            // and attaching the data to the appropriate Views in Detail Activity
            Intent intent = getActivity().getIntent();
            final View rootView = inflater.inflate(R.layout.fragment_detail_activity, container, false);

            if(intent != null) {
                final MovieInfo movieObj = (MovieInfo)intent.getParcelableExtra("movie");
                ((TextView)rootView.findViewById(R.id.title_text)).setText(movieObj.mTitle);
                ((TextView)rootView.findViewById(R.id.releaseDate_text)).setText(movieObj.mReleaseDate);
                ((TextView)rootView.findViewById(R.id.userRating_text)).setText(movieObj.mUserRating);
                ((TextView)rootView.findViewById(R.id.plot_text)).setText(movieObj.mPlot);

                ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_image);

                Picasso.with(getContext())
                        .load("http://image.tmdb.org/t/p/w185/" + movieObj.mPosterPath)
                        .placeholder(R.drawable.user_placeholder)
                        .error(R.drawable.user_placeholder_error)
                        .into(imageView);

                (rootView.findViewById(R.id.trailer_text)).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        GetTrailerTask getTrailer = new GetTrailerTask(new FragmentCallback() {

                            @Override
                            public void onTaskDone() {
                                if(movieObj.mTrailerPath0 != null)
                                    LauncheTrailer(movieObj.mTrailerPath0);
                                else
                                    ((TextView)rootView.findViewById(R.id.trailer_text)).setText("Trailer Not Available");
                            }
                        });
                      getTrailer.execute(movieObj);
                    }

                });
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
