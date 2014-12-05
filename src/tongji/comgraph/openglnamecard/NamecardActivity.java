package tongji.comgraph.openglnamecard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class NamecardActivity extends Activity {

	private NamecardGLSurfaceView glView;
	//private String photoPath;
	private Bundle renderOptions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		renderOptions = new Bundle();
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		//photoPath = bundle.getString("photo");

        Toast.makeText(NamecardActivity.this,bundle.getString("nameStr"), 1000).show();
		renderOptions.putString("photo", bundle.getString("photo"));
		renderOptions.putInt("bgId", bundle.getInt("bgId"));
		renderOptions.putInt("mistId", bundle.getInt("mistId"));
		renderOptions.putBoolean("hasLight", bundle.getBoolean("hasLight"));
		renderOptions.putString("nameStr", bundle.getString("nameStr"));
		renderOptions.putString("unitStr", bundle.getString("unitStr"));
		renderOptions.putString("phoneStr", bundle.getString("phoneStr"));
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        setContentView(R.layout.activity_namecard);
        
        glView = new NamecardGLSurfaceView(this, new NamecardRenderer(this, renderOptions));
        setContentView(glView);	
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.namecard, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
