package models;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Al on 2016-05-06.
 */
@SimpleSQLTable(table = "Trailer", provider = "TrailerProvider")

public class DBTrailerTable {

    public DBTrailerTable(){}

    public DBTrailerTable(String id, String key ){
        this.mId = id;
        this.mKey = key;
  //      this.mName = name;
    }

    @SimpleSQLColumn("id")
    public String mId;

    @SimpleSQLColumn("key")
    public String mKey;

//    @SimpleSQLColumn("name")
//    public String mName;
}
