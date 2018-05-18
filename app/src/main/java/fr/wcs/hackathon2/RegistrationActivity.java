package fr.wcs.hackathon2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrationActivity extends AppCompatActivity {

    //Photo
    private Uri mUrlImage;
    private String mCurrentPhotoPath;
    private Uri mPhotoURI;

    //CONSTANT
    static final int SELECT_IMAGE = 0;
    private static final int REQUEST_TAKE_PHOTO = 11;
    private static final String ID_PROFIL = "idprofil";

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseUser mCurrentUser;
    private StorageReference mStorageRef;

    //Widgets
    private Button btInscript;
    private EditText name;
    private EditText prenom;
    private EditText mail;
    private EditText ville;
    private EditText mdp;
    private Spinner spinner;
    private ImageView profile;
    private ProgressBar mProgressBarLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mRef = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        initWidgets();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            showPickImageDialog();

            }


        });

        btInscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            createAccount();

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

    public void createAccount() {

        String condition1 = name.getText().toString();
        String condition2 = prenom.getText().toString();
        String condition3 = mail.getText().toString();
        String condition4 = ville.getText().toString();
        String condition5 = mdp.getText().toString();

        if (TextUtils.isEmpty(condition3) || TextUtils.isEmpty(condition5) || TextUtils.isEmpty(condition1) || TextUtils.isEmpty(condition2) || TextUtils.isEmpty(condition4)) {

            Toast.makeText(RegistrationActivity.this, "Please, fill all fields !", Toast.LENGTH_SHORT).show();
        }
        else if(mdp.length() < 6) {

            Toast.makeText(RegistrationActivity.this, "Password 6 character min.", Toast.LENGTH_SHORT).show();
        }
        else {

            mProgressBarLoading.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(condition3, condition5).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        mProgressBarLoading.setVisibility(View.GONE);
                        Toast.makeText(RegistrationActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        String mailValue = mail.getText().toString().trim();
                        String nameValue = name.getText().toString();
                        String prenomValue = prenom.getText().toString();
                        String villeValue = ville.getText().toString();
                        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                        final String userID = mCurrentUser.getUid();
                        mRef.child(userID).child("Profil").child("id").setValue(userID);
                        mRef.child(userID).child("Profil").child("nom").setValue(nameValue);
                        mRef.child(userID).child("Profil").child("prenom").setValue(prenomValue);
                        mRef.child(userID).child("Profil").child("ville").setValue(villeValue);
                        mRef.child(userID).child("Profil").child("mail").setValue(mailValue);

                        if (mPhotoURI == null) {
                            mRef.child(userID).child("Profil").child("photouser").setValue("1");
                        } else {
                            //enregistre dans firebaseStorage
                            StorageReference riverRef = mStorageRef.child("PhotoUser").child(mPhotoURI.getLastPathSegment());;

                            riverRef.putFile(mPhotoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    String url = downloadUrl.toString();

                                    mRef.child(userID).child("Profil").child("photouser").setValue(url);
                                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                    mProgressBarLoading.setVisibility(View.GONE);
                                    Toast.makeText(RegistrationActivity.this, "OK", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    Toast.makeText(RegistrationActivity.this,"Upload is " + progress + "% done", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegistrationActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        //SaveSharedPreference.setUserName(LoginActivity.this, mail);
                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }

                }
            });
        }
    }

    public void initWidgets() {

        btInscript = findViewById(R.id.btCo);
        name = findViewById(R.id.etLast);
        prenom = findViewById(R.id.etFirst);
        mail = findViewById(R.id.etMailAdress);
        ville = findViewById(R.id.etCity);
        mdp = findViewById(R.id.etPass);
        spinner = findViewById(R.id.spinner);
        profile = findViewById(R.id.ivProfile);
        mProgressBarLoading = findViewById(R.id.progress_bar);
    }

    /*
    -----------------------------------AvatarMethod-------------------------------------------------
     */

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
                mPhotoURI = FileProvider.getUriForFile(this,
                        "fr.wcs.hackathon2.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
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

    private void showPickImageDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(RegistrationActivity.this);
        builderSingle.setTitle("Choisissez une option :");

        final String [] items = new String[] {"Gallerie", "Appareil photo"};
        final Integer[] icons = new Integer[] {R.drawable.gallery, R.drawable.camera_moto_icon};
        ListAdapter adapter = new ArrayAdapterWithIcon(RegistrationActivity.this, items, icons);

        builderSingle.setNegativeButton(
                "Annuler",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto, SELECT_IMAGE);
                                break;

                            case 1:
                                dispatchTakePictureIntent();
                                break;
                        }

                    }
                });
        builderSingle.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case SELECT_IMAGE:
                if(resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    mPhotoURI = selectedImage;
                    Glide.with(RegistrationActivity.this).load(mPhotoURI).into(profile);
                }
                break;
            case REQUEST_TAKE_PHOTO:
                if(resultCode == RESULT_OK) {
                    Glide.with(RegistrationActivity.this).load(mPhotoURI).into(profile);
                }
                break;
        }
    }

}