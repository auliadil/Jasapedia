package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.daos.ServiceDao;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.databases.ServiceDatabase;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;

public class ServiceRepository {
    private ServiceDao serviceDao;
    private LiveData<List<Service>> allServices;

    public ServiceRepository(Application application) {
        ServiceDatabase database = ServiceDatabase.getInstance(application);
        serviceDao = database.serviceDao();
        allServices = serviceDao.getAllServices();
    }

    public void insert(Service service) {
        new InsertServiceAsyncTask(serviceDao).execute(service);
    }

    public void update(Service service) {
        new UpdateServiceAsyncTask(serviceDao).execute(service);
    }

    public void delete(Service service) {
        new DeleteServiceAsyncTask(serviceDao).execute(service);
    }

    public void deleteAllServices() {
        new DeleteAllServicesAsyncTask(serviceDao).execute();
    }

    public LiveData<List<Service>> getAllServices() {
        return allServices;
    }

    private static class InsertServiceAsyncTask extends AsyncTask<Service, Void, Void> {
        private ServiceDao serviceDao;

        private InsertServiceAsyncTask(ServiceDao serviceDao) {
            this.serviceDao = serviceDao;
        }

        @Override
        protected Void doInBackground(Service... services) {
            serviceDao.insert(services[0]);
            return null;
        }
    }

    private static class UpdateServiceAsyncTask extends AsyncTask<Service, Void, Void> {
        private ServiceDao serviceDao;

        private UpdateServiceAsyncTask(ServiceDao serviceDao) {
            this.serviceDao = serviceDao;
        }

        @Override
        protected Void doInBackground(Service... services) {
            serviceDao.update(services[0]);
            return null;
        }
    }

    private static class DeleteServiceAsyncTask extends AsyncTask<Service, Void, Void> {
        private ServiceDao serviceDao;

        private DeleteServiceAsyncTask(ServiceDao serviceDao) {
            this.serviceDao = serviceDao;
        }

        @Override
        protected Void doInBackground(Service... services) {
            serviceDao.delete(services[0]);
            return null;
        }
    }

    private static class DeleteAllServicesAsyncTask extends AsyncTask<Void, Void, Void> {
        private ServiceDao serviceDao;

        private DeleteAllServicesAsyncTask(ServiceDao serviceDao) {
            this.serviceDao = serviceDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            serviceDao.deleteAllServices();
            return null;
        }
    }
}
