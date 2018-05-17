package fr.wcs.hackathon2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btCo = findViewById(R.id.btCo);
        Button btIns = findViewById(R.id.btInscription);
        final EditText mail = findViewById(R.id.et_Mail);
        final EditText pass = findViewById(R.id.et_Mdp);

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
