package DAL.Store;
import DAL.Model.Book;

import java.util.Collection;

/**
 * Created by pavlo on 17.10.16.
 */
public interface Storage {

        public Collection<Book> books();

        public int add(final Book  book);

        public void edit(final Book book, int id);

        public void delete(final int bookId);

        public void clean();

        public Collection<Book> get(final String bookName);

        public void close();
}
