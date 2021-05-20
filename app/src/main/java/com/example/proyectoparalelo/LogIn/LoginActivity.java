package com.example.proyectoparalelo.LogIn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoparalelo.Admin.AdminActivity;
import com.example.proyectoparalelo.NavActivity;
import com.example.proyectoparalelo.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICATION";
    private final static int NOTIFICATION_ID = 0;
    MaterialButton btninicioSesion;
    ImageView imgYasaiLogin;
    TextInputLayout tfemailLogin,tfpasswordLogin;
    TextView txtBienvenidoLogin,txtcontinuarLogin,txtnuevoUsuario,txtPasswordForgot;
    TextInputEditText edtEmailLogin,edtPasswordLogin;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    SignInButton signInButton;
    GoogleSignInClient googleSignInClient;
    public static final int RC_SIGN_IN = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imgYasaiLogin = findViewById(R.id.imgYasaiLogin);
        txtBienvenidoLogin = findViewById(R.id.txtBienvenidoLogin);
        txtPasswordForgot = findViewById(R.id.txtPasswordForgot);
        btninicioSesion=findViewById(R.id.btninicioSesion);
        txtcontinuarLogin = findViewById(R.id.txtcontinuarLogin);
        tfemailLogin = findViewById(R.id.tfemailLogin);
        tfpasswordLogin = findViewById(R.id.tfpasswordLogin);
        txtnuevoUsuario = findViewById(R.id.txtnuevoUsuario);
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        txtPasswordForgot.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                goToPasswordForgot();
            }
        });
        signInButton = findViewById(R.id.btnLoginGoogle);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           LogInGoogle();
            }
        });
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        txtnuevoUsuario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            GoRegistrar();
            }
        });
        btninicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

    }

    private void goToPasswordForgot() {
        Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
        Pair[] pairs = new Pair[6];
        pairs[0] = new Pair<View, String> (imgYasaiLogin,"logoImageTrans");
        pairs[1] = new Pair<View, String> (txtBienvenidoLogin,"textTrans");
        pairs[2] = new Pair<View, String> (txtcontinuarLogin,"iniciaSesionTextTrans");
        pairs[3] = new Pair<View, String> (tfemailLogin,"emaiInputTextTrans");
        pairs[4] = new Pair<View, String> (btninicioSesion,"buttonTrans");
        pairs[5] = new Pair<View, String> (txtnuevoUsuario,"newUserTrans");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
            startActivity(intent, options.toBundle());
        }else{
            startActivity(intent);
            finish();
        }

    }

    private void LogInGoogle() {
        Intent SigInintent = googleSignInClient.getSignInIntent();
        startActivityForResult(SigInintent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            }catch (ApiException e){
                Toast.makeText(LoginActivity.this, "Fallo Google", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, NavActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Fallo en iniciar sesion", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void validate() {
        String email = edtEmailLogin.getText().toString().trim();
        String password = edtPasswordLogin.getText().toString().trim();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmailLogin.setError("Correo Invalido");
            return;
        }else{
            edtEmailLogin.setError(null);
        }
        if (password.isEmpty() || password.length() < 6){
            edtPasswordLogin.setError("La contraseña debe tener 6 caracteres como minimo");
            return;
        }else if (!Pattern.compile("[1-9]").matcher(password).find()){
            edtPasswordLogin.setError("Debe llevar al menos un numero");
            return;
        }else{
            edtPasswordLogin.setError(null);
        }
            LogIn(email,password);
    }

    private void LogIn(String email, String password) {
        Log.d("TAG","onClick: "+email);

        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
           verificacionDeAdmin(authResult.getUser().getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,"Credenciales equivocadas, prueba otra vez", Toast.LENGTH_LONG).show();
            }
        });
/**
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, NavActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"Credenciales equivocadas, prueba otra vez", Toast.LENGTH_LONG).show();
                }
            }
        });
        */

    }

    private void verificacionDeAdmin(String uid) {
        DocumentReference doc = firestore.collection("Users").document(uid);
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSucces"+documentSnapshot.getData());
                if(documentSnapshot.getString("isAdmin") != null){
                    notificacionAvisoAdmin();
                    notificacionAvisoAdminChannel();
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

    private void notificacionAvisoAdminChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void GoRegistrar(){
        Intent intent = new Intent(LoginActivity.this, RegistrarActivity.class);
        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String> (imgYasaiLogin,"logoImageTrans");
        pairs[1] = new Pair<View, String> (txtBienvenidoLogin,"textTrans");
        pairs[2] = new Pair<View, String> (txtcontinuarLogin,"iniciaSesionTextTrans");
        pairs[3] = new Pair<View, String> (tfemailLogin,"emaiInputTextTrans");
        pairs[4] = new Pair<View, String> (tfpasswordLogin,"passwordInputTextTrans");
        pairs[5] = new Pair<View, String> (btninicioSesion,"buttonTrans");
        pairs[6] = new Pair<View, String> (txtnuevoUsuario,"newUserTrans");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
            startActivity(intent, options.toBundle());
        }else{
            startActivity(intent);
            finish();
        }}

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish();
    }

    public void notificacionAvisoAdmin(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.yasai);
        builder.setContentTitle("Ha iniciado sesion");
        builder.setContentText("Acaba de iniciar sesion en su cuenta administrador");
        builder.setColor(Color.GREEN);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(NOTIFICATION_ID, builder.build());


    }
}