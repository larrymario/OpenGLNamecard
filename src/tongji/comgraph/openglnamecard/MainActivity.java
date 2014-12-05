package tongji.comgraph.openglnamecard;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static Context context;
	
	private static Button buttonNext; 
	private static Button buttonPhoto;
	private static Spinner spinnerColor;
	private static Spinner spinnerMist;
	private static CheckBox checkBoxLight;
	
	private String photoPath = null;
	private int bgId = 0;
	private int mistId = 0;
	private boolean hasLight = false;
	
	private EditText nameText;
	private String unitStr = new String();
	private String nameStr = new String();
	private String phoneStr = new String();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = MainActivity.this;
		
		spinnerColor = (Spinner) this.findViewById(R.id.spinnerColor);
		String[] spinnerColorItems = this.getResources().getStringArray(R.array.color_spinner);
		ArrayAdapter<String> spinnerColorAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, spinnerColorItems);
		spinnerColor.setAdapter(spinnerColorAdapter);
		spinnerColor.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position,
					long id) {
				bgId = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
			
		});
		
		spinnerMist = (Spinner) this.findViewById(R.id.spinnerMist);
		String[] spinnerMistItems = this.getResources().getStringArray(R.array.mist_spinner);
		ArrayAdapter<String> spinnerMistAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, spinnerMistItems);
		spinnerMist.setAdapter(spinnerMistAdapter);
		spinnerMist.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position,
					long id) {
				mistId = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
			
		});
		
		buttonNext = (Button) this.findViewById(R.id.buttonNext);
		buttonNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				nameText = (EditText)findViewById(R.id.editText1);
				unitStr = nameText.getText().toString();
				nameText = (EditText)findViewById(R.id.editText2);
				nameStr = nameText.getText().toString();
				nameText = (EditText)findViewById(R.id.editText3);
				phoneStr = nameText.getText().toString();

				Intent intent = new Intent(context, NamecardActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("photo", photoPath);
				bundle.putInt("bgId", bgId);
				bundle.putInt("mistId", mistId);
				bundle.putBoolean("hasLight", hasLight);
				bundle.putString("unitStr", unitStr);
				bundle.putString("nameStr", nameStr);
				bundle.putString("phoneStr", phoneStr);
				intent.putExtras(bundle);
				if (photoPath == null) {
					Toast.makeText(context, "Œ¥’“µΩÕº∆¨£¨ π”√‘§…ËÕº∆¨.", Toast.LENGTH_LONG).show();
				}
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
		
		checkBoxLight = (CheckBox) this.findViewById(R.id.checkBoxLight);
		checkBoxLight.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				hasLight = isChecked;
			}
		});
		
	}

	@SuppressWarnings("deprecation")
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case 0:
        	if (data != null) {
	        	Uri uri = data.getData();
	        	String[] proj = {MediaStore.Images.Media.DATA};
	        	Cursor imageCursor = managedQuery(uri,proj,null,null,null);
	        	int imageIndex = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        	imageCursor.moveToFirst();
	        	String imagePath = imageCursor.getString(imageIndex);
	        	buttonPhoto.setText(imagePath);
	        	photoPath = imagePath;
        	}
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
	
}
