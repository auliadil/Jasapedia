package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Booking;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.repositories.BookingRepository;

public class BookingViewModel extends AndroidViewModel {

    private BookingRepository repository;
    private LiveData<List<Booking>> allBookings;
    private LiveData<Booking> booking;

    public BookingViewModel(@NonNull Application application) {
        super(application);
        repository = new BookingRepository(application);
        allBookings = repository.getAllBookings();
    }

    public void insert(Booking booking) {
        repository.insert(booking);
    }

    public void update(Booking booking) {
        repository.update(booking);
    }

    public void delete(Booking booking) {
        repository.delete(booking);
    }

    public void deleteAllBookings() {
        repository.deleteAllBookings();
    }

    public LiveData<List<Booking>> getAllBookings() {
        return allBookings;
    }
}
