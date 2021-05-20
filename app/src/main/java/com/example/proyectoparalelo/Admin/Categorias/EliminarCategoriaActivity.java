package com.example.proyectoparalelo.Admin.Categorias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proyectoparalelo.Adapters.CategoriaEliminarAdapter;
import com.example.proyectoparalelo.ElementosList.CategoriaElement;
import com.example.proyectoparalelo.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class EliminarCategoriaActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView rcvCategoriaEliminar;
    CollectionReference Categoriaref;
    CategoriaEliminarAdapter adapter;
    //FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_categoria);

        firestore = FirebaseFirestore.getInstance();
        rcvCategoriaEliminar = findViewById(R.id.rcvCategoriaEliminar);
        Categoriaref = firestore.collection("Categorias");

        cargarRecyclerview();
        /*
        Query query = firestore.collection("Categorias");
        FirestoreRecyclerOptions<CategoriaElement> options = new FirestoreRecyclerOptions.Builder<CategoriaElement>()
                .setQuery(query, CategoriaElement.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<CategoriaElement, CategoriasViewHolder>(options){

            @NonNull
            @Override
            public CategoriasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_eliminar_categoria,parent,false);
                return new CategoriasViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CategoriasViewHolder categoriasViewHolder, int i, @NonNull CategoriaElement categoriaElement) {
            categoriasViewHolder.name.setText(categoriaElement.getNom());

            }
        };

        rcvCategoriaEliminar.setHasFixedSize(true);
        rcvCategoriaEliminar.setLayoutManager(new LinearLayoutManager(this));
        rcvCategoriaEliminar.setAdapter(adapter);
        */

    }

    private void cargarRecyclerview() {
        Query query = Categoriaref.orderBy("priority",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<CategoriaElement> options = new FirestoreRecyclerOptions.Builder<CategoriaElement>()
                .setQuery(query, CategoriaElement.class)
                .build();

        adapter = new CategoriaEliminarAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.rcvCategoriaEliminar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CategoriaActivity.class);
        startActivity(intent);
        finish();
    }

    /*
    private class CategoriasViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        public CategoriasViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.txtNameCategoriaElimnar);
        }

    }
    */

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


}