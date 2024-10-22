package com.favqs;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.airbnb.lottie.LottieAnimationView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuoteAdapter quoteAdapter;
    private LottieAnimationView loadingAnimationView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadingAnimationView = findViewById(R.id.loadingAnimationView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchQuotes();
            }
        });

        fetchQuotes();
    }

    private void fetchQuotes() {
        loadingAnimationView.setVisibility(View.VISIBLE); // Show loading animation

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://favqs.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FavQsApi favQsApi = retrofit.create(FavQsApi.class);

        Call<QuoteListResponse> call = favQsApi.getQuotes(1);
        call.enqueue(new Callback<QuoteListResponse>() {
            @Override
            public void onResponse(Call<QuoteListResponse> call, Response<QuoteListResponse> response) {
                loadingAnimationView.setVisibility(View.GONE); // Hide loading animation
                swipeRefreshLayout.setRefreshing(false); // Stop swipe refresh animation
                if (response.isSuccessful() && response.body() != null) {
                    List<QuoteListResponse.Quote> quotes = response.body().quotes;
                    if (quotes != null) {
                        quoteAdapter = new QuoteAdapter(quotes);
                        recyclerView.setAdapter(quoteAdapter);
                    } else {
                        Toast.makeText(MainActivity.this, "No quotes found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load quotes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QuoteListResponse> call, Throwable t) {
                loadingAnimationView.setVisibility(View.GONE); // Hide loading animation
                swipeRefreshLayout.setRefreshing(false); // Stop swipe refresh animation
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
