package com.example.al.moviesp1;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


/**
 * Created by Al on 2016-01-07.
 */
public class MovieInfo implements Parcelable {

    String mId;
    String mPosterPath;
    String mTitle;
    String mPlot;
    String mUserRating;
    String mPopularity;
    String mReleaseDate;
    String mTrailerPath0;
    String mTrailerPath1;

    // Each movieInfoList objects holds one movie's information that is later put into an array of movieInfoList objects
    //Parcelable was implemented for better performance than Serializable.

    public MovieInfo(String id, String posterPath, String title, String plot, String userRating, String popularity,String releaseDate )
    {
        Log.i("MainActivityFragmen", "movie object");
        this.mId = id;
        this.mPosterPath = posterPath;
        this.mTitle = title;
        this.mPlot = plot;
        this.mUserRating = userRating+"/10";
        this.mPopularity = popularity;
        if(!releaseDate.isEmpty())
            this.mReleaseDate = releaseDate.substring(0,4);
        else
            this.mReleaseDate = "n/a";
    }
    
    public void SetTrailers(String trailerPath0) {
        this.mTrailerPath0 = trailerPath0;
    }

    public void SetTrailers(String trailerPath0, String trailerPath1){
        this.mTrailerPath0 = trailerPath0;
        this.mTrailerPath1 = trailerPath1;
    }

    public MovieInfo(Parcel source) {
        mId = source.readString();
        mPosterPath = source.readString();
        mTitle = source.readString();
        mPlot = source.readString();
        mUserRating = source.readString();
        mPopularity = source.readString();
        mReleaseDate = source.readString();
        mTrailerPath0 = source.readString();
        mTrailerPath1 = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mPosterPath);
        dest.writeString(mTitle);
        dest.writeString(mPlot);
        dest.writeString(mUserRating);
        dest.writeString(mPopularity);
        dest.writeString(mReleaseDate);
        dest.writeString(mTrailerPath0);
        dest.writeString(mTrailerPath1);
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }

        @Override
        public MovieInfo createFromParcel(Parcel source) {
            return new MovieInfo(source);
        }
    };
}
