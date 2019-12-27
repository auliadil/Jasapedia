package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.splashopengl.OpenGLRenderer;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.splashopengl.OpenGLView;

public class SplashScreenActivity extends AppCompatActivity {

    private OpenGLView gLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        gLView = findViewById(R.id.openGL);

//        new android.os.Handler().postDelayed(
//            new Runnable() {
//                public void run() {
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(intent);
//                }
//            }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gLView.onPause();
    }
}
