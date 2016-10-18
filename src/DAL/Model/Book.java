package DAL.Model;

import java.util.Collection;
import java.util.Comparator;

/**
 * Created by pavlo on 17.10.16.
 */
public class Book {
    private int id;
    private String name;
    private String author;
    private String type;
    private int nPages;

    public Book(){

    }

    public Book(String name){
        this.name = name;
    }

    public Book(int id, String name, String author, String type, int nPages) {
        this.id = id;
        this.name = name.toUpperCase();
        this.author = author.toUpperCase();
        this.type = type.toUpperCase();
        this.nPages = nPages;
    }
    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public void setAuthor(String author) {
        this.author = author.toUpperCase();
    }

    public void setType(String type) {
        this.type = type.toUpperCase();
    }

    public void setnPages(int nPages) {
        this.nPages = nPages;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getAuthor() {
        return author;
    }

    public String getType() {
        return type;
    }

    public int getnPages() {
        return nPages;
    }

    @Override
    public String toString() {
        return
                "id: " + getId() + "\t| " +
                "Name: " + getName() + "\t| " +
                "Author: " + getAuthor() + "\t| " +
                "Type: " + getType() + "\t| "+
                        "Number of Pages: " + getnPages();
    }

    public void setId(int id) {
        this.id = id;
    }
}
