package com.kpi.slava.pizza_hub_v1.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable{

    private String idItem, name, idCategory, description, weight, price;

    public Item(String idItem, String name, String idCategory, String description, String weight, String price) {
        this.idItem = idItem;
        this.name = name;
        this.idCategory = idCategory;
        this.description = description;
        this.weight = weight;
        this.price = price;
    }

    public String getIdItem() {
        return idItem;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public String getName() {return name;}

    public String getDescription() {
        return description;
    }

    public String getWeight() {
        return weight;
    }

    public String getPrice() {
        return price;
    }

    protected Item(Parcel in) {
        idItem = in.readString();
        name = in.readString();
        idCategory = in.readString();
        description = in.readString();
        weight = in.readString();
        price = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idItem);
        dest.writeString(name);
        dest.writeString(idCategory);
        dest.writeString(description);
        dest.writeString(weight);
        dest.writeString(price);
    }
}
