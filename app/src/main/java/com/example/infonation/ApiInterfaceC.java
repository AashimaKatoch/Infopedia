package com.example.infonation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterfaceC {

    String BASE_URL_C ="https://corona.lmao.ninja/v2/";

    @GET("countries")
    Call<List<ModelClassC>> getcountrydata();
}
