package com.favqs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder> {
    private List<QuoteListResponse.Quote> quoteList;
    private OnQuoteInteractionListener listener;

    public interface OnQuoteInteractionListener {
        void onCopyQuote(QuoteListResponse.Quote quote);
        void onShareQuote(QuoteListResponse.Quote quote);
    }

    public QuoteAdapter(List<QuoteListResponse.Quote> quoteList, OnQuoteInteractionListener listener) {
        this.quoteList = quoteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_item, parent, false);
        return new QuoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        QuoteListResponse.Quote quote = quoteList.get(position);
        holder.bind(quote);
    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }

    class QuoteViewHolder extends RecyclerView.ViewHolder {
        TextView quoteTextView, authorTextView;
        ImageButton btnCopy, btnShare;

        QuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            quoteTextView = itemView.findViewById(R.id.quoteTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            btnCopy = itemView.findViewById(R.id.btnCopy);
            btnShare = itemView.findViewById(R.id.btnShare);
        }

        void bind(QuoteListResponse.Quote quote) {
            quoteTextView.setText(quote.body);
            authorTextView.setText("- " + quote.author);

            btnCopy.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCopyQuote(quote);
                }
            });

            btnShare.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onShareQuote(quote);
                }
            });
        }
    }
}