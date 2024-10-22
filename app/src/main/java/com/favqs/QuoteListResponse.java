package com.favqs;

import java.util.List;

public class QuoteListResponse {
    public List<Quote> quotes;

    public class Quote {
        public String body;
        public String author;
    }
}
