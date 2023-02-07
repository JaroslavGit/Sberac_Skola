package cz.oauh.hodl.eggcollector2;

public class CollectCl {
    private String date;
    private int eggCount;
    private int idWeather;
    private int degreeValue;

    public CollectCl(String date, int eggCount, int idWeather, int degreeValue) {
        this.date = date;
        this.eggCount = eggCount;
        this.idWeather = idWeather;
        this.degreeValue = degreeValue;
    }

    public String getDate() {
        return date;
    }

    public int getEggCount() {
        return eggCount;
    }

    public int getIdWeather() {
        return idWeather;
    }

    public int getDegreeValue() {
        return degreeValue;
    }
}
