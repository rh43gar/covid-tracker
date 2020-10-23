package io.study.covidtracker.services;

import io.study.covidtracker.models.Stats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CovidDataService {
    private static String COVID_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<Stats> allStats = new ArrayList<>();

    public List<Stats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchCovidData() throws IOException, InterruptedException {
        List<Stats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(COVID_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        int latestCases, prevDayCases;
        for (CSVRecord record : records) {
            Stats stat = new Stats();
            stat.setCountry(record.get("Country/Region"));
            latestCases = Integer.parseInt(record.get(record.size() - 1));
            prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            stat.setLatestTotalCases(latestCases);
            stat.setDiffFromPrevDay(latestCases - prevDayCases);
            System.out.println(stat);
            newStats.add(stat);
        }
        this.allStats = newStats;
    }
}
