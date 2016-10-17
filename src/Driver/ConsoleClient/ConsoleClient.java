package Driver.ConsoleClient;

import BLL.Library;
import DAL.Model.Book;
import com.sun.org.apache.bcel.internal.classfile.Unknown;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by pavlo on 17.10.16.
 */
public class ConsoleClient {
    String name = "Unknown";
    String author = "Unknown";
    String type = "Unknown";
    int nPages = 0;
    private static  Scanner scanner = new Scanner(System.in);
    private static Library library = new Library();


    private static Book createNewBook(Book book){
        System.out.println("new Name: ");
        if(scanner.hasNext())
            book.setName(scanner.next());
        System.out.println("new Author: ");
        if(scanner.hasNext())
            book.setAuthor(scanner.next());
        System.out.println("new Type: ");
        if(scanner.hasNext())
            book.setType(scanner.next());
        System.out.println("new Number of pages: ");
        if(scanner.hasNext())
            book.setnPages(Integer.parseInt(scanner.next()));
        return book;
    }

    public static String help(){
        return " -help - use for show information about library\n" +
                " -slib - for show all library" +
                "";
    }

    public static boolean add(String str){
//        String str = scanner.nextLine();
        System.out.println(library.addBook(new Book(
                -1, str.split(" ")[1], str.split(" ")[2], str.split(" ")[3],
                Integer.parseInt(str.split(" ")[4]))));
        return true;
    }

    public static boolean remove(String str){
        List<Book> books = library.removeBook(str);
        if(books == null){
            System.out.println(books.get(0).getName() + " no such book!");
//            return false;
        }
        else if(books.size() == 1){
            System.out.println(books.get(0).getName() + " was remowed!");
            return true;
        }
        else {
            for(Book book: books)
                System.out.println(book.toString());
            System.out.println("enter the id by book what you want to remove");
            library.removeBookById(Integer.parseInt(scanner.next()));
        }
    return true;
    }


    public static boolean edit(String name){
        Book book = new Book();
        List<Book> books = library.getBooksByName(name);

        if(books == null){
            System.out.println(books.get(0).getName() + " no such book!");
        }
        else if(books.size() == 1){

            library.editBook(createNewBook(books.get(0)));
            System.out.println(books.get(0).getName() + " was edit!");
            return true;
        }
        else {
            for(Book _book: books)
                System.out.println(book.toString());
            System.out.println("enter the id by book what you want to Edit");
            int t = Integer.parseInt(scanner.next());
            for(Book _book: books) {
                if (t == _book.getId())
                    library.editBook(createNewBook(_book), t);
            }

        }


    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hi dear User, this is our Library\n You can manege our library!");
        System.out.println("Pres '-help' for help in use.");

        boolean swt = true;
        while (swt){
            switch (scanner.next()){
                case "-help" :
                    System.out.println(help());
                    break;
                case "-slib" :
                    System.out.println(library.showAllLibrary());
                    break;
                case "-add":
                    ConsoleClient.add(scanner.nextLine());
                    break;
                case "-remove":
                    System.out.println(remove(scanner.nextLine()));
                    break;
                case "-edit":

                    break;

                case "-exit" : swt = false;
                    break;
            }
        }
    }
}
