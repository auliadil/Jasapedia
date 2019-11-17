package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia;

import com.google.gson.annotations.SerializedName;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Category;

public class ListCategory {

    private int id;

    @SerializedName("categories")
    private Category[] categories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category[] getCategories() {
        return categories;
    }

    public void setCategories(Category[] categories) {
        this.categories = categories;
    }
}
