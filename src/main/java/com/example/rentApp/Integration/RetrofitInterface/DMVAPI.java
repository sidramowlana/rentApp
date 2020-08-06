package com.example.rentApp.Integration.RetrofitInterface;

import com.example.rentApp.Integration.Models.DMV;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import java.util.List;

public interface DMVAPI {

    @GET("api/dmv/getAll")
    Call<List<DMV>> getAllLicense();
}
