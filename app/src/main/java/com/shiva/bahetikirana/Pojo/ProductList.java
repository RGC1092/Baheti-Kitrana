package com.shiva.bahetikirana.Pojo;

import android.provider.Telephony;

/**
 * Created by RAHUL on 20-Jan-2020.
 */
public class ProductList {

    private int id;
    private float Quantity;


    private String createdAt;


    private int billNo;
    private String Product;
    private float Total;
    private float Rate;

    public ProductList() {

    }

    public ProductList(String product, float quantity, float rate, float total) {
        Quantity = quantity;
        Product = product;
        Total = total;
        Rate = rate;

    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getBillNo() {
        return billNo;
    }

    public void setBillNo(int billNo) {
        this.billNo = billNo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getQuantity() {
        return Quantity;
    }

    public void setQuantity(float quantity) {
        Quantity = quantity;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        Total = total;
    }

    public float getRate() {
        return Rate;
    }

    public void setRate(float rate) {
        Rate = rate;
    }


}
