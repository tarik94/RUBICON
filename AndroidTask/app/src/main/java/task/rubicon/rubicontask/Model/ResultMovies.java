package task.rubicon.rubicontask.Model;

import java.util.ArrayList;

public class ResultMovies {
    private ArrayList<Movie> results;

    public ResultMovies(ArrayList<Movie> results) {
        this.results = results;
    }

    public ResultMovies(){}

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }
}
