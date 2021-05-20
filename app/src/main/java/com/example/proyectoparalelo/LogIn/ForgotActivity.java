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

import com.example.proyectoparalelo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {

    MaterialButton btnRecuperar;
    TextInputEditText edtEmailForgot;
    TextView txtBienvenidoForgot, txtcontinuarForgot, txtVolverLogin;
    ImageView imgYasaiForgot;
    TextInputLayout tfemailForgot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        txtVolverLogin = findViewById(R.id.txtVolverLogin);
        txtVolverLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });
        btnRecuperar = findViewById(R.id.btnRecuperar);
        edtEmailForgot = findViewById(R.id.edtEmailForgot);
        btnRecuperar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                validate();
            }
        });

    }

    private void validate() {
        String email = edtEmailForgot.getText().toString().trim();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmailForgot.setError("Correo invalido");
            return;
        }
        enviarEmail(email);
    }

    private void enviarEmail(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email;
        auth.sendPasswordResetEmail(emailAddress).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotActivity.this,"Correo Enviado", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ForgotActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(ForgotActivity.this,"Correo Invalido", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        goToLogin();
    }

    private void goToLogin() {
        Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
        /*Pair[] pairs = new Pair[6];
        pairs[0] = new Pair<View, String> (imgYasaiForgot,"logoImageTrans");
        pairs[1] = new Pair<View, String> (txtBienvenidoForgot,"textTrans");
        pairs[2] = new Pair<View, String> (txtcontinuarForgot,"iniciaSesionTextTrans");
        pairs[3] = new Pair<View, String> (tfemailForgot,"emaiInputTextTrans");
        pairs[4] = new Pair<View, String> (btnRecuperar,"buttonTrans");
        pairs[5] = new Pair<View, String> (txtVolverLogin,"newUserTrans");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(ForgotActivity.this, pairs);
            startActivity(intent, options.toBundle());
        }else{
            */
            startActivity(intent);
            finish();
        //}

    }
}