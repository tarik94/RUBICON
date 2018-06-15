package task.rubicon.rubicontask.Retrofit;

import retrofit2.http.GET;
import retrofit2.http.Query;
import task.rubicon.rubicontask.Model.ResultMovies;
import task.rubicon.rubicontask.Model.ResultTvShows;

public interface ApiService {
    @GET("movie/top_rated?api_key=3e282daf871755917862a2d4e9139571")
    io.reactivex.Observable<ResultMovies> getMovies();

    @GET("tv/top_rated?api_key=3e282daf871755917862a2d4e9139571")
    io.reactivex.Observable<ResultTvShows> getTvShows();

    @GET("search/movie?api_key=3e282daf871755917862a2d4e9139571")
    io.reactivex.Observable<ResultMovies> getMovies(@Query("query") String query);

    @GET("search/tv?api_key=3e282daf871755917862a2d4e9139571")
    io.reactivex.Observable<ResultTvShows> getTvShows(@Query("query") String query);
}
