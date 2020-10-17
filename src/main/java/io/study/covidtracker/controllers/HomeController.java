package io.study.covidtracker.controllers;

import io.study.covidtracker.models.Stats;
import io.study.covidtracker.services.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CovidDataService covidDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<Stats> allStats = covidDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("stats", allStats);
        return "home";
    }
}
