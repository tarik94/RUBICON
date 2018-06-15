package task.rubicon.rubicontask.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import task.rubicon.rubicontask.Model.Movie;
import task.rubicon.rubicontask.Model.TvShow;
import task.rubicon.rubicontask.R;

public class ArrayAdapterTvShows extends ArrayAdapter<TvShow> {

    private Context context;
    private int resource;
    private ArrayList<TvShow> objects;

    public ArrayAdapterTvShows(@NonNull Context context, int resource, @NonNull ArrayList<TvShow> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resource, parent, false);
        }
        ImageView posterImage = view.findViewById(R.id.posterImage);
        TextView title = view.findViewById(R.id.title);
        if(position > -1) {
            TvShow item = getItem(position);
            title.setText(item.getName());
            Picasso.get().load("https://image.tmdb.org/t/p/original" + item.getPoster_path()).fit().centerInside().into(posterImage);
        }
        return view;
    }

    public void refresh(ArrayList<TvShow> tvShows){
        objects.clear();
        objects.addAll(tvShows);
        this.notifyDataSetChanged();
    }
}
