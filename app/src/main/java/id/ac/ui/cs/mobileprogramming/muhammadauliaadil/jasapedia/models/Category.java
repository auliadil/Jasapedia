package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class Category {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("alias")
    private String alias;

    @SerializedName("title")
    private String title;

    @SerializedName("parent_aliases")
    private String[] parent_aliases;

    @SerializedName("country_blacklist")
    private String[] country_blacklist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getParent_aliases() {
        return parent_aliases;
    }

    public void setParent_aliases(String[] parent_aliases) {
        this.parent_aliases = parent_aliases;
    }

    public String[] getCountry_blacklist() {
        return country_blacklist;
    }

    public void setCountry_blacklist(String[] country_blacklist) {
        this.country_blacklist = country_blacklist;
    }
}
