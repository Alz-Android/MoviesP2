package models;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Al on 2016-05-03.
 */
@SimpleSQLTable(table = "Movies", provider = "TestProvider")
public class DBMovieTable {
    public DBMovieTable(){}

    public DBMovieTable(int a, String b, String c, String d, Float e, Float f, String g){
        this.id = a;
        this.posterPath = b;
        this.title = c;
        this.overview = d;
        this.voteAverage = e;
        this.popularity = f;
        this.releaseDate = g;
    }
    @SimpleSQLColumn("poster_path")
    public String posterPath;

    @SimpleSQLColumn("overview")
    public String overview;

    @SimpleSQLColumn("releaseDate")
    public String releaseDate;

    @SimpleSQLColumn("id")
    public Integer id;

    @SimpleSQLColumn("title")
    public String title;

    @SimpleSQLColumn("popularity")
    public Float popularity;

    @SimpleSQLColumn("voteAverage")
    public Float voteAverage;
}
