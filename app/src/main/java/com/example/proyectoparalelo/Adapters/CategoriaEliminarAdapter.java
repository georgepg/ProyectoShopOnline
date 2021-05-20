package com.example.proyectoparalelo.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoparalelo.ElementosList.CategoriaElement;
import com.example.proyectoparalelo.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CategoriaEliminarAdapter extends FirestoreRecyclerAdapter<CategoriaElement, CategoriaEliminarAdapter.CategoriaEliminarHolder> {

    public CategoriaEliminarAdapter(@NonNull FirestoreRecyclerOptions<CategoriaElement> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoriaEliminarHolder holder, int position, @NonNull CategoriaElement model) {
        holder.name.setText(model.getNom());
    }

    @NonNull
    @Override
    public CategoriaEliminarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_eliminar_categoria, parent,false);
        return new CategoriaEliminarHolder(v);
    }

    class CategoriaEliminarHolder extends RecyclerView.ViewHolder {
        TextView name;

        public CategoriaEliminarHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtNameCategoriaElimnar);
        }
    }


}
