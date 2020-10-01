package com.example.rentApp.Integration.Callback;

import com.example.rentApp.Integration.RetrofitInterface.ResponseCallback;
import com.example.rentApp.Response.MessageResponse;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class CustomizeCallback<T> implements Callback<T> {

    private ResponseCallback responseCallBack;

    public CustomizeCallback(ResponseCallback responseCallBack) {
        this.responseCallBack = responseCallBack;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!response.isSuccessful() && response.errorBody()!=null) {
            String error = null;
            try {
                error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class).getMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Customize callback not successfull "+error);
            responseCallBack.onError(error);
        } else if(response.body()!=null) {
            responseCallBack.onSuccess(response);
            System.out.println("Customize callback successfull "+response.body());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        String errorMessage = t.getMessage();
        responseCallBack.onError("Network error please try again later!" +errorMessage);
    }
}