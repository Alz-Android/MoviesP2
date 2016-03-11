package RealmDbObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class MovieList extends RealmList{

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private RealmList<MovieJSON> results = new RealmList<>();
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public RealmList<MovieJSON> getResults() {
        return results;
    }

    public void setResults(RealmList<MovieJSON> results) {
        this.results = results;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @SerializedName("total_pages")
    @Expose

    private Integer totalPages;


}