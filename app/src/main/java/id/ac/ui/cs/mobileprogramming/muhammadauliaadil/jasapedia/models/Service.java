package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "services")
public class Service {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String overview;

    private double rating;

    private String category;

    private String location;

    private String hours;

    private String phoneNumber;

    private String imageUrl;

    public Service(String name, String overview, double rating, String category, String location, String hours, String phoneNumber) {
        this.name = name;
        this.overview = overview;
        this.rating = rating;
        this.category = category;
        this.location = location;
        this.hours = hours;
        this.phoneNumber = phoneNumber;
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

    public void setRating(int rating) {
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

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
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
}
