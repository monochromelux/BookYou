package com.example.ggrr1.bookyou;

public class MessageListItem {
    private String book_name;
    private String user_name;
    private String tel;
    private String message;
    private String created;


    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTel() { return tel; }

    public void setTel(String tel) { this.tel = tel; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
