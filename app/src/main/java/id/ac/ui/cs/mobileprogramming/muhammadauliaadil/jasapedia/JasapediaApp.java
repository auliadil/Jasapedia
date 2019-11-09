package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia;

import android.app.Application;

import com.cloudinary.android.MediaManager;

public class JasapediaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MediaManager.init(this);
    }
}
