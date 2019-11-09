package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface YelpApi {

    @Headers("Authorization: Bearer FRnunewLKg1nM6GG-Hay6OxeyrDvonUl8Suowa42sQi6E8A53dHMz6zXXXlDhXfZ60HeNcx2V8-4oG_6YaXJctK8rcmjukMAXk86ahbPI8BgCK9iq2bdPQW0VabGXXYx")
    @GET("categories")
    Call<ListCategory> getCategories();
}
