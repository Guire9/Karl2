package edu.temple.karl2;

import java.io.Serializable;

public class Book implements Serializable {
    String title, author, coverURL;
    int id;


    public Book( String author, String title, String coverURL,int id){
        this.title=title;
        this.author=author;
        this.coverURL=coverURL;
        this.id=id;
    }

    public String getAuthor(){
        return author;
    }
    public String getTitle(){
        return title;
    }
    public String getCoverURL(){return coverURL;}
    public int getId(){return id;}
}
