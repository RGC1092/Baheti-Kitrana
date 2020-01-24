package com.shiva.bahetikirana.Pojo;

/**
 * Created by RAHUL on 18-Jan-2020.
 */
public class MyBills {

    int BILL_NO;
    String BILL_DATE;
    String NAME;
    String ADDRESS;
    String MOBILE;
    String PRODUCT;
    float QUANTITY;
    float RATE;


    float SUBTOTAL;
    float CUTTING_VALUE;
    float CUTTING_RATE;
    float WAGES_VALUE;
    float WAGES_RATE;
    float TRANSPORT_VALUE;
    float TRANSPORT_RATE;
    float TOTAL_PAYABLE;
    String PAYBY;
    float PAID;
    float BALANCE;
    String CREATED_AT;
    String UPDATED_AT;

    public MyBills(){

    }

    public int getBILL_NO() {
        return BILL_NO;
    }

    public void setBILL_NO(int BILL_NO) {
        this.BILL_NO = BILL_NO;
    }

    public String getBILL_DATE() {
        return BILL_DATE;
    }

    public void setBILL_DATE(String BILL_DATE) {
        this.BILL_DATE = BILL_DATE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }


    public String getPRODUCT() {
        return PRODUCT;
    }

    public void setPRODUCT(String PRODUCT) {
        this.PRODUCT = PRODUCT;
    }

    public float getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(float QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public float getRATE() {
        return RATE;
    }

    public void setRATE(float RATE) {
        this.RATE = RATE;
    }

    public float getSUBTOTAL() {
        return SUBTOTAL;
    }

    public void setSUBTOTAL(float SUBTOTAL) {
        this.SUBTOTAL = SUBTOTAL;
    }

    public float getCUTTING_VALUE() {
        return CUTTING_VALUE;
    }

    public void setCUTTING_VALUE(float CUTTING_VALUE) {
        this.CUTTING_VALUE = CUTTING_VALUE;
    }

    public float getCUTTING_RATE() {
        return CUTTING_RATE;
    }

    public void setCUTTING_RATE(float CUTTING_RATE) {
        this.CUTTING_RATE = CUTTING_RATE;
    }

    public float getWAGES_VALUE() {
        return WAGES_VALUE;
    }

    public void setWAGES_VALUE(float WAGES_VALUE) {
        this.WAGES_VALUE = WAGES_VALUE;
    }

    public float getWAGES_RATE() {
        return WAGES_RATE;
    }

    public void setWAGES_RATE(float WAGES_RATE) {
        this.WAGES_RATE = WAGES_RATE;
    }

    public float getTRANSPORT_VALUE() {
        return TRANSPORT_VALUE;
    }

    public void setTRANSPORT_VALUE(float TRANSPORT_VALUE) {
        this.TRANSPORT_VALUE = TRANSPORT_VALUE;
    }

    public float getTRANSPORT_RATE() {
        return TRANSPORT_RATE;
    }

    public void setTRANSPORT_RATE(float TRANSPORT_RATE) {
        this.TRANSPORT_RATE = TRANSPORT_RATE;
    }

    public float getTOTAL_PAYABLE() {
        return TOTAL_PAYABLE;
    }

    public void setTOTAL_PAYABLE(float TOTAL_PAYABLE) {
        this.TOTAL_PAYABLE = TOTAL_PAYABLE;
    }

    public String getPAYBY() {
        return PAYBY;
    }

    public void setPAYBY(String PAYBY) {
        this.PAYBY = PAYBY;
    }

    public float getPAID() {
        return PAID;
    }

    public void setPAID(float PAID) {
        this.PAID = PAID;
    }

    public float getBALANCE() {
        return BALANCE;
    }

    public void setBALANCE(float BALANCE) {
        this.BALANCE = BALANCE;
    }

    public String getCREATED_AT() {
        return CREATED_AT;
    }

    public void setCREATED_AT(String CREATED_AT) {
        this.CREATED_AT = CREATED_AT;
    }

    public String getUPDATED_AT() {
        return UPDATED_AT;
    }

    public void setUPDATED_AT(String UPDATED_AT) {
        this.UPDATED_AT = UPDATED_AT;
    }
}
