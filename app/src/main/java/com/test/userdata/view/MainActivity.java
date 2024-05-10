package com.test.userdata.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.userdata.R;
import com.test.userdata.repository.model.Thumbnail;
import com.test.userdata.repository.remote.api.APIInterface;
import com.test.userdata.repository.model.MediaCoverage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> imageURLs;
    private ImageAdapter adapter;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        // Initialize Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://acharyaprashant.org/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Fetch image URLs from API
        fetchImageURLs();

        // Initialize adapter
        adapter = new ImageAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void fetchImageURLs() {
        APIInterface apiInterface = retrofit.create(APIInterface.class);
        Call<List<MediaCoverage>> call = apiInterface.getMediaCoverages(100);
        call.enqueue(new Callback<List<MediaCoverage>>() {
            @Override
            public void onResponse(Call<List<MediaCoverage>> call, Response<List<MediaCoverage>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    imageURLs = new ArrayList<>();
                    for (MediaCoverage mediaCoverage : response.body()) {
                        Thumbnail thumbnail = mediaCoverage.getThumbnail();
                        String domain = thumbnail.getDomain() != null ? thumbnail.getDomain() : "";
                        String basePath = thumbnail.getBasePath() != null ? thumbnail.getBasePath() : "";
                        String key = thumbnail.getKey() != null ? thumbnail.getKey() : "";

                        String imageURL = domain + "/" + basePath + "/0/" + key;
                        imageURLs.add(imageURL);
                    }
                    adapter.setImageURLs(imageURLs);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MediaCoverage>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
