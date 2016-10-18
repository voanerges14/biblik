package DAL.Store;

import DAL.Model.Book;
import DAL.Store.Storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * Created by pavlo on 17.10.16.
 */
public class JDBCStore implements Storage{
        private final Connection connection;
        public JDBCStore() throws SQLException, ClassNotFoundException {
            Class.forName("com.mysql.jdbc.Driver");
//        String AzureStringURL = "jdbc:sqlserver://dmytroki.database.windows.net:1433;database=companies;" +
//                "user=dmytroki@dmytroki;password=125678965274kihi_;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
//        this.connection = DriverManager.getConnection(AzureStringURL);
//            this.connection = DriverManager.getConnection("jdbc:mysql://companies.czrh6kl4gie2.us-west-2.rds.amazonaws.com/companies", "root", "rootroot");
//        this.connection =  DriverManager.getConnection("jdbc:mysql://localhost:3307/companies", "root", "root");
            this.connection = DriverManager.getConnection("jdbc:mysql://eu-cdbr-azure-west-a.cloudapp.net:3306/acsm_20fce0d183b5ab7"
                    , "bd1f39f1a5d0ba", "64bd59b0");
            //this.connection =  DriverManager.getConnection("jdbc:mysql://104.198.56.204:3306/companies", "root", "root");
        }

    @Override
    public Collection<Book> books() {
            final List<Book> books = new ArrayList<>();
            try (final Statement statement = this.connection.createStatement();
                 final ResultSet rs = statement.executeQuery("select * from library")) {
                while (rs.next()) {
                    books.add(new Book(rs.getInt("id"), rs.getString("name")
                            , rs.getString("author"), rs.getString("type"), rs.getInt("nPages")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return books;
    }


    @Override
    public int add(Book book) {
        try (final PreparedStatement statement =
                     this.connection.prepareStatement("INSERT INTO library (name, author, type, nPages) VALUES(?,?,?,?)"
                             , Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getType());
            statement.setInt(4, book.getnPages());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Could not add new book");
    }


    @Override
    public void edit(Book book) {
        try (final PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE book SET name = ?, author = ?, type = ?, nPages = ? WHERE id = ?")) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getType());
            statement.setInt(4, book.getnPages());
            statement.setInt(5, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(int bookId) {
        try (final PreparedStatement statement = this.connection.prepareStatement(
                "delete from library where id = ?")) {
            statement.setInt(1, bookId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clean() {
        try
        {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM library");
            statement.executeQuery("ALTER SEQUENCE library_id_seq  RESTART WITH 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Book> get(String bookName) {
        final List<Book> library = new ArrayList<>();
        try (final PreparedStatement statement = this.connection.prepareStatement(
                "select * from library where name = ?"))
        {
            statement.setString(1, bookName);
            //ResultSet rs = statement.executeQuery();
            try (final ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    library.add(new Book(rs.getInt("id"), rs.getString("name")
                            , rs.getString("author"), rs.getString("type"), rs.getInt("nPages")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return library;
    }

    @Override
    public void close() {
        try {
        connection.close();
        } catch (SQLException e) {
        e.printStackTrace();
        }
    }
}

