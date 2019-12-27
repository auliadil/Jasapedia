package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.splashopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class OpenGLView extends GLSurfaceView {

    public OpenGLView(Context context){
        super(context);
        init();
    }

    private void init() {
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(new OpenGLRenderer());
    }
}
