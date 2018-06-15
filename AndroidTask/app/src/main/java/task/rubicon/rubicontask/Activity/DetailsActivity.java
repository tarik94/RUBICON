package task.rubicon.rubicontask.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import task.rubicon.rubicontask.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView titleDetails = findViewById(R.id.titleDetails);
        ImageView cover = findViewById(R.id.cover);
        TextView overview = findViewById(R.id.overview);

        String title = getIntent().getStringExtra("Title");
        String imagePath = getIntent().getStringExtra("Image");
        String stringOverview = getIntent().getStringExtra("Overview");

        titleDetails.setText(title);
        Picasso.get().load("https://image.tmdb.org/t/p/original" + imagePath).fit().centerInside().into(cover);
        overview.setText(stringOverview);
    }
}
