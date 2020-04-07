package edu.temple.karl2;

import java.io.Serializable;

public class Book implements Serializable {
    String title, author;

    public Book( String author, String title){
        this.title=title;
        this.author=author;
    }

    public String getAuthor(){
        return author;
    }
    public String getTitle(){
        return title;
    }
}
