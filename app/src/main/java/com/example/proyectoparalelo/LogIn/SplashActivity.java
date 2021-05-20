package com.example.proyectoparalelo.LogIn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectoparalelo.Admin.AdminActivity;
import com.example.proyectoparalelo.NavActivity;
import com.example.proyectoparalelo.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Animation anim_arriba = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation anim_abajo = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();

        TextView txtYasai_Splash = findViewById(R.id.txtYasai_Splash);
        TextView txtRestaurant_Splash = findViewById(R.id.txtRestaurant_Splash);
        ImageView logoYasai_Splash = findViewById(R.id.logoYasai_Splash);

        txtYasai_Splash.setAnimation(anim_abajo);
        txtRestaurant_Splash.setAnimation(anim_abajo);
        logoYasai_Splash.setAnimation(anim_arriba);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(SplashActivity.this);
                if (user != null /*|| account != null*/){

                    String Uid = user.getUid();
                    verificacionDeAdmin(Uid);

/*
                    Intent intent = new Intent(SplashActivity.this, NavActivity.class);
                    startActivity(intent);
                    finish();
*/
                }else{
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View,String>(logoYasai_Splash,"logoImageTrans");
                    pairs[1] = new Pair<View,String>(txtRestaurant_Splash,"textTrans");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,pairs);
                        startActivity(intent, options.toBundle());
                    }else{
                        startActivity(intent);
                        finish();
                    }

                }

            }
        },3000);
    }
    private void verificacionDeAdmin(String uid) {
        DocumentReference doc = firestore.collection("Users").document(uid);
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSucces"+documentSnapshot.getData());
                if(documentSnapshot.getString("isAdmin") != null){
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    finish();
                }

                if (documentSnapshot.getString("isUser") != null){
                    startActivity(new Intent(getApplicationContext(), NavActivity.class));
                    finish();
                }
            }
        });
    }
}