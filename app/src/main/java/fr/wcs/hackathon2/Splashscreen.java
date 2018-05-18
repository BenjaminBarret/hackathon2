package fr.wcs.hackathon2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splashscreen extends AppCompatActivity {

    private final int SPLASH_TIME_OUT = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        getSupportActionBar().hide();

        //ImageView splashScreen = findViewById(R.id.splashscreen);
        //Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        //splashScreen.startAnimation(animation);

        // chargement des infos de l'utilisateur
        Singleton singleton = Singleton.getsIntance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                Splashscreen.this.startActivity(intent);
                Splashscreen.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
