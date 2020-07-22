package data;

public class TotalEntry {

    double totalCost;
    String desTotalCost;

    public TotalEntry(double totalCost, String desTotalCost){
        this.totalCost = totalCost;
        this.desTotalCost = desTotalCost;
    }

    public String getDesTotalCost() {
        return desTotalCost;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
