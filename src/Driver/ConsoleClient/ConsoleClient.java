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


    private static Book createNewBook(Book _book){
        Book book = _book;
        Scanner scan = new Scanner(System.in);
        System.out.print("new Name: ");
        String str = scan.nextLine();
        if(!str.isEmpty())
            book.setName(str);

        System.out.print("new Author: ");
        str = scan.nextLine();
        if(!str.isEmpty())
            book.setAuthor(str);

        System.out.print("new Type: ");
        str = scan.nextLine();
        if(!str.isEmpty())
            book.setType(str);

        System.out.print("new Number of pages: ");
        str = scan.nextLine();
        if(!str.isEmpty()){
            boolean swt = false;
            while (!swt) {
                if (str.matches("[-+]?\\d+")) {
                    swt = true;
                    book.setnPages(Integer.parseInt(str));
                } else System.out.println(scan.next() + " is not a number");
            }
        }
        return book;
    }
    public static void help(){
        System.out.println(  " -help - show information about library\n" +
                " -show - show all books in library\n" +
                " -add - add book to library in format -add [parameters]\n\t " +
                    " [Book_Name Book_Author Type_of_book number_of_pages]\n" +
                " -remove - delete book from library by name [Book_Name]\n\t" +
                    " -remove [Book_Name]" +
                " -edit - edit book from library by name [Book_Name]\n\t" +
                    " -edit [Book_name]" +
                " -exit - end work in library\n" +
                " put all parameters without-> []\n");
    }
    public static void show(){
        try {
            List<Book> books = (List<Book>) library.showAllLibrary();
            for (Book book: books){
                System.out.println(book.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void add(String str){
        try {
            if ((library.addBook(new Book(
                    -1, str.split(" ")[1], str.split(" ")[2], str.split(" ")[3],
                    Integer.parseInt(str.split(" ")[4]))))) {
                System.out.println("Book |" + str + " | was add.");
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.err.println(" ' -add ' mast have a parameters! ");
            help();
        }


    }
    public static void remove(String name){
        try {

            List<Book> books = library.removeBook(name.split(" ")[1]);
            if (books == null) {
                System.out.println("Book |" + name + " | not found!");
            } else if (books.size() == 1) {
                System.out.println("Book |" + name + " | was removed!");
            } else {
                for (Book book : books)
                    System.out.println(book.toString());
                System.out.print("Enter the id by book what you want to remove: ");
                boolean swt = false;
                while (!swt) {
                    if (scanner.hasNextInt()) {
                        swt = true;
                        int t = Integer.parseInt(scanner.next());
                        if (library.removeBookById(t)) {
                            System.out.println("Book |" + name + " | with id: " + t + " was removed!");
                        }
                    } else System.out.println(scanner.next() + " is not a number");
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println(" ' -remove ' mast have a parameters! ");
            help();
        }
    }
    public static void edit(String name){
        try {
            List<Book> books = library.getBooksByName(name.split(" ")[1]);
            if (books == null) {
                System.out.println("Book |" + name + " | not found");
            } else if (books.size() == 1) {
                library.editBook(createNewBook(books.get(0)));
                System.out.println("Book |" + name + " | was edit!");
                System.out.println("\t| " + books.get(0).toString() + " |");
            } else {
                for (Book _book : books)
                    System.out.println(_book.toString());
                System.out.print("Enter the id by book what you want to edit: ");
                boolean swt = false;
                while (!swt) {
                    if (scanner.hasNextInt()) {
                        swt = true;
                        int t = Integer.parseInt(scanner.next());
                        for (Book _book : books) {
                            if (t == _book.getId()) {
                                library.editBook(createNewBook(_book));
                                break;
                            }
                        }
                        System.out.println("Book |" + name + " | was edited!");
                    } else System.out.println(scanner.next() + " is not a number");
                }


            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println(" ' -edit ' mast have a parameters! ");
            help();
        }
    }
    public static void incorectParam(String str){
        System.out.println("NOT FOUND COMMAND ' " + str + " '\n");
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
       try {

           System.out.println("################################");
           System.out.println("# Hi dear User in our Library  #\n" +
                   "# You can manege this library! #");
           System.out.println("###############################\n");

           System.out.println("Pres '-help' for help in use.");

           boolean swt = true;
           while (swt) {
               switch (scanner.next()) {
                   case "-help":
                       help();
                       break;
                   case "-show":
                       show();
                       break;
                   case "-add":
                       add(scanner.nextLine());
                       break;
                   case "-remove":
                       remove(scanner.nextLine());
                       break;
                   case "-edit":
                       edit(scanner.nextLine());
                       break;
                   case "-exit":
                       swt = false;
                       library.endWork();
                       break;
                   default:
                       incorectParam(scanner.next());

               }
           }
       }catch (Exception e){
       }
       finally {
           library.endWork();
       }
    }
}
