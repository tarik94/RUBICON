package task.rubicon.rubicontask.Model;

import java.util.ArrayList;

public class ResultTvShows {
    private ArrayList<TvShow> results;

    public ResultTvShows(ArrayList<TvShow> results) {
        this.results = results;
    }

    public ResultTvShows(){}

    public ArrayList<TvShow> getResults() {
        return results;
    }

    public void setResults(ArrayList<TvShow> results) {
        this.results = results;
    }
}
