package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.daos.BookingDao;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.databases.BookingDatabase;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Booking;

public class BookingRepository {
    private BookingDao bookingDao;
    private LiveData<List<Booking>> allBookings;

    public BookingRepository(Application application) {
        BookingDatabase database = BookingDatabase.getInstance(application);
        bookingDao = database.bookingDao();
        allBookings = bookingDao.getAllBookings();
    }

    public void insert(Booking booking) {
        new InsertBookingAsyncTask(bookingDao).execute(booking);
    }

    public void update(Booking booking) {
        new UpdateBookingAsyncTask(bookingDao).execute(booking);
    }

    public void delete(Booking booking) {
        new DeleteBookingAsyncTask(bookingDao).execute(booking);
    }

    public void deleteAllBookings() {
        new DeleteAllBookingsAsyncTask(bookingDao).execute();
    }

    public LiveData<List<Booking>> getAllBookings() {
        return allBookings;
    }

    private static class InsertBookingAsyncTask extends AsyncTask<Booking, Void, Void> {
        private BookingDao bookingDao;

        private InsertBookingAsyncTask(BookingDao bookingDao) {
            this.bookingDao = bookingDao;
        }

        @Override
        protected Void doInBackground(Booking... bookings) {
            bookingDao.insert(bookings[0]);
            return null;
        }
    }

    private static class UpdateBookingAsyncTask extends AsyncTask<Booking, Void, Void> {
        private BookingDao bookingDao;

        private UpdateBookingAsyncTask(BookingDao bookingDao) {
            this.bookingDao = bookingDao;
        }

        @Override
        protected Void doInBackground(Booking... bookings) {
            bookingDao.update(bookings[0]);
            return null;
        }
    }

    private static class DeleteBookingAsyncTask extends AsyncTask<Booking, Void, Void> {
        private BookingDao bookingDao;

        private DeleteBookingAsyncTask(BookingDao bookingDao) {
            this.bookingDao = bookingDao;
        }

        @Override
        protected Void doInBackground(Booking... bookings) {
            bookingDao.delete(bookings[0]);
            return null;
        }
    }

    private static class DeleteAllBookingsAsyncTask extends AsyncTask<Void, Void, Void> {
        private BookingDao bookingDao;

        private DeleteAllBookingsAsyncTask(BookingDao bookingDao) {
            this.bookingDao = bookingDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bookingDao.deleteAllBookings();
            return null;
        }
    }
}
