package Driver.ConsoleClient;

import BLL.Library;
import DAL.Model.Book;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by pavlo on 17.10.16.
 */
public class ConsoleClient {

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

    public static void help(){
        System.out.println(  " -help - use for show information about library\n" +
                " -show - for show all library\n" +
                " -add - add book to library in format -add [parameters]\n\t [Book_Name Book_Author Type_of_book number_of_pages]\n" +
                " -remove - delete book from library by name [Book_Name]\n" +
                " -edit - edit book from library by name [Book_Name]\n" +
                " -exit - end work in library\n" +
                " put all parameters without-> []\n");
    }

    public static boolean add(String str){
        str = str.toUpperCase();
        System.out.println(library.addBook(new Book(
                -1, str.split(" ")[1], str.split(" ")[2], str.split(" ")[3],
                Integer.parseInt(str.split(" ")[4]))));
        return true;
    }

    public static boolean remove(String str){
        str = str.toUpperCase();
        List<Book> books = library.removeBook(str.split(" ")[1]);
        if(books == null){
            System.out.println(/*books.get(0).getName() + */" no such book!");
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
        name = name.toUpperCase();
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
                    library.editBookById(createNewBook(_book));
            }

        }

        return true;
    }
    public static void show(){
//        library.showAllLibrary();
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.out.println("Hi dear User, this is our Library\n You can manege our library!");
        System.out.println("Pres '-help' for help in use.");

        boolean swt = true;
        while (swt){
            switch (scanner.next()){
                case "-help" :
                    help();
                    break;
                case "-show" :
                    library.showAllLibrary();
                    break;
                case "-add":
                    ConsoleClient.add(scanner.nextLine());
                    break;
                case "-remove":
                    remove(scanner.nextLine());
                    break;
                case "-edit":

                    break;

                case "-exit" : swt = false;
                    break;
            }
        }
    }
}
