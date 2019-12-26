package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "services")
public class Service {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "rating")
    private double rating;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "service_hours")
    private String serviceHours;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "cost")
    private int cost;

    @ColumnInfo(name = "unit_cost")
    private String unitCost;

    public Service(String name, String overview, double rating, String category, String location, String serviceHours, String phoneNumber, String imageUrl, int cost, String unitCost) {
        this.name = name;
        this.overview = overview;
        this.rating = rating;
        this.category = category;
        this.location = location;
        this.serviceHours = serviceHours;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
        this.cost = cost;
        this.unitCost = unitCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getServiceHours() {
        return serviceHours;
    }

    public void setServiceHours(String serviceHours) {
        this.serviceHours = serviceHours;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(String unitCost) {
        this.unitCost = unitCost;
    }
}
