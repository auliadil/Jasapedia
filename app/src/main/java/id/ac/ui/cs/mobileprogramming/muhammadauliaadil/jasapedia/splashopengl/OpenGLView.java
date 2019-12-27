package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.splashopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class OpenGLView extends GLSurfaceView {
    public OpenGLView(Context context) {
        super(context);
        init();
    }

    public OpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
