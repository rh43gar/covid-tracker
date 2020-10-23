package io.study.covidtracker.models;

public class Stats {
    private String country;
    private int latestTotalCases;
    private int diffFromPrevDay;

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public String getCountry() {
        return this.country;
    }

    public int getLatestTotalCases() {
        return this.latestTotalCases;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    @Override
    public String toString() {
        return "Stats{" +
                " country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                '}';
    }
}
