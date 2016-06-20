package com.example.al.moviesp1.models;


import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Al on 2016-05-06.
 */

@SimpleSQLTable(table = "Review", provider = "ReviewProvider")
public class DBReviewTable {

    public DBReviewTable(){}

    public DBReviewTable(String movieId, String content){
        this.mMovieId = movieId;
        this.mContent = content;

//        this.mAuthor = author;
//        this.mUrl = url;
    }

    @SimpleSQLColumn("movieId")
    public String mMovieId;

    @SimpleSQLColumn("Content")
    public String mContent;

//    @SimpleSQLColumn(value = "id", primary = true)
//    public String mId;



//    @SimpleSQLColumn("Author")
//    public String mAuthor;
//
//    @SimpleSQLColumn("Url")
//    public String mUrl;
}
