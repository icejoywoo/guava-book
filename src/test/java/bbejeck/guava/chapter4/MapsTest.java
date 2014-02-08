package bbejeck.guava.chapter4;


import bbejeck.guava.common.model.Book;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * User: Bill Bejeck
 * Date: 4/8/13
 * Time: 10:43 PM
 */
public class MapsTest {
   private  Book book = new Book.Builder().isbn("ISBN123").title("book1").build();
   private  Book book2 = new Book.Builder().isbn("ISBN456").title("book2").build();
   private  Book book3 = new Book.Builder().isbn("ISBN789").title("book3").build();
   private  List<Book> books = Lists.newArrayList(book,book2,book3);

    @Test
    public void uniqueIndexTest(){
        Map<String,Book> bookMap = Maps.uniqueIndex(books,new Function<Book, String>() {
            @Override
            public String apply(Book book) {
                return book.getIsbn();
            }
        });
       assertThat(bookMap.get("ISBN123"),is(book));
       assertThat(bookMap.get("ISBN456"),is(book2));
       assertThat(bookMap.get("ISBN789"),is(book3));
    }

    @Test
    public void asMapTest(){
        Set<Book> bookSet = Sets.newHashSet(books);
        Map<Book,String> bookToIsbn = Maps.asMap(bookSet,new Function<Book, String>() {
            @Override
            public String apply(Book book) {
                return book.getIsbn();
            }
        });
        assertThat(bookToIsbn.get(book),is("ISBN123"));
        assertThat(bookToIsbn.get(book2),is("ISBN456"));
        assertThat(bookToIsbn.get(book3),is("ISBN789"));
    }

    @Test
    public void transformValuesTest(){
        Map<String,Book> bookMap = Maps.uniqueIndex(books,new Function<Book, String>() {
            @Override
            public String apply(Book book) {
                return book.getIsbn();
            }
        });

        Map<String,String> map = Maps.transformValues(bookMap,new Function<Book, String>() {
            @Override
            public String apply(Book book) {
                 return book.getTitle();
            }
        });

        assertThat(map.get("ISBN123"),is("book1"));
        assertThat(map.get("ISBN456"),is("book2"));
        assertThat(map.get("ISBN789"),is("book3"));
    }

    @Test
    public void transformEntriesTest(){
        Map<String,Book> bookMap = Maps.uniqueIndex(books,new Function<Book, String>() {
            @Override
            public String apply(Book book) {
                return book.getIsbn();
            }
        });

        Map<String,String> map = Maps.transformEntries(bookMap,new Maps.EntryTransformer<String,Book,String>(){
            @Override
            public String transformEntry(String key, Book value) {
                StringBuilder builder = new StringBuilder();
                return builder.append(key).append("|").append(value.getTitle()).toString();
            }
        });

        assertThat(map.get("ISBN123"),is("ISBN123|book1"));
        assertThat(map.get("ISBN456"),is("ISBN456|book2"));
        assertThat(map.get("ISBN789"),is("ISBN789|book3"));
    }

}
