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
            serviceDao.insert(new Service("Jaya Automobile", "Kami menyediakan berbagai layanan reparasi mobil, di antaranya: ganti oli, tune up, spare part", 2, "auto-repair-shop", "Jl. Margonda Raya No. 475, Pondok Cina", "08.00 AM - 08.00 PM", "(021) 7714100", "https://res.cloudinary.com/adiltech20/image/upload/v1573300985/automobile-car-car-repair-classic-190537-min_evfusl.jpg", 200000, "car"));
            serviceDao.insert(new Service("Cantika Laundry", "Kami menyediakan berbagai layanan laundry", 3, "laundry", "Jl. Sukmajaya No. 200, Pondok Cina", "11.00 AM - 06.00 PM", "(021) 7714100", "https://res.cloudinary.com/adiltech20/image/upload/v1573300936/photo-of-woman-standing-inside-the-laundromat-2927523-min_it5ltj.jpg", 8000, "kg"));
            serviceDao.insert(new Service("Lanang Barberhop", "Kami dapat membuat anda lebih cantik", 4.6, "beauty-clinic", "Jl. Kartini No. 41, Pancoran Mas", "11.00 AM - 08.00 PM", "(021) 7714100", "https://res.cloudinary.com/adiltech20/image/upload/v1573300983/men-having-their-haircut-1813272-min_gea8v5.jpg", 20000, "person"));
            return null;
        }
    }
}
