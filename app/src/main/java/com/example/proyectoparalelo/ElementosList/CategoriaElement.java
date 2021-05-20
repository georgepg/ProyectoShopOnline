package com.example.proyectoparalelo.ElementosList;

import java.io.Serializable;

public class CategoriaElement implements Serializable {
    public String Nombre;
    private int ImageId;

    public CategoriaElement() {}

    public CategoriaElement(String nombre) {
        this.Nombre = nombre;
    }

    public CategoriaElement(String nombre, int imageId) {
        this.Nombre = nombre;
        this.ImageId = imageId;
    }

    public String getNom() {
        return Nombre;
    }

    public void setNom(String nombre) {
        Nombre = nombre;
    }


    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }


}
