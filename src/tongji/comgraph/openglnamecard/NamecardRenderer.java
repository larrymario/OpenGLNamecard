package tongji.comgraph.openglnamecard;

import javax.microedition.khronos.egl.EGLConfig; 
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class NamecardRenderer implements Renderer {

	private Context context;
	
	private NamecardPlane namecard;
	
	public float xrot;
	public float yrot;
	public float zrot;
	
	public float scale = 0.8f;
	
	public NamecardRenderer(Context context, String photo) {
		this.context = context;
		
		namecard = new NamecardPlane(photo);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		gl.glScalef(scale > 0.1f ? scale : 0.1f, scale > 0.1f ? scale : 0.1f, scale > 0.1f ? scale : 0.1f);
		gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);
				
		namecard.draw(gl);
				
		//yrot += 1.0f;

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0)
		{
			height = 1;	
		}
		
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float)width/(float)height,0.1f,100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glShadeModel(GL10.GL_SMOOTH);
		
		gl.glClearColor(0f, 0f, 0f, 1.0f);
		
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glDisable(GL10.GL_DITHER);
		
		namecard.loadTexture(gl, context);
		gl.glEnable(GL10.GL_TEXTURE_2D);
	}

}
