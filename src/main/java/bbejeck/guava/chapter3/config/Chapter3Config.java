package bbejeck.guava.chapter3.config;

import bbejeck.guava.chapter3.function.BookListConverter;
import bbejeck.guava.chapter3.predicate.LowRainfallPredicate;
import bbejeck.guava.chapter3.predicate.SmallPopulationPredicate;
import bbejeck.guava.chapter3.supplier.BookListSupplier;
import bbejeck.guava.common.model.Book;
import bbejeck.guava.common.model.City;
import bbejeck.guava.common.service.BookServiceImpl;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * User: Bill Bejeck
 * Date: 4/5/13
 * Time: 5:14 PM
 */
@Configuration
@ComponentScan(basePackages = {"bbejeck.guava.chapter3.depinjection"})

public class Chapter3Config {

    @Bean(autowire = Autowire.BY_NAME, name = "lowRainfall")
    public Predicate<City> lowRainFallPredicate() {
        return new LowRainfallPredicate();
    }

    @Bean(autowire = Autowire.BY_NAME, name = "smallPopulation")
    public Predicate<City> smallPopulationPredicate() {
        return new SmallPopulationPredicate();
    }

    @Bean
    public Supplier<List<Book>> bookListSupplier() {
        return new BookListSupplier(new BookServiceImpl());
    }

    @Bean
    Function<List<Book>, Map<String, String>> bookListConverter() {
        return new BookListConverter();
    }


}
