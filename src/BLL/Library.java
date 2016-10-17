package BLL;


import DAL.Model.Book;
import DAL.Store.JDBCStore;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by pavlo on 17.10.16.
 */
public class Library {
    private String name;
    private List<Book> books;
    private JDBCStore jdbcStore;

    public Library(){
        try {
            jdbcStore = new JDBCStore();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        books = (List<Book>) jdbcStore.books();
    }

    public boolean addBook(Book book){
        try {
            jdbcStore.add(book);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> getBooksByName(String name){
        return (List<Book>) jdbcStore.get(name);
    }

    public List<Book> removeBook(String name) {
        books = getBooksByName(name);

        if (books.isEmpty()) {
            return null;
        } else if (books.size() == 1) {
            jdbcStore.delete(books.get(0).getId());
        }
        return books;
    }

    public boolean removeBookById(int id){
        try {
            jdbcStore.delete(id);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> editBook(Book book){

        books = (List<Book>) jdbcStore.get(name);

        if(books.isEmpty()){
            return null;
        }
        else if(books.size() == 1){
            jdbcStore.edit(book);
            return books;
        }
        else {
            return books;
        }
        return true;
    }

    public Collection<Book> showAllLibrary() throws SQLException, ClassNotFoundException {
        books = (List<Book>) jdbcStore.books();
        if(books.isEmpty()) {
            books.add(new Book("Empty"));
        }
        return books;
    }
}
