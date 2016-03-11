package RealmDbObjects;

import io.realm.RealmObject;

/**
 * Created by Al on 3/2/2016.
 */

//Realm forced this since it can't handle an ArrayList of Integers
public class IntegerObject extends RealmObject{

    private Integer genreId;

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }
}

