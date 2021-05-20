package com.example.proyectoparalelo.ui.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.proyectoparalelo.CartaActivity;
import com.example.proyectoparalelo.LogIn.LoginActivity;
import com.example.proyectoparalelo.NavActivity;
import com.example.proyectoparalelo.R;
import com.example.proyectoparalelo.ui.Food.FoodFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenuFragment extends Fragment {

    private MenuViewModel homeViewModel;
    TextView txtsaludo;
    FloatingActionButton fabInsta,fabFace,fabTwiter;
    ImageView imgYasaiHome,imgFood,imgChat,imgUs,imgOut;
    LinearLayout btnFood,
            btnUs,
            btnSR,
            btnOut;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseUser user;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        imgYasaiHome= root.findViewById(R.id.imgYasaiHome);
        RotateAnimation animation = new RotateAnimation(0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0f,
               //RotateAnimation.RELATIVE_TO_SELF, 0f,
                RotateAnimation.RELATIVE_TO_PARENT,0f);
        animation.setDuration(2000);

        imgYasaiHome.startAnimation(animation);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        txtsaludo = root.findViewById(R.id.txtsaludo);

        saludo();

        imgFood = root.findViewById(R.id.imgFood);
        imgChat = root.findViewById(R.id.imgChat);
        fabInsta = root.findViewById(R.id.fabInsta);
        fabFace = root.findViewById(R.id.fabFace);
        fabTwiter = root.findViewById(R.id.fabTwiter);
        imgUs = root.findViewById(R.id.imgUs);
        imgOut = root.findViewById(R.id.imgOut);
        Glide.with(this).load("https://healthysnackday.com/images/recipe-finder/banner-recipe.png").into(imgFood);
        Glide.with(this).load("https://help.twitter.com/content/dam/help-twitter/brand/logo.png").into(fabTwiter);
        Glide.with(this).load("https://www.centralita-virtual.org/wp-content/uploads/2019/04/imagen-1-articulo-5.png").into(imgChat);
        Glide.with(this).load("https://fatimamartinez.es/wp-content/uploads/2019/07/cuentas-suspendidas-instagram-400x400.png").into(fabInsta);
        Glide.with(this).load("https://www.awerty.net/wp-content/uploads/2019/06/Horario-AWERTY.png").into(imgUs);
        Glide.with(this).load("https://lh3.googleusercontent.com/proxy/gapfWo8Exuat8OuubmhRs0Vj42UC0xP54jGLckPvWg2Fz9ap0qPmDxubdeUgHEdjXERJnTAtEEOgW8c2VQDdNH4VBGfV95Pt4bMk9i_2Shmz65omjAcqQcv0KfYxL1-mNAB6atMqwC0wvy41ERQ-KGXJ6kHO9ZcOnw_gjM4sr0I45PHngDr83JCz-lmbv3EIGg45DNk-LiOghYnJ").into(imgOut);

        btnFood = root.findViewById(R.id.btnFood);
        btnUs = root.findViewById(R.id.btnUs);
        btnSR = root.findViewById(R.id.btnSR);
        btnOut = root.findViewById(R.id.btnOut);

        btnOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Salir();
            }
        });

        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), CartaActivity.class);
                startActivity(intent);
                //finish();
                /*
                FoodFragment foodFragment = new FoodFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.mainActivity, foodFragment).commit();
                */
            }
        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
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

    private void Salir() {
        System.out.println("Funciona btn salir");
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);

    }
}
