package com.example.proyectoparalelo.LogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoparalelo.Admin.AdminActivity;
import com.example.proyectoparalelo.NavActivity;
import com.example.proyectoparalelo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegistrarActivity extends AppCompatActivity {

    TextView txtBienvenidoRegistro, txtcontinuarRegistro, txtGoToIni;
    ImageView imgYasaiRegistrar;
    TextInputLayout tfemail,tfpassword;
    MaterialButton btnRegistrar;
    TextInputEditText edtName,edtEmail,edtPassword,edtPasswordConfirm,edtCode;
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        txtBienvenidoRegistro = findViewById(R.id.txtBienvenidoRegistro);
        txtcontinuarRegistro = findViewById(R.id.txtcontinuarRegistro);
        txtGoToIni = findViewById(R.id.txtGoToIni);
        imgYasaiRegistrar = findViewById(R.id.imgYasaiRegistrar);
        tfemail = findViewById(R.id.tfemailRegistro);
        tfpassword = findViewById(R.id.tfpasswordRegistro);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPasswordConfirm = findViewById(R.id.edtPasswordConfirm);
        edtCode = findViewById(R.id.edtCode);

        txtGoToIni.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                transitionBack();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

    }

    private void validate() {

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String Comfirmpassword = edtPasswordConfirm.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String code = edtCode.getText().toString().trim();

        if (code.isEmpty()){
            edtCode.setError(null);

            if (name.isEmpty()){
                edtName.setError("El nombre es requerido");
                return;
            }else{
                edtName.setError(null);
            }
            if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edtEmail.setError("Correo Invalido");
                return;
            }else{
                edtEmail.setError(null);
            }

            if (password.isEmpty() || password.length() < 6){
                edtPassword.setError("La contraseña debe tener 6 caracteres como minimo");
                return;
            }else if (!Pattern.compile("[1-9]").matcher(password).find()){
                edtPassword.setError("Debe llevar al menos un numero");
                return;
            }else{
                edtPassword.setError(null);
            }

            if(!Comfirmpassword.equals(password)){
                edtPasswordConfirm.setError("La contraseña no coincide");
                return;
            }else{
                registrarUser(email,password);
            }

        }else if (code.equals("Yasai")){
            edtCode.setError(null);

            if (name.isEmpty()){
                edtName.setError("El nombre es requerido");
                return;
            }else{
                edtName.setError(null);
            }

            if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edtEmail.setError("Correo Invalido");
                return;
            }else{
                edtEmail.setError(null);
            }

            if (password.isEmpty() || password.length() < 6){
                edtPassword.setError("La contraseña debe tener 6 caracteres como minimo");
                return;
            }else if (!Pattern.compile("[1-9]").matcher(password).find()){
                edtPassword.setError("Debe llevar al menos un numero");
                return;
            }else{
                edtPassword.setError(null);
            }

            if(!Comfirmpassword.equals(password)){
                edtPasswordConfirm.setError("La contraseña no coincide");
                return;
            }else{
                registrarAdmin(email,password);
            }

        }else {
            edtCode.setError("El codigo no es valido");
        }
        
    }

    private void registrarAdmin(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    DocumentReference document = firestore.collection("Users").document(user.getUid());
                    Map<String, Object> userInfo = new HashMap<>();

                    userInfo.put("Fullname",edtName.getText().toString());
                    userInfo.put("UserEmail",edtEmail.getText().toString());
                    userInfo.put("CodeAdmin", edtCode.getText().toString());
                        userInfo.put("isAdmin","1");
                        document.set(userInfo);
                        Intent intent = new Intent(RegistrarActivity.this, AdminActivity.class);
                        startActivity(intent);
                        finish();
                }else{
                    Toast.makeText(RegistrarActivity.this, "Fallo al registrarte", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void registrarUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    DocumentReference document = firestore.collection("Users").document(user.getUid());
                    Map<String, Object> userInfo = new HashMap<>();

                    userInfo.put("Fullname",edtName.getText().toString());
                    userInfo.put("UserEmail",edtEmail.getText().toString());
                    userInfo.put("CodeAdmin", edtCode.getText().toString());

                        userInfo.put("isUser","1");
                        document.set(userInfo);
                        Intent intent = new Intent(RegistrarActivity.this, NavActivity.class);
                        startActivity(intent);
                        finish();

                }else{
                    Toast.makeText(RegistrarActivity.this, "Fallo al registrarte", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        transitionBack();
    }

    public void transitionBack(){
        Intent intent = new Intent(RegistrarActivity.this, LoginActivity.class);
        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String> (imgYasaiRegistrar,"logoImageTrans");
        pairs[1] = new Pair<View, String> (txtBienvenidoRegistro,"textTrans");
        pairs[2] = new Pair<View, String> (txtcontinuarRegistro,"iniciaSesionTextTrans");
        pairs[3] = new Pair<View, String> (tfemail,"emaiInputTextTrans");
        pairs[4] = new Pair<View, String> (tfpassword,"passwordInputTextTrans");
        pairs[5] = new Pair<View, String> (btnRegistrar,"buttonTrans");
        pairs[6] = new Pair<View, String> (txtGoToIni,"newUserTrans");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(RegistrarActivity.this,pairs);
            startActivity(intent, options.toBundle());
        }else{
            startActivity(intent);
            finish();
        }

    }


}