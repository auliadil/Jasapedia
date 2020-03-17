package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.daos.ServiceDao;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;

@Database(entities = {Service.class}, version = 3, exportSchema = false)
public abstract class ServiceDatabase extends RoomDatabase {
    private static ServiceDatabase instance;

    public abstract ServiceDao serviceDao();

    public static synchronized ServiceDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ServiceDatabase.class, "service_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ServiceDao serviceDao;

        private PopulateDbAsyncTask(ServiceDatabase db) {
            serviceDao = db.serviceDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
