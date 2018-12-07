package com.example.ggrr1.bookyou;

import android.graphics.drawable.Drawable;

public class BookListItem {
    public Drawable bookImage;

    public String book_id;
    public String name;
    public String author;
    public String price;
    public String sale_price;
    public String date;

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBookName() {
        return name;
    }

    public void setBookName(String bookName) {
        this.name = bookName;
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
        return sale_price;
    }

    public void setSalePrice(String salePrice) {
        this.sale_price = salePrice;
    }

    public Drawable getBookImage() {
        return bookImage;
    }

    public void setBookImage(Drawable bookImage) {
        this.bookImage = bookImage;
    }
}
