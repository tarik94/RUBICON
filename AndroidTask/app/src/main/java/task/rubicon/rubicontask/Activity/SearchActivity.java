package task.rubicon.rubicontask.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import task.rubicon.rubicontask.Adapter.ArrayAdapterMovies;
import task.rubicon.rubicontask.Adapter.ArrayAdapterTvShows;
import task.rubicon.rubicontask.Model.Movie;
import task.rubicon.rubicontask.Model.ResultMovies;
import task.rubicon.rubicontask.Model.ResultTvShows;
import task.rubicon.rubicontask.Model.TvShow;
import task.rubicon.rubicontask.R;
import task.rubicon.rubicontask.Retrofit.ApiService;
import task.rubicon.rubicontask.Retrofit.RetrofitClient;

public class SearchActivity extends AppCompatActivity {

    private ImageButton back;
    private ImageButton clear;
    private EditText text;
    private ListView listView;

    private ArrayAdapterMovies arrayAdapterMovies;
    private ArrayAdapterTvShows arrayAdapterTvShows;

    private PublishSubject publishSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        back = findViewById(R.id.back);
        clear = findViewById(R.id.cancel);
        text = findViewById(R.id.searchText);
        listView = findViewById(R.id.liveSearchList);

        final int movies = getIntent().getIntExtra("Movies", 0);

        if(movies == 0){
            arrayAdapterMovies = new ArrayAdapterMovies(this, R.layout.listview_item, new ArrayList<>());
            listView.setAdapter(arrayAdapterMovies);
        } else {
            arrayAdapterTvShows = new ArrayAdapterTvShows(this, R.layout.listview_item, new ArrayList<>());
            listView.setAdapter(arrayAdapterTvShows);
        }

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
            if(movies == 0){
                intent.putExtra("Title", arrayAdapterMovies.getItem(position).getTitle());
                intent.putExtra("Image", arrayAdapterMovies.getItem(position).getPoster_path());
                intent.putExtra("Overview", arrayAdapterMovies.getItem(position).getOverview());
            } else {
                intent.putExtra("Title", arrayAdapterTvShows.getItem(position).getName());
                intent.putExtra("Image", arrayAdapterTvShows.getItem(position).getPoster_path());
                intent.putExtra("Overview", arrayAdapterTvShows.getItem(position).getOverview());
            }
            startActivity(intent);
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.getText().clear();
            }
        });

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 2){
                    if(publishSubject == null)
                        publishSubject = PublishSubject.create();
                    if(movies == 0){
                        publishSubject
                                .debounce(300, TimeUnit.MILLISECONDS)
                                .distinctUntilChanged()
                                .switchMap(searchValues -> RetrofitClient.getInstance().getMovies(s.toString())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread()))
                                .subscribeWith(new DisposableObserver<ResultMovies>() {
                                    @Override
                                    public void onNext(ResultMovies resultMovies) {
                                        arrayAdapterMovies.refresh(resultMovies.getResults());
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        publishSubject.onNext(s.toString());
                    } else {
                        publishSubject
                                .debounce(300, TimeUnit.MILLISECONDS)
                                .distinctUntilChanged()
                                .switchMap(searchValues -> RetrofitClient.getInstance().getTvShows(s.toString())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread()))
                                .subscribeWith(new DisposableObserver<ResultTvShows>() {
                                    @Override
                                    public void onNext(ResultTvShows resultTvShows) {
                                        arrayAdapterTvShows.refresh(resultTvShows.getResults());
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        publishSubject.onNext(s.toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
