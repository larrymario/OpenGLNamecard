package tongji.comgraph.openglnamecard;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.util.DisplayMetrics;
import android.util.Log;

public class NamecardGLSurfaceView extends GLSurfaceView {

	private int mScreenWidth;
	private int mScreenHeight;
	private float mPreviousX = 0.0f;
	private float mPreviousY = 0.0f;
	private float mCurrentRate = 1.0f;
	private float mOldRate = 1.0f;
	private boolean mIsFirst = true;
	private float mOriginalLength;
	private float mCurrentLength;
	
	NamecardRenderer renderer;
	
	public NamecardGLSurfaceView(Context context) {
		super(context);
		init(context);
	}
	
	public NamecardGLSurfaceView(Context context, NamecardRenderer renderer) {
		super(context);
		this.setRenderer(renderer);
		this.renderer = renderer;
		init(context);
	}
	
	private void init(Context context) {
		DisplayMetrics display = context.getResources().getDisplayMetrics();
		mScreenWidth = display.widthPixels;
		mScreenHeight = display.heightPixels;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		if (e.getPointerCount() == 1) {
			switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mPreviousX = e.getX();
				mPreviousY = e.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				float dx = x - mPreviousX;
				float dy = y - mPreviousY;
				
				renderer.yrot += 0.5f * dx;
				requestRender();
				
				mPreviousX = x;
				mPreviousY = y;	
				break;
			case MotionEvent.ACTION_UP:
				mOldRate = renderer.scale;
				break;
			}
		}
		if (e.getPointerCount() == 2) {
			switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mIsFirst = false;
				break;
			case MotionEvent.ACTION_MOVE:
				if (mIsFirst) {
					mOriginalLength = (float) Math.sqrt(Math.pow(e.getX(0)
							- e.getX(1), 2)
							+ Math.pow(e.getY(0) - e.getY(1), 2));
					mIsFirst = false;
				} else {
					mCurrentLength = (float) Math.sqrt(Math.pow(e.getX(0)
							- e.getX(1), 2)
							+ Math.pow(e.getY(0) - e.getY(1), 2));
					mCurrentRate = (float) (mOldRate * (mCurrentLength / mOriginalLength));
					renderer.scale = mCurrentRate;
					requestRender();
				}
				break;				
			}
			
				
		}
		
		
		return true;
	}
}
