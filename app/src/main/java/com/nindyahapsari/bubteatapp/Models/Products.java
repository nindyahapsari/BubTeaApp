package com.nindyahapsari.bubteatapp.Models;

public class Products {

    private String tea, name, id;


    public Products()
    {

    }

    public Products(String tea, String name, String id) {
        this.tea = tea;
        this.name = name;
        this.id = id;
    }


    public String getTea() {
        return tea;
    }

    public void setTea(String tea) {
        this.tea = tea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
