package com.favqs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface FavQsApi {

    String API_KEY = "f5294d81d33fd02e451ef484272d31ab";

    @Headers("Authorization: Token token=" + API_KEY)
    @GET("api/quotes")
    Call<QuoteListResponse> getQuotes(@Query("page") int page);
}
