package com.example.rentApp.Integration.Service;

import com.example.rentApp.Integration.Models.WebScrapping;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebScrapingService {
    public List<WebScrapping> getWebscrapeData() throws IOException {
        List<WebScrapping> webScrappingList = new ArrayList<>();
        Document document = Jsoup.connect("https://www.malkey.lk/rates/self-drive-rates.html").get();
        Elements body = document.select("tbody");

        for (Element e : body.select("tr:not([id])")) {
            int size = e.select("td").size();
            if (size == 4) {
                String vehicleName = e.select("td").get(0).text();
                String ratePerMonth = e.select("td").get(1).text();
                String ratePerWeek = e.select("td").get(2).text();
                String excessMilagePerDay = e.select("td").get(3).text();

                WebScrapping webScrapping = new WebScrapping
                        (vehicleName, ratePerMonth, ratePerWeek, excessMilagePerDay);
                webScrappingList.add(webScrapping);
            }
        }
        System.out.println(webScrappingList);
        return webScrappingList;
    }
}