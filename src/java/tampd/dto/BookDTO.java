/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampd.dto;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class BookDTO implements Serializable {
    String id,bookTitle,authorTitle,firstName,lastName;
    double price;
    public BookDTO() {
    }

    public BookDTO(String id, String bookTitle, double price, String authorTitle, String firstName, String lastName) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.price = price;
        this.authorTitle = authorTitle;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthorTitle() {
        return authorTitle;
    }

    public void setAuthorTitle(String authorTitle) {
        this.authorTitle = authorTitle;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
}
