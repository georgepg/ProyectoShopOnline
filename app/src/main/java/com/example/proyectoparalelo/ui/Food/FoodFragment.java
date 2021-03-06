package com.example.proyectoparalelo.ui.Food;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoparalelo.R;

public class FoodFragment extends Fragment {

    private FoodViewModel galleryViewModel;
  /*
    List<ListElement> elements;
    List<CategoriaElement> elementsCategoria;
    RecyclerView recyclerViewCategoria;
    RecyclerView recyclerView;
*/
    ProgressDialog progressDialog;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(FoodViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
/*
        recyclerView = root.findViewById(R.id.rcvFood);
        recyclerViewCategoria = root.findViewById(R.id.rcvCategoria);
        initVertical();
        initHorizontal();
        */

        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
/*
    private void initHorizontal() {
        String[] names = new String[]{"Cereales","Pasta","Ensaladas","Sopas","Jugos","Refrescos","Frutas","Helados"};
        int[] Imgenes = new int[]{R.drawable.business,R.drawable.home,R.drawable.business,R.drawable.cake,R.drawable.business,R.drawable.cake,R.drawable.business,R.drawable.cake};
        elementsCategoria = new ArrayList<>();
        for (int i = 0;i < names.length;i++) {
            elementsCategoria.add(new CategoriaElement(names[i],Imgenes[i]));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoria.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCategoria.setLayoutManager(layoutManager);
        CategoriaAdapter listAdapterCategoria = new CategoriaAdapter(elementsCategoria, getContext());
        recyclerViewCategoria.setHasFixedSize(true);
        recyclerViewCategoria.setAdapter(listAdapterCategoria);

    }

    private void initVertical() {
        String[] name = new String[]{"Quinoa","Mijo","Arroz integral","Amaranto","Ma??z","Trigo sarraceno","Teff"};
        String[] descrip = new String[]{"La quinoa contiene un 15% de prote??nas, sus granos son crujientes y quedan bien tanto en platos salados como en dulces.","Es un superalimento beneficioso para el cerebro gracias a su contenido en lecitina, magnesio y f??sforo.","Al no haber sido descascarillado, conserva mucha m??s fibra que el arroz blanco, lo que reduce su ??ndice gluc??mico y lo hace m??s saludable. Y adem??s es abundante en minerales, vitaminas del grupo B y vitamina E.","Posee m??s prote??nas que muchos cereales, as?? como magnesio, hierro y manganeso. Excelente aporte de prote??nas vegetales. ","Es un cereal tierno, dulce y jugoso que se puede comer como verdura, destaca por su aporte en hidratos de carbono, ???bra, betacaroteno y vitamina C.??z","Su sabor a frutos secos va bien en ensalada y contiene un ???avonoide llamado rutina que lo hace antiin???amatorio y cardioprotector.","Estos diminutos granos proporcionan hierro, cinc y manganeso; son adem??s una fuente de energ??a saludable gracias a su bajo ??ndice gluc??mico."};
        int[] Images = new int[]{R.drawable.business,R.drawable.business,R.drawable.business,R.drawable.cake,R.drawable.business,R.drawable.cake,R.drawable.business};
        String[] price = new String[]{"15","18","20","12","10","15","20"};
        elements = new ArrayList<>();
        for (int i = 0;i < name.length;i++) {
            elements.add(new ListElement(name[i], descrip[i], price[i]+" ???", Images[i]));
        }

        FoodAdapter listAdapter = new FoodAdapter(elements,getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);

    }
    */

}