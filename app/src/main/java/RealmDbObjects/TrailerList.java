package RealmDbObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Al on 2/26/2016.
 */
public class TrailerList extends RealmObject{
        @SerializedName("id")
        @Expose
        private Integer id;

        @SerializedName("results")
        @Expose
        private RealmList<TrailerJSON> results = new RealmList<TrailerJSON>();

        public RealmList<TrailerJSON> getResults() {
                return results;
        }

        public void setResults(RealmList<TrailerJSON> results) {
                this.results = results;
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }


}
