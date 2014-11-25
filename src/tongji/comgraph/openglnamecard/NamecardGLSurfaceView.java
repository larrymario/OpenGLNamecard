package tongji.comgraph.openglnamecard;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class NamecardGLSurfaceView extends GLSurfaceView {

	float mPreviousX = 0.0f;
	float mPreviousY = 0.0f;
	NamecardRenderer renderer;
	
	public NamecardGLSurfaceView(Context context) {
		super(context);
	}
	
	public NamecardGLSurfaceView(Context context, NamecardRenderer renderer) {
		super(context);
		this.setRenderer(renderer);
		this.renderer = renderer;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dx = x - mPreviousX;
			float dy = y - mPreviousY;
			
			renderer.yrot += 0.5f * dx;
			requestRender();
			
			mPreviousX = x;
			mPreviousY = y;
			break;
		case MotionEvent.ACTION_DOWN:
			mPreviousX = e.getX();
			mPreviousY = e.getY();
		}
		
		
		
		return true;
	}
}
