package bbejeck.guava.chapter3.supplier;

import bbejeck.guava.chapter3.function.BookListConverter;
import bbejeck.guava.common.model.Book;
import bbejeck.guava.common.service.BookService;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * User: Bill Bejeck
 * Date: 4/5/13
 * Time: 2:03 PM
 */
public class SupplierTest {

    private Supplier<Map<String, String>> bookMapSupplier;
    private BookService bookService = mock(BookService.class);
    private BookListSupplier bookListSupplier = new BookListSupplier(bookService);
    private Function<List<Book>, Map<String, String>> function = new BookListConverter();


    @Test
    public void testComposedMemoizeSupplier() throws Exception {
        Book book = new Book.Builder().author("Pennypacker, HJ").isbn("ISBN-98765432").title("How I Made Millions").publisher("Acme Publishers").price(4999.99).build();
        when(bookService.get()).thenReturn(Lists.newArrayList(book));
        Supplier<List<Book>> cachedSupplier = Suppliers.memoize(bookListSupplier);
        bookMapSupplier = Suppliers.compose(function, cachedSupplier);
        Map<String, String> bookMap = bookMapSupplier.get();
        assertThat(bookMap.get("ISBN-98765432"), is("How I Made Millions|Acme Publishers|4999.99"));
        Thread.sleep(15);

        bookMap = bookMapSupplier.get();
        assertThat(bookMap.get("ISBN-98765432"), is("How I Made Millions|Acme Publishers|4999.99"));
        Thread.sleep(15);

        bookMap = bookMapSupplier.get();
        assertThat(bookMap.get("ISBN-98765432"), is("How I Made Millions|Acme Publishers|4999.99"));
        Thread.sleep(15);

        bookMap = bookMapSupplier.get();
        assertThat(bookMap.get("ISBN-98765432"), is("How I Made Millions|Acme Publishers|4999.99"));

        verify(bookService, times(1)).get();
    }

    @Test
    public void testComposedMemoizeWithExpirationSupplier() throws Exception {
        Book book = new Book.Builder().author("Pennypacker, HJ").isbn("ISBN-98765432").title("How I Made Millions").publisher("Acme Publishers").price(4999.99).build();
        when(bookService.get()).thenReturn(Lists.newArrayList(book));
        Supplier<List<Book>> cachedSupplier = Suppliers.memoizeWithExpiration(bookListSupplier,10L,TimeUnit.MILLISECONDS);
        bookMapSupplier = Suppliers.compose(function, cachedSupplier);
        Map<String, String> bookMap = bookMapSupplier.get();
        assertThat(bookMap.get("ISBN-98765432"), is("How I Made Millions|Acme Publishers|4999.99"));
        Thread.sleep(15);

        bookMap = bookMapSupplier.get();
        assertThat(bookMap.get("ISBN-98765432"), is("How I Made Millions|Acme Publishers|4999.99"));
        Thread.sleep(15);

        bookMap = bookMapSupplier.get();
        assertThat(bookMap.get("ISBN-98765432"), is("How I Made Millions|Acme Publishers|4999.99"));
        Thread.sleep(15);

        bookMap = bookMapSupplier.get();
        assertThat(bookMap.get("ISBN-98765432"), is("How I Made Millions|Acme Publishers|4999.99"));

        verify(bookService, times(4)).get();
    }


}
