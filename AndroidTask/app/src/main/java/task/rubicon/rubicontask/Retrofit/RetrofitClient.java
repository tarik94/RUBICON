package task.rubicon.rubicontask.Retrofit;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import task.rubicon.rubicontask.Model.ResultMovies;
import task.rubicon.rubicontask.Model.ResultTvShows;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static RetrofitClient instance;
    private ApiService apiService;

    private RetrofitClient(){
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public ApiService getApiService(){
        return apiService;
    }

    public io.reactivex.Observable<ResultMovies> getMovies(){
        return apiService.getMovies();
    }

    public io.reactivex.Observable<ResultMovies> getMovies(String query){
        return apiService.getMovies(query);
    }

    public io.reactivex.Observable<ResultTvShows> getTvShows(){
        return apiService.getTvShows();
    }

    public io.reactivex.Observable<ResultTvShows> getTvShows(String query) {
        return apiService.getTvShows(query);
    }
}
