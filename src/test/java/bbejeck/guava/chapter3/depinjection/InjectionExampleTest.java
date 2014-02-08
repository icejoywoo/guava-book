package bbejeck.guava.chapter3.depinjection;

import bbejeck.guava.chapter3.config.Chapter3Config;
import bbejeck.guava.chapter7.EventBusTestBase;
import bbejeck.guava.common.model.Book;
import bbejeck.guava.common.model.City;
import com.google.common.collect.Lists;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Bill Bejeck
 * Date: 8/7/13
 * Time: 11:31 PM
 */
public class InjectionExampleTest extends EventBusTestBase {

    private static InjectionExample example;
    private static List<City> cities;
    private static City city = new City.Builder().averageRainfall(55.9).population(400000).build();
    private static City city2 = new City.Builder().averageRainfall(24.3).population(1000000).build();

    @BeforeClass
    public static void setUp() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(Chapter3Config.class);
        example = context.getBean(InjectionExample.class);
        cities = Lists.newArrayList(city, city2);
    }

    @Test
    public void testConvertBooks() throws Exception {
        Book b = new Book.Builder().isbn("1234567").title("book1").publisher("acme").price(59.99).build();
        Book b2 = new Book.Builder().isbn("23456878").title("book2").publisher("acme").price(39.99).build();
        List<Book> books = Lists.newArrayList(b, b2);
        Map<String, String> bookMap = example.convertBooks(books);
        String expectedValue = "book1|acme|59.99";
        assertThat(bookMap.get("1234567"), is(expectedValue));
    }

    @Test
    public void testGetListOfBooks() throws Exception {
        List<Book> books = example.getListOfBooks();
        assertThat(books.size(), is(5));
    }

    @Test
    public void testFilterByPopulation() throws Exception {
        List<City> cityList = example.filterByPopulation(cities);
        assertThat(cityList.size(), is(1));
        assertThat(cityList.get(0), is(city));

    }

    @Test
    public void testFilterByRainfall() throws Exception {
        List<City> cityList = example.filterByRainfall(cities);
        assertThat(cityList.size(), is(1));
        assertThat(cityList.get(0), is(city2));

    }
}
