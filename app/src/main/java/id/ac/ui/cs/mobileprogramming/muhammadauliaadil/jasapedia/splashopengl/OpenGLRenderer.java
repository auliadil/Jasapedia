package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.splashopengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private double blueValue = 1f;
    private double FLASH_DURATION = 1000;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0,0, (float) blueValue, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0,0, (float) blueValue, 0);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        blueValue = ((Math.sin(System.currentTimeMillis() * 2 * Math.PI / FLASH_DURATION) * 0.5) + 0.5);
    }
}
