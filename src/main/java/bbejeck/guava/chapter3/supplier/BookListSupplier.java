package bbejeck.guava.chapter3.supplier;

import bbejeck.guava.common.model.Book;
import bbejeck.guava.common.service.BookService;
import com.google.common.base.Supplier;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: Bill Bejeck
 * Date: 4/5/13
 * Time: 1:53 PM
 */
public class BookListSupplier implements Supplier<List<Book>> {

    private BookService bookService;

    public BookListSupplier(BookService bookService) {
        this.bookService = checkNotNull(bookService);
    }

    @Override
    public List<Book> get() {
        return bookService.get();
    }
}
