package tongji.comgraph.openglnamecard;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private static Button buttonNext; 
	private static Button buttonPhoto;
	private static Context context;
	
	private String photoPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = MainActivity.this;
		buttonNext = (Button) this.findViewById(R.id.buttonNext);
		buttonNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, NamecardActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("photo", photoPath);
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
		
		buttonPhoto = (Button) this.findViewById(R.id.buttonPhoto);
		buttonPhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent, 0);
				
			}
		});
		
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch (requestCode) {
        case 0:
        	Uri uri = data.getData();
        	String[] proj = {MediaStore.Images.Media.DATA};
        	Cursor imageCursor = managedQuery(uri,proj,null,null,null);
        	int imageIndex = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        	imageCursor.moveToFirst();
        	String imagePath = imageCursor.getString(imageIndex);
        	buttonPhoto.setText(imagePath);
        	photoPath = imagePath;
        	
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
	
}
