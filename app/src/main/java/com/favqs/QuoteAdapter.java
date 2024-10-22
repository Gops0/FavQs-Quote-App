package com.favqs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder> {

    private List<QuoteListResponse.Quote> quoteList;

    public QuoteAdapter(List<QuoteListResponse.Quote> quoteList) {
        this.quoteList = quoteList;
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item, parent, false);
        return new QuoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        QuoteListResponse.Quote quote = quoteList.get(position);
        holder.quoteTextView.setText(quote.body);
        holder.authorTextView.setText(quote.author);
    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }

    public static class QuoteViewHolder extends RecyclerView.ViewHolder {
        TextView quoteTextView, authorTextView;

        public QuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            quoteTextView = itemView.findViewById(R.id.quoteTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
        }
    }
}
