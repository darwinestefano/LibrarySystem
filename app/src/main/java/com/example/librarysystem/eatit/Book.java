package com.example.librarysystem.eatit;
/**
 * Book.java
 * This class is an object of Book
 * @author: Darwin Machado
 *
 */
import java.io.Serializable;

public class Book  implements Serializable{

    private Integer id;
    private Integer quantity;
    private String title, author, genre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    @Override
    public String toString() {
        return  title;
    }
}
