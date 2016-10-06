package com.example.framgia.imarketandroid.util;

import com.example.framgia.imarketandroid.data.model.CategoryList;
import com.example.framgia.imarketandroid.data.model.Session;
import com.example.framgia.imarketandroid.data.model.Store;
import com.example.framgia.imarketandroid.data.model.StoreTypeList;
import com.example.framgia.imarketandroid.data.model.Stores;
import com.example.framgia.imarketandroid.data.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yue on 22/07/2016.
 */
public interface IMarketApiEndPoint {
    @GET("stores/1/categories")
    Call<CategoryList> loadCategories();
    // TODO: 19/08/2016 change notification if have api
    @GET("users/32")
    Call<Session> eventNotification();
    @POST("sessions")
    Call<UserModel> login(@Body Session session);
    @POST("users")
    Call<UserModel> register(@Body UserModel user);
    @Headers("Content-Type: application/json")
    @PATCH("users/{iduser}")
    Call<UserModel> updateUser(@Path(value = "iduser",encoded = true) int iduser
        ,@Body UserModel userModel);
    @GET("commerce/1/store_type_list")
    Call<StoreTypeList> loadStoreType();
    @GET("stores")
    Call<Stores> getStoreByStoreType(@Query("store_type_id") int store_type_id);
}