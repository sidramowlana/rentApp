package com.example.rentApp.Integration.Service;

import com.example.rentApp.Integration.Models.DMV;
import com.example.rentApp.Integration.Repository.DMVRepository;
import com.example.rentApp.Integration.RetrofitInterface.ResponseCallback;
import com.example.rentApp.Integration.Service.DMVCallbackService;
import com.example.rentApp.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

@Service
public class DMVSchedulerService implements ResponseCallback {

    private DMVCallbackService dmvCallbackService;
    private DMVRepository dmvRepository;
    private List<DMV> dmvList = new ArrayList<>();

    @Autowired
    public DMVSchedulerService(DMVCallbackService dmvCallbackService, DMVRepository dmvRepository) {
        this.dmvCallbackService = dmvCallbackService;
        this.dmvRepository = dmvRepository;
    }

    @Scheduled(cron = "0 0 1 * * *")
//    @Scheduled(cron = "*/30 * * * * *")
    public void getDMVLicenseList() {
        dmvCallbackService.getAllLicense(this);
    }

    @Override
    public void onSuccess(Response response) {
        dmvList = (List<DMV>) response.body();
        List<DMV> alreadyExist = dmvRepository.findAll();
        if (alreadyExist != null) {
            for (DMV d : dmvList) {
                if (!dmvRepository.existsByDrivingLicence(d.getDrivingLicence())) {
                    saveDMVToDb(d);
                }
            }
        } else {
            for (DMV d : dmvList) {
                saveDMVToDb(d);
            }
        }
    }

    public void saveDMVToDb(DMV d) {
        DMV dmv = new DMV();
        dmv.setDrivingLicence(d.getDrivingLicence());
        dmv.setType(d.getType());
        dmvRepository.save(dmv);
    }

    @Override
    public void onError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public String getType(User user) {
        DMV dmv = dmvRepository.findByDrivingLicence(user.getDrivingLicence());
        return dmv.getType();
    }
}
