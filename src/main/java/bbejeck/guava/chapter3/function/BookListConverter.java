package bbejeck.guava.chapter3.function;

import bbejeck.guava.common.model.Book;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * User: Bill Bejeck
 * Date: 4/5/13
 * Time: 1:44 PM
 */
public class BookListConverter implements Function<List<Book>, Map<String, String>> {

    @Override
    public Map<String, String> apply(List<Book> input) {
        Map<String,String> map = Maps.newHashMap();
        Joiner joiner = Joiner.on("|");
        for (Book book : input) {
              map.put(book.getIsbn(),joiner.join(book.getTitle(),book.getPublisher(),book.getPrice()));
        }
       return map;
    }
}
