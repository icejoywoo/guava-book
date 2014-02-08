package bbejeck.guava.chapter6.mapmaker;

import bbejeck.guava.common.model.Book;
import com.google.common.collect.MapMaker;
import org.junit.Test;

import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * User: Bill Bejeck
 * Date: 4/19/13
 * Time: 8:38 AM
 */

public class LowMemoryMapMakerTests {

    @Test
    public void testSoftValuesEntriesRemovedWhenMemoryLow() throws Exception {
        ConcurrentMap<String, Book> books;
        books = new MapMaker()
                .softValues()
                .makeMap();
        books.put("Key1", new Book.Builder().title("book1").build());
        books.put("Key2", new Book.Builder().title("book2").build());
        books.put("Key3", new Book.Builder().title("book3").build());


        assertThat(books.get("Key1").getTitle(), is("book1"));
        assertThat(books.get("Key2").getTitle(), is("book2"));
        assertThat(books.get("Key3").getTitle(), is("book3"));

        try {
            Object[] memHog = new Object[(int) Runtime.getRuntime().maxMemory()];
        } catch (Throwable e) {
            //Don't care
        }

        assertThat(books.get("Key1"), is(nullValue()));
        assertThat(books.get("Key2"), is(nullValue()));
        assertThat(books.get("Key3"), is(nullValue()));
    }

    @Test
    public void testWeakValuesEntriesRemovedWhenMemoryLow() throws Exception {
        ConcurrentMap<String, Book> books;
        books = new MapMaker()
                .weakValues()
                .makeMap();
        books.put("Key1", new Book.Builder().title("book1").build());
        books.put("Key2", new Book.Builder().title("book2").build());
        books.put("Key3", new Book.Builder().title("book3").build());


        assertThat(books.get("Key1").getTitle(), is("book1"));
        assertThat(books.get("Key2").getTitle(), is("book2"));
        assertThat(books.get("Key3").getTitle(), is("book3"));

        try {
            Object[] memHog = new Object[(int) Runtime.getRuntime().maxMemory()];
        } catch (Throwable e) {
            //Don't care
        }

        assertThat(books.get("Key1"), is(nullValue()));
        assertThat(books.get("Key2"), is(nullValue()));
        assertThat(books.get("Key3"), is(nullValue()));
    }


    @Test
    public void testEntriesNotRemovedWhenMemoryLow() throws Exception {

        ConcurrentMap<String, Book> books;
        books = new MapMaker().concurrencyLevel(2)
                .makeMap();
        books.put("Key1", new Book.Builder().title("book1").build());
        books.put("Key2", new Book.Builder().title("book2").build());
        books.put("Key3", new Book.Builder().title("book3").build());


        assertThat(books.get("Key1").getTitle(), is("book1"));
        assertThat(books.get("Key2").getTitle(), is("book2"));
        assertThat(books.get("Key3").getTitle(), is("book3"));

        try {
            Object[] memHog = new Object[(int) Runtime.getRuntime().maxMemory()];
        } catch (Throwable e) {
            //Don't care
        }

        assertThat(books.get("Key1").getTitle(), is("book1"));
        assertThat(books.get("Key2").getTitle(), is("book2"));
        assertThat(books.get("Key3").getTitle(), is("book3"));
    }
}
