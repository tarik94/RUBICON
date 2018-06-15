package task.rubicon.rubicontask.Model;

public class Movie {
    private int id;
    private boolean video;
    private String title;
    private String poster_path;
    private String overview;

    public Movie(int id, boolean video, String title, String poster_path, String overview) {
        this.id = id;
        this.video = video;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
