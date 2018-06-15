package task.rubicon.rubicontask.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import task.rubicon.rubicontask.Adapter.ArrayAdapterMovies;
import task.rubicon.rubicontask.Adapter.ArrayAdapterTvShows;
import task.rubicon.rubicontask.Model.Movie;
import task.rubicon.rubicontask.Model.ResultMovies;
import task.rubicon.rubicontask.Model.ResultTvShows;
import task.rubicon.rubicontask.Model.TvShow;
import task.rubicon.rubicontask.R;
import task.rubicon.rubicontask.Retrofit.RetrofitClient;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private Toolbar toolbar;

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("Movies", tabLayout.getSelectedTabPosition());
                startActivity(intent);
                return false;
            }
        });
        return true;
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        private Disposable disposable;

        ArrayAdapterMovies arrayAdapterMovies;
        ArrayAdapterTvShows arrayAdapterTvShows;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ListView listView = rootView.findViewById(R.id.itemContainer);
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                arrayAdapterMovies = new ArrayAdapterMovies(getContext(), R.layout.listview_item, new ArrayList<Movie>());
                listView.setAdapter(arrayAdapterMovies);
                getMovies();
            } else {
                arrayAdapterTvShows = new ArrayAdapterTvShows(getContext(), R.layout.listview_item, new ArrayList<TvShow>());
                listView.setAdapter(arrayAdapterTvShows);
                getTvShows();
            }
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){
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
            return rootView;
        }

        void getMovies(){
            disposable = RetrofitClient.getInstance()
                    .getMovies()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResultMovies>() {
                        @Override
                        public void accept(ResultMovies resultMovies) throws Exception {
                            ArrayList<Movie> movies = new ArrayList<>();
                            if(resultMovies.getResults().size() > 10)
                                for(int i = 0; i < 10; i++)
                                    movies.add(resultMovies.getResults().get(i));
                            else
                                movies.addAll(resultMovies.getResults());
                            arrayAdapterMovies.refresh(movies);
                        }
                    });
        }

        void getTvShows(){
            disposable = RetrofitClient.getInstance()
                    .getTvShows()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResultTvShows>() {
                        @Override
                        public void accept(ResultTvShows resultTvShows) throws Exception {
                            ArrayList<TvShow> tvShows = new ArrayList<>();
                            if(resultTvShows.getResults().size() > 10)
                                for(int i = 0; i < 10; i++)
                                    tvShows.add(resultTvShows.getResults().get(i));
                            else
                                tvShows.addAll(resultTvShows.getResults());
                            arrayAdapterTvShows.refresh(tvShows);
                        }
                    });
        }

        @Override
        public void onDestroyView() {
            if(disposable != null && !disposable.isDisposed())
                disposable.dispose();
            super.onDestroyView();
        }

        @Override
        public void onStop() {
            if(disposable != null && !disposable.isDisposed())
                disposable.dispose();
            super.onStop();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
