package fr.wcs.hackathon2;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrationActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 567;
    private Uri mFileUri = null;
    private String mCurrentPhotoPath;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final Button btInscript = findViewById(R.id.btInscription);
        final EditText name = findViewById(R.id.etLast);
        final EditText prenom = findViewById(R.id.etFirst);
        final EditText mail = findViewById(R.id.etMailAdress);
        final EditText ville = findViewById(R.id.etCity);
        final EditText mdp = findViewById(R.id.etPass);
        final Spinner spinner = findViewById(R.id.spinner);
        final ImageView profile = findViewById(R.id.ivProfile);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakePictureIntent();

            }


        });

        btInscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String condition1 = name.getText().toString();
                String condition2 = prenom.getText().toString();
                String condition3 = mail.getText().toString();
                String condition4 = ville.getText().toString();
                String condition5 = mdp.getText().toString();

                if (condition1.equals("") || condition2.equals("") || condition3.equals("") || condition4.equals("") || condition5.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Veuillez renseigner les champs obligatoires", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                }


            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



            }

            @Override
            public void onNothingSelected (AdapterView<?> adapterView){

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {


            final ImageView profile = findViewById(R.id.ivProfile);
            Glide.with(this).load(mFileUri) .into(profile);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                mFileUri = FileProvider.getUriForFile(this,
                        "com.plapl.bookingartist.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
}