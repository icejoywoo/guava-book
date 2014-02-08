package bbejeck.guava.chapter9.bloomfilter;

import bbejeck.guava.common.model.Book;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.hash.BloomFilter;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: Bill Bejeck
 * Date: 5/10/13
 * Time: 7:13 AM
 */
public class BloomFilterExample {

    public static void main(String[] args) throws Exception {
        File booksPipeDelimited = new File("src/main/resources/books.data");

        List<Book> books = Files.readLines(booksPipeDelimited, Charsets.UTF_8, new LineProcessor<List<Book>>() {
            Splitter splitter = Splitter.on('|');
            List<Book> books = Lists.newArrayList();
            Book.Builder builder = new Book.Builder();

            public boolean processLine(String line) throws IOException {
                List<String> parts = Lists.newArrayList(splitter.split(line));
                builder.author(parts.get(0))
                        .title(parts.get(1))
                        .publisher(parts.get(2))
                        .isbn(parts.get(3))
                        .price(Double.parseDouble(parts.get(4)));
                books.add(builder.build());
                return true;
            }

            @Override
            public List<Book> getResult() {
                return books;
            }
        });

        BloomFilter<Book> bloomFilter = BloomFilter.create(BookFunnel.FUNNEL, 5);

        for (Book book : books) {
             bloomFilter.put(book);
        }

        Book book1 = books.get(0);
        Book newBook = new Book.Builder().title("Mountain Climbing").build();
        System.out.println("Book ["+book1.getTitle()+"] contained "+bloomFilter.mightContain(book1));
        System.out.println("Book ["+newBook.getTitle()+"] contained "+bloomFilter.mightContain(newBook));
    }
}
