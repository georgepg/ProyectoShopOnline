package com.example.proyectoparalelo.Admin.Categorias;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoparalelo.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class AnyadirCategoriaActivity extends AppCompatActivity {

    MaterialButton btnRegistrar, btnSubirFoto;
    TextView txtTituloCategoria;
    FirebaseFirestore firestore;
    TextInputEditText edtNombre;
    ImageView imgFoto;
    Uri imgUri;

    private static final int Gallery_Intent = 1;
    StorageReference mStorage;
    FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_categoria);

        edtNombre = findViewById(R.id.edtNombre);
        txtTituloCategoria = findViewById(R.id.txtTituloCategoria);
        imgFoto = findViewById(R.id.imgFoto);
        storage = FirebaseStorage.getInstance();
        mStorage = storage.getReference();

        /*
        String text = txtTituloCategoria.getText().toString();
        SpannableString ss = new SpannableString(text);
        UnderlineSpan span = new UnderlineSpan();
        ss.setSpan(span, 0,15 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtTituloCategoria.setText(ss);
        */
        firestore = FirebaseFirestore.getInstance();

        btnSubirFoto = findViewById(R.id.btnSubirFoto);
        btnSubirFoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            cargarImagen();
            }
        });
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String nombre = edtNombre.getText().toString().trim();
                validar(nombre);
                SubirImagen();
            }
        });

    }

    private void cargarImagen() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData() != null){
            imgUri = data.getData();
            imgFoto.setImageURI(imgUri);
        }
        String nombre = edtNombre.getText().toString().trim();
        if (nombre.isEmpty()){
            edtNombre.setError("La casilla esta vacia");
        }

    }

    private void SubirImagen() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Subiendo imagen");
        pd.show();

        final String ramdomKey = UUID.randomUUID().toString();
        StorageReference riversRef = mStorage.child(edtNombre.getText().toString().trim());

        riversRef.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),"Datos Subidos correctamente", Snackbar.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Fallo al Subir", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPrecent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Porciento: "+ (int) progressPrecent + "%");
            }
        });
    }


    private void subirdatos(String nombre) {
        Map<String, Object> doc = new HashMap<>();
        String id = "COD"+nombre.toUpperCase();
        doc.put("id",id);
        doc.put("Titulo", nombre);

        firestore.collection("Categorias").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                edtNombre.setText("");
                Toast.makeText(AnyadirCategoriaActivity.this,"Subiendo datos...", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AnyadirCategoriaActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    private void validar(String nombre) {
        if (nombre.isEmpty()){
            edtNombre.setError("La casilla esta vacia");
        }else{
            subirdatos(nombre);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CategoriaActivity.class);
        startActivity(intent);
       finish();
    }
}