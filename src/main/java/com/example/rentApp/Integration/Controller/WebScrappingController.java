package com.example.rentApp.Integration.Controller;

import com.example.rentApp.Integration.Models.WebScrapping;
import com.example.rentApp.Integration.Service.WebScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/webscrape/")
public class WebScrappingController {
    private WebScrapingService webScrapingService;

    @Autowired
    public WebScrappingController(WebScrapingService webScrapingService) {
        this.webScrapingService = webScrapingService;
    }

    @GetMapping("vehicle-data")
    public List<WebScrapping> getWebscrapeData() throws IOException {
       return webScrapingService.getWebscrapeData();
    }
}


