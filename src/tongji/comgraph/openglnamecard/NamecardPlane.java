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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.widget.Toast;

public class NamecardPlane {
	private FloatBuffer vertexBuffer;
	private FloatBuffer texBuffer1;
	private FloatBuffer texBuffer2;
	
	
	private FloatBuffer lightPositionBuffer;
	private FloatBuffer lightDiffuseBuffer;
	private FloatBuffer lightSpecularBuffer;

	
	private Bundle renderOptions;
	private String filename;
	private String unitStr = null;
	private String nameStr = null;
	private String phoneStr = null;
	
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
			0.0f, 1.0f,
			1.0f, 1.0f,
			0.0f, 0.0f,
			1.0f, 0.0f
		
	};
	
	private float[] lightPosition = { 0.0f, 1.0f, 2.0f, 1.0f };
	private float[] lightDiffuse = { 1.0f, 1.0f, 1.0f, 1.0f };
	private float[] lightSpecular = { 1.0f, 1.0f, 1.0f, 1.0f };

	
	
	int[] textureIDs = new int[1];
	
	Bitmap bmp1 = null;
	Bitmap bmp = null;
	
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
        
        ByteBuffer lightSpecularbyteBuffer = ByteBuffer
	    		.allocateDirect(lightSpecular.length * 4 * 6);
	    lightSpecularbyteBuffer.order(ByteOrder.nativeOrder());
	    lightSpecularBuffer = lightSpecularbyteBuffer.asFloatBuffer();
	    lightSpecularBuffer.put(lightSpecular);
	    lightSpecularBuffer.position(0);
	    
	    this.renderOptions = renderOptions;
	    this.filename = renderOptions.getString("photo");
	    this.unitStr = renderOptions.getString("unitStr");
	    this.nameStr = renderOptions.getString("nameStr");
	    this.phoneStr = renderOptions.getString("phoneStr");
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
	    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	    gl.glPopMatrix();
	    
	    gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer2);	//Using Reversed Setting
	    
	    gl.glPushMatrix();
	    gl.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
	    gl.glTranslatef(0.0f, 0.0f, 0.0f);
	    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp1, 0);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	    gl.glPopMatrix();
	    
	    
	    
	    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY); 
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glDisable(GL10.GL_CULL_FACE);
	}
	
	private Bitmap add3Bitmap(Bitmap first, Bitmap second, Bitmap third) {
        int width = Math.max(first.getWidth(), second.getWidth());
        int height = first.getHeight()+second.getHeight()+third.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight(), null);
        canvas.drawBitmap(third, 0,first.getHeight()+second.getHeight(), null);
        return result;
	}
	
	public void initBitmap() {
		Bitmap subBit1 = null;
		Bitmap subBit2 = null;
		Bitmap subBit3 = null;
        String mstrTitle = unitStr;
        String subTitle = nameStr;
        String telephone = phoneStr;
        
        subBit1 = Bitmap.createBitmap(256, 128, Bitmap.Config.ARGB_8888);
        subBit2 = Bitmap.createBitmap(256, 64, Bitmap.Config.ARGB_8888);
        subBit3 = Bitmap.createBitmap(256, 64, Bitmap.Config.ARGB_8888);
        
        Canvas canvasTemp = new Canvas(subBit1);
        canvasTemp.drawColor(Color.WHITE);
        Paint p = new Paint();
        String familyName = "kaishu";
        Typeface font = Typeface.create(familyName, Typeface.NORMAL);
        p.setColor(Color.RED);
        p.setTypeface(font);
        p.setTextSize(32);
        canvasTemp.drawText(mstrTitle, 30, 80, p);

        Canvas canvasTemp2 = new Canvas(subBit2);
        canvasTemp2.drawColor(Color.GRAY);
        Paint q = new Paint();
        q.setColor(Color.WHITE);
        q.setTextSize(24);
        canvasTemp2.drawText(subTitle, 80, 40, q);
        
        Canvas canvasTemp3 = new Canvas(subBit3);
        canvasTemp3.drawColor(Color.WHITE);
        Paint r = new Paint();
        r.setColor(Color.BLACK);
        r.setTextSize(22);
        canvasTemp3.drawText(telephone, 80, 40, r);
        
        bmp1 = add3Bitmap(subBit1,subBit2,subBit3);
    } 
	
	public void loadTexture(GL10 gl, Context context) {
		initBitmap();
		gl.glGenTextures(1, textureIDs, 0);
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);
		
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
		
		if (renderOptions.getBoolean("hasLight")) {
		      float mat_specular[] = {1.0f,2.0f,1.0f,1.0f};

		      float low_shininess [] = {5.0f};
			//Lightings
	        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBuffer); 
	       // gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuseBuffer);
		    gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 10.0f);
	        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, lightSpecularBuffer);
	        gl.glEnable(GL10.GL_LIGHT0); 
		}
	        
		
		
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
		
	}
}
