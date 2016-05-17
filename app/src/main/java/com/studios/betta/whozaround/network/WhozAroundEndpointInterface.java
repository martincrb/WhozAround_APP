package com.studios.betta.whozaround.network;

import com.studios.betta.whozaround.objects.Trip;
import com.studios.betta.whozaround.objects.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Martin on 15/05/2016.
 */
public interface WhozAroundEndpointInterface {
    @GET("/whozapi/v1/users/{user}")
    public Call<User> getUser(@Path("user") String user);

    @POST("/whozapi/v1/users")
    public Call<User> createUser(@Body User user);

    @POST("/whozapi/v1/users/{user}/trips")
    public Call<Trip> addTrip(@Path("user") String user, @Body Trip trip);

    @GET("/whozapi/v1/users/{user}/trips")
    public Call<List<Trip>> getTrips(@Path("user") String user);
}
