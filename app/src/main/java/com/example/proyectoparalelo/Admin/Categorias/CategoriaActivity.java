package com.example.proyectoparalelo.Admin.Categorias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.proyectoparalelo.Admin.AdminActivity;
import com.example.proyectoparalelo.R;

public class CategoriaActivity extends AppCompatActivity {

    LinearLayout btnAñadir, btnEditar, btnEliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        btnEditar = findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToEdit();
            }
        });

        btnAñadir = findViewById(R.id.btnAñadir);
        btnAñadir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToAñadir();
            }
        });

        btnEliminar = findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToEliminar();
            }
        });
    }

    private void goToEliminar() {
        Intent intent = new Intent(CategoriaActivity.this, EliminarCategoriaActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToAñadir() {
        Intent intent = new Intent(CategoriaActivity.this, AnyadirCategoriaActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToEdit() {
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
        finish();
    }
}