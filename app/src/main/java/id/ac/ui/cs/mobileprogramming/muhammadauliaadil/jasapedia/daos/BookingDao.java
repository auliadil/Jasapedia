package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Booking;

@Dao
public interface BookingDao {

    @Insert
    void insert(Booking booking);

    @Update
    void update(Booking booking);

    @Delete
    void delete(Booking booking);

    @Query("DELETE FROM bookings")
    void deleteAllBookings();

    @Query("SELECT * FROM bookings ORDER BY id ASC")
    LiveData<List<Booking>> getAllBookings();
}
