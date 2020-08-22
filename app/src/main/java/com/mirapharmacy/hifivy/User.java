package com.mirapharmacy.hifivy;

class User {

    private String name;
    private int company_id;
    private int min_amount;
    private double price;
    private int customer_id;
    private String id;
    private int last_value;
    private int diff;
    private int curnt_value;
    private int month_id;
    private int year_id;
    private int not_payed;
    private int payed;

    public User(String name, int company_id, int min_amount, double price, int customer_id, String id, int last_value, int diff, int curnt_value, int month_id, int year_id, int not_payed, int payed) {
        this.name = name;
        this.company_id = company_id;
        this.min_amount = min_amount;
        this.price = price;
        this.customer_id = customer_id;
        this.id = id;
        this.last_value = last_value;
        this.diff = diff;
        this.curnt_value = curnt_value;
        this.month_id = month_id;
        this.year_id = year_id;
        this.not_payed = not_payed;
        this.payed = payed;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(int min_amount) {
        this.min_amount = min_amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getLast_value() {
        return last_value;
    }

    public void setLast_value(int last_value) {
        this.last_value = last_value;
    }

    public int getCurnt_value() {
        return curnt_value;
    }

    public void setCurnt_value(int curnt_value) {
        this.curnt_value = curnt_value;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMonth_id() {
        return month_id;
    }

    public void setMonth_id(int month_id) {
        this.month_id = month_id;
    }

    public int getYear_id() {
        return year_id;
    }

    public void setYear_id(int year_id) {
        this.year_id = year_id;
    }

    public int getNot_payed() {
        return not_payed;
    }

    public void setNot_payed(int not_payed) {
        this.not_payed = not_payed;
    }

    public int getPayed() {
        return payed;
    }

    public void setPayed(int payed) {
        this.payed = payed;
    }
}
