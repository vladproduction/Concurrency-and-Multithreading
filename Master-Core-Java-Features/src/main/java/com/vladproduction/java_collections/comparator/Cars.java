package com.vladproduction.java_collections.comparator;

import java.util.Objects;

public class Cars {

    private String carName;
    private int year;
    private int price;

    public Cars(String carName, int year, int price) {
        this.carName = carName;
        this.year = year;
        this.price = price;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cars cars = (Cars) o;
        return year == cars.year && price == cars.price && Objects.equals(carName, cars.carName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carName, year, price);
    }

    @Override
    public String toString() {
//        return this.carName + " " + this.year + " " + this.price;
        return String.format("%-10s %-6d $%-8d", this.carName, this.year, this.price);
    }
}
