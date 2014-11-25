package tongji.comgraph.openglnamecard;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class NamecardPlane {
	private FloatBuffer vertexBuffer;
	private FloatBuffer texBuffer1;
	private FloatBuffer texBuffer2;
	
	private float[] vertices = {
		-3.5f, -2.0f, 0.0f,
		3.5f, -2.0f, 0.0f,
		-3.5f, 2.0f, 0.0f,
		3.5f, 2.0f, 0.0f
	};
	
	private float[] texturePos1 = {
		0.0f, 1.0f,
		1.0f, 1.0f,
		0.0f, 0.0f,
		1.0f, 0.0f
	};
	
	private float[] texturePos2 = {
		1.0f, 1.0f,
		0.0f, 1.0f,
		1.0f, 0.0f,
		0.0f, 0.0f
		
	};
	
	int[] textureIDs = new int[1];
	
	public NamecardPlane() {
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
	    vbb.order(ByteOrder.nativeOrder());
	    vertexBuffer = vbb.asFloatBuffer();
	    vertexBuffer.put(vertices);
	    vertexBuffer.position(0);
	    
	    ByteBuffer tbb1 = ByteBuffer.allocateDirect(texturePos1.length * 4);
	    tbb1.order(ByteOrder.nativeOrder());
	    texBuffer1 = tbb1.asFloatBuffer();
	    texBuffer1.put(texturePos1);
	    texBuffer1.position(0);
	    
	    ByteBuffer tbb2 = ByteBuffer.allocateDirect(texturePos2.length * 4);
	    tbb2.order(ByteOrder.nativeOrder());
	    texBuffer2 = tbb2.asFloatBuffer();
	    texBuffer2.put(texturePos2);
	    texBuffer2.position(0);
	}
	
	public void draw(GL10 gl) {
		gl.glFrontFace(GL10.GL_CCW);
	    gl.glEnable(GL10.GL_CULL_FACE);
	    gl.glCullFace(GL10.GL_BACK);
	    
	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer1);
	    
	    gl.glPushMatrix();
	    gl.glTranslatef(0.0f, 0.0f, 0.0f);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	    gl.glPopMatrix();
	    
	    gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer2);
	    
	    gl.glPushMatrix();
	    gl.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
	    gl.glTranslatef(0.0f, 0.0f, 0.0f);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	    gl.glPopMatrix();
	    
	    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY); 
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glDisable(GL10.GL_CULL_FACE);
	}
	
	public void loadTexture(GL10 gl, Context context) {
		gl.glGenTextures(1, textureIDs, 0);
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);
		
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		InputStream ins = context.getResources().openRawResource(R.drawable.jpg0001);
		Bitmap bmp;
		try {
			bmp = BitmapFactory.decodeStream(ins);
		} finally {
			try {
				ins.close();
			}catch(IOException e) {}
		}
		
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
	}
}
