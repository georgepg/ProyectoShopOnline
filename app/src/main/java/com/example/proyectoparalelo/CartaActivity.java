package com.example.proyectoparalelo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.example.proyectoparalelo.Adapters.CategoriaAdapter;
import com.example.proyectoparalelo.Adapters.FoodAdapter;
import com.example.proyectoparalelo.ElementosList.CategoriaElement;
import com.example.proyectoparalelo.ElementosList.ListElement;

import java.util.ArrayList;
import java.util.List;

public class CartaActivity extends AppCompatActivity {

    List<ListElement> CerealesList;
    List<ListElement> PastaList;
    List<CategoriaElement> elementsCategoria;
    RecyclerView recyclerViewCategoria;
    RecyclerView recyclerView;
    ImageButton btnShoppingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);
        recyclerView = findViewById(R.id.rcvFood);
        recyclerViewCategoria = findViewById(R.id.rcvCategoria);
        ShoppingCart();
        cerealesList();
        categoriaList();
    }

    private void ShoppingCart() {
        Glide.with(this).load("https://image.flaticon.com/icons/png/512/107/107831.png").into(btnShoppingCart);
    }

    private void categoriaList() {
        String[] names = new String[]{"Cereales","Pasta","Ensaladas","Sopas","Jugos","Refrescos","Frutas","Helados"};
        int[] Imgenes = new int[]{R.drawable.business,R.drawable.home,R.drawable.business,R.drawable.cake,R.drawable.business,R.drawable.cake,R.drawable.business,R.drawable.cake};
        elementsCategoria = new ArrayList<>();
        for (int i = 0;i < names.length;i++) {
            elementsCategoria.add(new CategoriaElement(names[i]/*,Imgenes[i]*/));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoria.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCategoria.setLayoutManager(layoutManager);
        CategoriaAdapter listAdapterCategoria = new CategoriaAdapter(elementsCategoria, this, new CategoriaAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int adapterPosition) {
                actualizarCategoria(adapterPosition);
            }
        });
        recyclerViewCategoria.setHasFixedSize(true);
        recyclerViewCategoria.setAdapter(listAdapterCategoria);

    }

    private void actualizarCategoria(int adapterPosition) {
        switch (adapterPosition){
            case 0:
                cerealesList();
                break;
            case 1:
                pastaList();
                break;
            case 2:


        }

    }
    private void pastaList(){
        String[] namePasta = new String[]{"Fetuchinis","Raviolis","Tagliatela","Fetuchinis","Raviolis","Tagliatela","Fetuchinis"};
        String[] descrip = new String[]{"La quinoa contiene un 15% de proteínas, sus granos son crujientes y quedan bien tanto en platos salados como en dulces.","Es un superalimento beneficioso para el cerebro gracias a su contenido en lecitina, magnesio y fósforo.","Al no haber sido descascarillado, conserva mucha más fibra que el arroz blanco, lo que reduce su índice glucémico y lo hace más saludable. Y además es abundante en minerales, vitaminas del grupo B y vitamina E.","Posee más proteínas que muchos cereales, así como magnesio, hierro y manganeso. Excelente aporte de proteínas vegetales. ","Es un cereal tierno, dulce y jugoso que se puede comer como verdura, destaca por su aporte en hidratos de carbono, ﬁbra, betacaroteno y vitamina C.íz","Su sabor a frutos secos va bien en ensalada y contiene un ﬂavonoide llamado rutina que lo hace antiinﬂamatorio y cardioprotector.","Estos diminutos granos proporcionan hierro, cinc y manganeso; son además una fuente de energía saludable gracias a su bajo índice glucémico."};
        int[] Images = new int[]{R.drawable.business,R.drawable.business,R.drawable.business,R.drawable.cake,R.drawable.business,R.drawable.cake,R.drawable.business};
        String[] price = new String[]{"15","18","20","12","10","15","20"};
        PastaList = new ArrayList<>();
        for (int i = 0;i < namePasta.length;i++) {
            PastaList.add(new ListElement(namePasta[i], descrip[i], price[i]+" €", Images[i]));
        }

        FoodAdapter listpastaAdapter = new FoodAdapter(PastaList,this,new FoodAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(ListElement item) {
                gotoDescription(item);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listpastaAdapter);
    }

    private void cerealesList() {
        String[] namesCereales = new String[]{"Quinoa","Mijo","Arroz integral","Amaranto","Maíz","Trigo sarraceno","Teff"};
        String[] descrip = new String[]{"La quinoa contiene un 15% de proteínas, sus granos son crujientes y quedan bien tanto en platos salados como en dulces.","Es un superalimento beneficioso para el cerebro gracias a su contenido en lecitina, magnesio y fósforo.","Al no haber sido descascarillado, conserva mucha más fibra que el arroz blanco, lo que reduce su índice glucémico y lo hace más saludable. Y además es abundante en minerales, vitaminas del grupo B y vitamina E.","Posee más proteínas que muchos cereales, así como magnesio, hierro y manganeso. Excelente aporte de proteínas vegetales. ","Es un cereal tierno, dulce y jugoso que se puede comer como verdura, destaca por su aporte en hidratos de carbono, ﬁbra, betacaroteno y vitamina C.íz","Su sabor a frutos secos va bien en ensalada y contiene un ﬂavonoide llamado rutina que lo hace antiinﬂamatorio y cardioprotector.","Estos diminutos granos proporcionan hierro, cinc y manganeso; son además una fuente de energía saludable gracias a su bajo índice glucémico."};
        int[] Images = new int[]{R.drawable.business,R.drawable.business,R.drawable.business,R.drawable.cake,R.drawable.business,R.drawable.cake,R.drawable.business};
        String[] price = new String[]{"15","18","20","12","10","15","20"};
        CerealesList = new ArrayList<>();
        for (int i = 0;i < namesCereales.length;i++) {
            CerealesList.add(new ListElement(namesCereales[i], descrip[i], price[i]+" €", Images[i]));
        }

        FoodAdapter listCerealAdapter = new FoodAdapter(CerealesList,this,new FoodAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(ListElement item) {
                gotoDescription(item);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listCerealAdapter);

    }

    private void gotoDescription(ListElement item) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra("ListElement",item);
        startActivity(intent);
    }
}