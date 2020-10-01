package com.example.rentApp.Integration.Service;

import com.example.rentApp.Integration.Callback.CustomizeCallback;
import com.example.rentApp.Integration.Models.DMV;
import com.example.rentApp.Integration.RetrofitClient.RetrofitClient;
import com.example.rentApp.Integration.RetrofitInterface.DMVAPI;
import com.example.rentApp.Integration.RetrofitInterface.ResponseCallback;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import java.util.List;

@Service
public class DMVCallbackService {
    DMVAPI dmvapi;
    RetrofitClient retrofitClient;

    public DMVCallbackService() {
        this.dmvapi = retrofitClient.getRetrofitClientInstance().create(DMVAPI.class);
    }

    public void getAllLicense(ResponseCallback callback) {
        Call<List<DMV>> call = dmvapi.getAllLicense();
        call.enqueue(new CustomizeCallback<List<DMV>>(callback));
    }
}


