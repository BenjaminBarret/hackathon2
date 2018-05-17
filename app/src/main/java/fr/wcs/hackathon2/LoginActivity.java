package fr.wcs.hackathon2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

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
    private Button btCo;
    private Button btIns;
    private EditText mail;
    private EditText pass;
    private ProgressBar mProgressBarLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btCo = findViewById(R.id.btCo);
        btIns = findViewById(R.id.btInscription);
        mail = findViewById(R.id.et_Mail);
        pass = findViewById(R.id.et_Mdp);
        mProgressBarLoading = findViewById(R.id.progress_bar_load);

        btCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String condition1 = mail.getText().toString();
                String condition2 = pass.getText().toString();
                if (condition1.equals("") || condition2.equals("")){
                    Toast.makeText(LoginActivity.this, "Veuillez renseigner les champs obligatoires", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent co = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(co);
                }
            }
        });

        btIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inscription = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(inscription);
            }
        });


    }
}
