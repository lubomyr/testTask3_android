package com.testtaskapp.api;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CategoriesApi {
    @GET("api/v1/us/audiobooks/top-audiobooks/all/25/explicit.json")
    Call<JsonElement> getAudioBooks();

    @GET("api/v1/us/movies/top-movies/all/25/explicit.json")
    Call<JsonElement> getMovies();

    @GET("api/v1/us/podcasts/top-podcasts/all/25/explicit.json")
    Call<JsonElement> getPodcasts();

    @GET("lookup/{id}")
    Call<JsonElement> getItem(@Query("id") long id);
}
