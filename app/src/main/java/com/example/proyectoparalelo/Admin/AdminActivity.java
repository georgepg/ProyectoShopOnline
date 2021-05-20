package com.example.proyectoparalelo.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyectoparalelo.Admin.Categorias.CategoriaActivity;
import com.example.proyectoparalelo.LogIn.LoginActivity;
import com.example.proyectoparalelo.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminActivity extends AppCompatActivity {

    FloatingActionButton fabSalir;
    TextView txtsaludo;
    LinearLayout btnProducto,
            btnCategoria;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        txtsaludo = findViewById(R.id.txtsaludo);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        saludo();

        fabSalir = findViewById(R.id.fabSalir);
        fabSalir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Salir();
            }
        });

        //Glide.with(this).load("https://cdn.icon-icons.com/icons2/1769/PNG/512/4115235-exit-logout-sign-out_114030.png").into(fabSalir);
        //Glide.with(this).load("https://image.flaticon.com/icons/png/512/611/611406.png").into(fabSalir);
        Glide.with(this).load("https://image.flaticon.com/icons/png/512/56/56805.png").into(fabSalir);

        btnProducto = findViewById(R.id.btnProducto);
        btnCategoria = findViewById(R.id.btnCategoria);
        btnCategoria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToCategoria();
            }
        });

    }

    private void goToCategoria() {
        Intent intent = new Intent(AdminActivity.this, CategoriaActivity.class);
        startActivity(intent);
        finish();
    }

    private void Salir() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void saludo() {
        firestore.collection("Users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String name = documentSnapshot.getString("Fullname");
                    txtsaludo.setText(name);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {}
}