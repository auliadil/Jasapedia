package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;

@Dao
public interface ServiceDao {

    @Insert
    void insert(Service service);

    @Update
    void update(Service service);

    @Delete
    void delete(Service service);

    @Query("DELETE FROM services")
    void deleteAllServices();

    @Query("SELECT * FROM services ORDER BY name DESC")
    LiveData<List<Service>> getAllServices();
}
