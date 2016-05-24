package com.example.al.moviesp1;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import models.MoviesTable;

/**
 * Created by Al on 2016-05-19.
 */
public class MovieCursorAdapter extends CursorAdapter {

    public MovieCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);

        return view;
    }

    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // our view is pretty simple here --- just a text view
        // we'll keep the UI functional with a simple (and slow!) binding.

        ImageView imageView = (ImageView) view.findViewById(R.id.movie_image);
//        imageView.setBackgroundResource(R.drawable.user_placeholder_error);

        Log.i("MainActivityFragment", cursor.getString(cursor.getColumnIndex("poster_path")));

                Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185/" + cursor.getString(cursor.getColumnIndex("poster_path")))
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .into(imageView);
    }
}
