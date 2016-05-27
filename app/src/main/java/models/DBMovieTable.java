package models;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Al on 2016-05-03.
 */
@SimpleSQLTable(table = "Movies", provider = "MovieProvider")
public class DBMovieTable {
    public DBMovieTable(){}

    public DBMovieTable(String favorite, String id, String posterPath, String title, String plot, String userRating, String popularity,String releaseDate ){
        this.mFavorite = favorite;
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


    @SimpleSQLColumn("favorite")
    public String mFavorite;

    @SimpleSQLColumn("poster_path")
    public String mPosterPath;

    @SimpleSQLColumn("overview")
    public String mPlot;

    @SimpleSQLColumn("releaseDate")
    public String mReleaseDate;

    @SimpleSQLColumn("id")
    public String mId;

    @SimpleSQLColumn("title")
    public String mTitle;

    @SimpleSQLColumn("popularity")
    public String mPopularity;

    @SimpleSQLColumn("voteAverage")
    public String mUserRating;

    @SimpleSQLColumn("popular")
    public String mPopular;
}
