package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.daos.BookingDao;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Booking;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;

@Database(entities = {Booking.class}, version = 3, exportSchema = false)
public abstract class BookingDatabase extends RoomDatabase {
    private static BookingDatabase instance;

    public abstract BookingDao bookingDao();

    public static synchronized BookingDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BookingDatabase.class, "booking_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BookingDao bookingDao;

        private PopulateDbAsyncTask(BookingDatabase db) {
            bookingDao = db.bookingDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
