package models;


import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Al on 2016-05-06.
 */

@SimpleSQLTable(table = "Review", provider = "ReviewProvider")
public class DBReviewTable {

    public DBReviewTable(){}

    public DBReviewTable(String id, String author, String content, String url){
        this.mId = id;
        this.mAuthor = author;
        this.mContent = content;
        this.mUrl = url;
    }

    @SimpleSQLColumn(value = "id", primary = true)
    public String mId;

    @SimpleSQLColumn("Author")
    public String mAuthor;

    @SimpleSQLColumn("Content")
    public String mContent;

    @SimpleSQLColumn("Url")
    public String mUrl;
}
