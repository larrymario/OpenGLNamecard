package tongji.comgraph.openglnamecard;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.os.Bundle;

public class NamecardPlane {
	private FloatBuffer vertexBuffer;
	private FloatBuffer texBuffer1;
	private FloatBuffer texBuffer2;
	
	private FloatBuffer matSpecularBuffer;
	private FloatBuffer matShinenessBuffer;
	
	private FloatBuffer lightPositionBuffer;
	private FloatBuffer lightDiffuseBuffer;

	
	private Bundle renderOptions;
	private String filename;
	
	private float[] vertices = {
		-3.2f, -1.8f, 0.0f,
		3.2f, -1.8f, 0.0f,
		-3.2f, 1.8f, 0.0f,
		3.2f, 1.8f, 0.0f
	};
	private float[] texturePos1 = {
		0.0f, 1.0f,
		1.0f, 1.0f,
		0.0f, 0.0f,
		1.0f, 0.0f
	};
	private float[] texturePos2 = {		//Horizontally Reversed Texture
		1.0f, 1.0f,
		0.0f, 1.0f,
		1.0f, 0.0f,
		0.0f, 0.0f
		
	};
	
	private float[] lightPosition = { 0.0f, 0.0f, 2.0f, 1.0f };
	private float[] lightDiffuse = { 1.0f, 1.0f, 1.0f, 1.0f };
	
	
	int[] textureIDs = new int[1];
	
	public NamecardPlane(Bundle renderOptions) {
		
		
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

	    //Lightings
	    ByteBuffer lightDiffusebyteBuffer = ByteBuffer
	    		.allocateDirect(lightDiffuse.length * 4 * 6);
	    lightDiffusebyteBuffer.order(ByteOrder.nativeOrder());
	    lightDiffuseBuffer = lightDiffusebyteBuffer.asFloatBuffer();
	    lightDiffuseBuffer.put(lightDiffuse);
	    lightDiffuseBuffer.position(0);
        
        ByteBuffer lightPositionbyteBuffer = ByteBuffer
                .allocateDirect(lightPosition.length * 4 * 6);
        lightPositionbyteBuffer.order(ByteOrder.nativeOrder());
        lightPositionBuffer = lightPositionbyteBuffer.asFloatBuffer();
        lightPositionBuffer.put(lightPosition);
        lightPositionBuffer.position(0);
	    
	    this.renderOptions = renderOptions;
	    this.filename = renderOptions.getString("photo");
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
	    
	    gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer2);	//Using Reversed Setting
	    
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
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
		
		if (renderOptions.getBoolean("hasLight")) {
			//Lightings
	        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBuffer); 
	        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, lightDiffuseBuffer);
	        gl.glEnable(GL10.GL_LIGHT0); 
		}
	        
		Bitmap bmp = null;
		if (filename != null) {
			FileInputStream ins = null;
			File file = new File(renderOptions.getString("photo"));
			try {
				ins = new FileInputStream(file);
				//bmp = BitmapFactory.decodeFile(filename);
				bmp = BitmapFactory.decodeStream(ins);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					ins.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		else {
			InputStream ins = null;
			try {
				ins = context.getResources().openRawResource(R.drawable.jpg0031);
				bmp = BitmapFactory.decodeStream(ins);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					ins.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
	}
}
