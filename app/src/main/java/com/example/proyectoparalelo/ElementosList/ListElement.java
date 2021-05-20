package com.example.proyectoparalelo.ElementosList;

import java.io.Serializable;

public class ListElement implements Serializable {
    public String nombre;
    public String Descripcion;
    public String Precio;
    private int ImageId;

    public ListElement(String nombre, String descripcion, String precio, int imageId) {
        this.nombre = nombre;
        Descripcion = descripcion;
        Precio = precio;
        ImageId = imageId;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }
}
