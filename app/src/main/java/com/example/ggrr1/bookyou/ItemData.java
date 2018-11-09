package com.example.ggrr1.bookyou;

import android.graphics.drawable.Drawable;

public class ItemData {
    public Drawable bookImage;
    public String bookName;
    public String author;
    public String price;
    public String salePrice;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public Drawable getBookImage() {
        return bookImage;
    }

    public void setBookImage(Drawable bookImage) {
        this.bookImage = bookImage;
    }
}
