package tongji.comgraph.openglnamecard;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.os.Bundle;
import android.util.Log;

public class NamecardRenderer implements Renderer {

	private Context context;
	
	private NamecardPlane namecard;
	private Bundle renderOptions;
	
	public float xrot;
	public float yrot;
	public float zrot;
	
	public float scale = 0.9f;
	
	private int fogMode[] = {
		GL10.GL_EXP,
		GL10.GL_EXP2,
		GL10.GL_LINEAR
	};
	private float fogColor[] = {
		0.3f, 0.3f, 0.3f, 1.0f
	};
	
	public NamecardRenderer(Context context, Bundle renderOptions) {
		this.context = context;
		this.renderOptions = renderOptions;
		
		namecard = new NamecardPlane(renderOptions);

	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		if (renderOptions.getBoolean("hasLight")) {
			gl.glEnable(GL10.GL_LIGHTING);
		}
		
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
		
		Log.v("color", "color " + renderOptions.getInt("bgId"));
		switch (renderOptions.getInt("bgId")) {
		case 0:
			gl.glClearColor(0f, 0f, 0f, 1.0f);
			break;
		case 1:
			gl.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
			break;
		case 2:
			gl.glClearColor(0.03f, 0.0f, 0.1875f, 1.0f);
			break;
		case 3:
			gl.glClearColor(0.0f, 0.62f, 1.0f, 1.0f);
			break;
		}
		
		
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glDisable(GL10.GL_DITHER);
		
		namecard.loadTexture(gl, context);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		gl.glFogx(GL10.GL_FOG_MODE, fogMode[1]); 
        gl.glFogfv(GL10.GL_FOG_COLOR, fogColor,0); 
        //gl.glHint(GL10.GL_FOG_HINT, GL10.GL_FOG_HINT); 
        gl.glFogf(GL10.GL_FOG_DENSITY, 0.3f); 
        gl.glFogf(GL10.GL_FOG_START, 1.0f);
        gl.glFogf(GL10.GL_FOG_END, 5.0f);
        gl.glEnable(GL10.GL_FOG); 
	}

}
