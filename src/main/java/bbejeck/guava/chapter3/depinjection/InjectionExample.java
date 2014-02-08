package bbejeck.guava.chapter3.depinjection;

import bbejeck.guava.common.model.Book;
import bbejeck.guava.common.model.City;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.FluentIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: Bill Bejeck
 * Date: 8/7/13
 * Time: 11:15 PM
 */

@Component
public class InjectionExample {

    private Predicate<City> lowRainfallPredicate;
    private Predicate<City> smallPopulationPredicate;
    private Supplier<List<Book>> bookListSupplier;
    private Function<List<Book>, Map<String, String>> bookListConverter;

    @Autowired
    public InjectionExample(Function<List<Book>, Map<String, String>> bookListConverter,
                            Supplier<List<Book>> bookListSupplier,
                            @Qualifier("lowRainfall") Predicate<City> lowRainfallPredicate,
                            @Qualifier("smallPopulation") Predicate<City> smallPopulationPredicate) {

        this.bookListConverter = checkNotNull(bookListConverter);
        this.bookListSupplier = checkNotNull(bookListSupplier);
        this.lowRainfallPredicate = checkNotNull(lowRainfallPredicate);
        this.smallPopulationPredicate = checkNotNull(smallPopulationPredicate);
    }


    public Map<String, String> convertBooks(List<Book> books) {
        return bookListConverter.apply(books);
    }

    public List<Book> getListOfBooks() {
        return bookListSupplier.get();
    }

    public List<City> filterByPopulation(List<City> cities) {
        return FluentIterable.from(cities).filter(smallPopulationPredicate).toList();
    }

    public List<City> filterByRainfall(List<City> cities) {
        return FluentIterable.from(cities).filter(lowRainfallPredicate).toList();
    }
}
