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
        this.name = name;
        this.author = author;
        this.type = type;
        this.nPages = nPages;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setName(String name) {
        this.name = name;
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
                "id: " + getId() + "| " +
                "Name: " + getName() + "| " +
                "Author: " + getAuthor() + "| " +
                "Type: " + getType() + "| "+
                        "Number of Pages: " + getnPages() + "\n";
    }

    public void setId(int id) {
        this.id = id;
    }
}
