package com.test.userdata.repository.remote.api;

import com.test.userdata.repository.model.MediaCoverage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("content/misc/media-coverages")
    Call<List<MediaCoverage>> getMediaCoverages(@Query("limit") int limit);
}
