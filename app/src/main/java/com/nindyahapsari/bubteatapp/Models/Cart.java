package com.nindyahapsari.bubteatapp.Models;

public class Cart {

    private String name, date;
    private Long quantity, totalprice;


    public Cart(){

    }

    public Cart(String name, String date, Long quantity, Long totalprice) {
        this.name = name;
        this.date = date;
        this.quantity = quantity;
        this.totalprice = totalprice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Long totalprice) {
        this.totalprice = totalprice;
    }
}
