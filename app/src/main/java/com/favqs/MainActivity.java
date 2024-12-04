package com.favqs;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements QuoteAdapter.OnQuoteInteractionListener {

    private RecyclerView recyclerView;
    private QuoteAdapter quoteAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ExtendedFloatingActionButton fabRefresh;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        initializeViews();

        // Setup RecyclerView
        setupRecyclerView();

        // Fetch initial quotes
        fetchQuotes();
    }



    private void initializeViews() {
        // Find views
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        fabRefresh = findViewById(R.id.fabRefresh);
       // toolbar = findViewById(R.id.toolbar);

        // Set up toolbar
        setSupportActionBar(toolbar);

        // Set up refresh button
        fabRefresh.setOnClickListener(v -> fetchQuotes());

        // Set up swipe refresh
        swipeRefreshLayout.setOnRefreshListener(this::fetchQuotes);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchQuotes() {
        // Show loading state
        showLoadingState();

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://favqs.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create API service
        FavQsApi favQsApi = retrofit.create(FavQsApi.class);

        // Make API call
        Call<QuoteListResponse> call = favQsApi.getQuotes(1);
        call.enqueue(new Callback<QuoteListResponse>() {
            @Override
            public void onResponse(Call<QuoteListResponse> call, Response<QuoteListResponse> response) {
                // Hide loading state
                hideLoadingState();

                if (response.isSuccessful() && response.body() != null) {
                    List<QuoteListResponse.Quote> quotes = response.body().quotes;
                    if (quotes != null && !quotes.isEmpty()) {
                        // Update RecyclerView with quotes
                        updateQuotesList(quotes);
                    } else {
                        showError("No quotes found");
                    }
                } else {
                    showError("Failed to load quotes");
                }
            }

            @Override
            public void onFailure(Call<QuoteListResponse> call, Throwable t) {
                // Hide loading state
                hideLoadingState();
                showError("Error: " + t.getMessage());
            }
        });
    }

    private void updateQuotesList(List<QuoteListResponse.Quote> quotes) {
        quoteAdapter = new QuoteAdapter(quotes, this);
        recyclerView.setAdapter(quoteAdapter);
    }

    private void showLoadingState() {
        swipeRefreshLayout.setRefreshing(true);
    }

    private void hideLoadingState() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCopyQuote(QuoteListResponse.Quote quote) {
        // Copy quote to clipboard
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Quote", quote.body);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Quote copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareQuote(QuoteListResponse.Quote quote) {
        // Share quote via intent
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, quote.body + "\n- " + quote.author);
        startActivity(Intent.createChooser(shareIntent, "Share Quote"));
    }
}