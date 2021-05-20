package com.example.proyectoparalelo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectoparalelo.ElementosList.ListElement;

public class DescriptionActivity extends AppCompatActivity {

    TextView txtTitledescrip ,txtContentdescrip,txtPricedescrip;
    ImageView imgdescrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        ListElement element = (ListElement) getIntent().getSerializableExtra("ListElement");
        txtTitledescrip = findViewById(R.id.txtTitledescrip);
        txtContentdescrip = findViewById(R.id.txtContentdescrip);
        txtPricedescrip = findViewById(R.id.txtPricedescrip);
        imgdescrip = findViewById(R.id.imgdescrip);

        txtTitledescrip.setText(element.getNombre());
        //txtTitledescrip.setTextColor(getColorStateList()));

        txtContentdescrip.setText(element.getDescripcion());

        txtPricedescrip.setText(element.getPrecio());
        //txtPricedescrip.setTextColor(Integer.parseInt("#000"));

        //imgdescrip.setImage

        imgdescrip.setImageResource(element.getImageId());

    }
}