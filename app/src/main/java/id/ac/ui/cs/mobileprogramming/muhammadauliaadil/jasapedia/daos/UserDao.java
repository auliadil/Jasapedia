package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM users")
    void deleteAllUsers();

    @Query("SELECT * FROM users ORDER BY id ASC")
    LiveData<List<User>> getAllUsers();
}
