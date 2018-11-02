package com.example.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.camera.MainActivity.SETTINGS_PREFS_Avatar;


public class Page2Activity extends BaseActivity {

	SharedPreferences settings;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page2);

		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);

		initImageView(SETTINGS_PREFS_Avatar, R.id.ImageView_Avatar);

		String value;
		Bundle bundle = getIntent().getExtras();
			value = bundle.getString("name");

		TextView textView = (TextView)findViewById(R.id.TextView_FirstNameP2);
		textView.setText(value);

    }

	public  void initImageView(String key, int id)
	{
		if(!settings.contains(key))return;

		ImageView imageView = (ImageView) findViewById(id);
		String urlString = settings.getString(key, "");



		if(urlString.equals("")) return;
		Uri imageUrl = Uri.parse(urlString);
		imageView.setImageURI(imageUrl);
	}

}
