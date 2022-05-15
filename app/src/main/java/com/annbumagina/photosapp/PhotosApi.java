package com.annbumagina.photosapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotosApi {
    @GET("?key=21629364-9c339fd8b33b0c8ab7393376a&per_page=20&image_type=photo")
    Call<Photos> getPhotos(@Query("q") String tags);
}
