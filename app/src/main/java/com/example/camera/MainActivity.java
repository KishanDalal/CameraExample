package com.example.camera;

import java.io.Console;
import java.io.File;
import java.net.URI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import static android.support.v4.app.ActivityCompat.startActivityForResult;
import static android.view.View.*;
import static com.example.camera.R.id.EditText_FirstName;
import static com.example.camera.R.id.ImageView_Avatar;
import static com.example.camera.R.id.image;

public class MainActivity extends BaseActivity {

 public static String SETTINGS_PREFS = "SETTINGS PREFS";
 public static String SETTINGS_PREFS_FirstName = "First Name";
 public static String SETTINGS_PREFS_Avatar = "Avatar";
 public static final int TAKE_AVATAR_CAMERA_REQUEST = 1;

SharedPreferences settings;

Button btn;

 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
     settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
     btn = (Button) findViewById(R.id.Button_Submit);
     //initImageView(SETTINGS_PREFS_Avatar, R.id.ImageView_Avatar);
     //TextView text = (TextView) findViewById(R.id.TextView_FirstNameP2);

  initAvatar();

 }


 private void initAvatar() {
  ImageButton avatarButton = (ImageButton) findViewById(R.id.ImageButton_Picture);



     if(settings.contains(SETTINGS_PREFS_AVATAR))
     {

         String avatarUri = settings.getString(SETTINGS_PREFS_AVATAR, "");

         if(avatarUri != "")
         {
             Uri imageUri = Uri.parse(avatarUri);
             avatarButton.setImageURI(imageUri);
         }
         else
         {
             avatarButton.setImageResource(R.drawable.avatar);
         }

     }
     else
     {
         avatarButton.setImageResource(R.drawable.avatar);
     }



  avatarButton.setOnClickListener(new ChooseCameraListener());
 }


 class ChooseCameraListener implements View.OnClickListener {

  @Override
  public void onClick(View view) {
   Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

   startActivityForResult(Intent.createChooser(pictureIntent, "Take your photo"), TAKE_AVATAR_CAMERA_REQUEST);
  }


 }

 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
   switch (requestCode)
   {
    case TAKE_AVATAR_CAMERA_REQUEST:
     if (resultCode == Activity.RESULT_CANCELED)
     {

     }
     else if (resultCode == Activity.RESULT_OK)
     {
       Bitmap cameraPic = (Bitmap) data.getExtras().get("data");
       if(cameraPic != null)
       {
            try
            {
              saveAvatar(cameraPic);
            }
            catch (Exception e)
            {

            }
       } // End of if

     } // End of elseif
   } // End of switch
 }

 private void saveAvatar(Bitmap avatar) {
   String avatarFileName = "avatar.jpg";
   try
   {
     avatar.compress(Bitmap.CompressFormat.JPEG, 100, openFileOutput(avatarFileName, MODE_PRIVATE));



   }
   catch (Exception e)
   {

   }
     Uri avatarUri = Uri.fromFile(new File(this.getFilesDir(), avatarFileName));

     ImageView avatarButton = (ImageButton) findViewById(R.id.ImageButton_Picture);

     avatarButton.setImageURI(null);
     avatarButton.setImageURI(avatarUri);

     EditText name = (EditText) findViewById(R.id.EditText_FirstName);

     SharedPreferences.Editor editor = settings.edit();
     editor.putString(SETTINGS_PREFS_Avatar, avatarUri.getPath());
     editor.putString(SETTINGS_PREFS_FirstName, avatarUri.getPath());
     editor.commit();

 }




 public void Contiune(View view)
 {
     EditText edit = (EditText) findViewById(R.id.EditText_FirstName);
     String name = edit.getText().toString();

     ImageView img = (ImageView) findViewById(R.id.ImageButton_Picture);
     Drawable my = img.getDrawable();

     Intent intent = new Intent(MainActivity.this, Page2Activity.class);
     intent.putExtra("name", name);
     //intent.putExtra("image", my);
     startActivity(intent);
 }


}// Base Activity Close