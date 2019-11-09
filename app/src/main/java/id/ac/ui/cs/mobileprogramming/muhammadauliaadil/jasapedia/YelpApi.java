package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface YelpApi {

    @GET("/categories")
    Call<List<Category>> getCategories(@Header("Authorization") String header);
}
