package task.rubicon.rubicontask.Model;

public class TvShow {
    private int id;
    private String name;
    private String overview;
    private String poster_path;

    public TvShow(int id, String name, String overview, String poster_path) {
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
