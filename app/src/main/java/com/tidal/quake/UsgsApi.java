package com.tidal.quake;

//import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface UsgsApi {

    @GET("/{listType}")
    void getProperties(@Path("listType") String listType, Callback<QuakeModel> cb);

}
