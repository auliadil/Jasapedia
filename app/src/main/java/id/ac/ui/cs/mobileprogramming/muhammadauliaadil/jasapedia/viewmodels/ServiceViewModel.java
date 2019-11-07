package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.repositories.ServiceRepository;

public class ServiceViewModel extends AndroidViewModel {

    private ServiceRepository repository;
    private LiveData<List<Service>> allServices;
    private LiveData<Service> service;

    public ServiceViewModel(@NonNull Application application) {
        super(application);
        repository = new ServiceRepository(application);
        allServices = repository.getAllServices();
    }

    public void insert(Service service) {
        repository.insert(service);
    }

    public void update(Service service) {
        repository.update(service);
    }

    public void delete(Service service) {
        repository.delete(service);
    }

    public void deleteAllServices() {
        repository.deleteAllServices();
    }

    public LiveData<List<Service>> getAllServices() {
        return allServices;
    }
}
