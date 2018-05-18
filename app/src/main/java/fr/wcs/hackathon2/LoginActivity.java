package fr.wcs.hackathon2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LoginActivity extends AppCompatActivity {

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

    Intent mGoToMainActivity;

    private static final String ID_PROFIL = "idprofil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btCo = findViewById(R.id.btCo);
        btIns = findViewById(R.id.btInscription);
        mail = findViewById(R.id.et_Mail);
        pass = findViewById(R.id.et_Mdp);
        mProgressBarLoading = findViewById(R.id.progress_bar_load);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mRef = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mGoToMainActivity = new Intent(LoginActivity.this, MainActivity.class);

        btCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
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

        /*
    -------------------------------SignIn---------------------------------------------
     */

    public void signIn() {

        String mailValue = mail.getText().toString().trim();
        String passValue = pass.getText().toString().trim();
        if (TextUtils.isEmpty(mailValue) || TextUtils.isEmpty(passValue)) {

            Toast.makeText(LoginActivity.this, "Please, fill all fields !", Toast.LENGTH_SHORT).show();
        } else {

            mProgressBarLoading.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(mailValue, passValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String userID = mCurrentUser.getUid();

                        mGoToMainActivity.putExtra(ID_PROFIL, userID);

                        //String mailString = mail.getText().toString().trim();
                        //SaveSharedPreference.setUserName(LoginActivity.this, mailString);

                        LoginActivity.this.startActivity(mGoToMainActivity);
                        mProgressBarLoading.setVisibility(View.GONE);

                        finish();


                    } else {
                        mProgressBarLoading.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Incorrect password or mail.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
