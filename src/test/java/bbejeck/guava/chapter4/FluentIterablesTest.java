package bbejeck.guava.chapter4;

import bbejeck.guava.common.model.Person;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Bill Bejeck
 * Date: 4/7/13
 * Time: 9:36 PM
 */
public class FluentIterablesTest {
    private Person person1;
    private Person person2;
    private Person person3;
    private Person person4;
    Collection<Person> personList;

    @Before
    public void setUp() {
        person1 = new Person("Wilma", "Flintstone", 30, "F");
        person2 = new Person("Fred", "Flintstone", 32, "M");
        person3 = new Person("Betty", "Rubble", 31, "F");
        person4 = new Person("Barney", "Rubble", 33, "M");
        personList = Lists.newArrayList(person1, person2, person3, person4);
    }


    @Test
    public void testFilter() throws Exception {
        Iterable<Person> personsFilteredByAge = FluentIterable.from(personList).filter(new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge() > 31;
            }
        });

        assertThat(Iterables.contains(personsFilteredByAge, person2), is(true));
        assertThat(Iterables.contains(personsFilteredByAge, person4), is(true));
        assertThat(Iterables.contains(personsFilteredByAge, person1), is(false));
        assertThat(Iterables.contains(personsFilteredByAge, person3), is(false));
    }

    @Test
    public void testAnyMatch() throws Exception {
        Predicate<Person> personOlderThan31Predicate =  new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge() > 31;
            }
        };

        assertThat(FluentIterable.from(personList).anyMatch(personOlderThan31Predicate),is(true));
    }

    @Test
    public void testAllMatch() throws Exception {
        Predicate<Person> personOlderThan25Predicate =  new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge() > 25;
            }
        };

        assertThat(FluentIterable.from(personList).allMatch(personOlderThan25Predicate),is(true));
    }


    @Test
    public void testFilterNoMatch() throws Exception {
        Iterable<Person> filtered = FluentIterable.from(personList).filter(new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge() < 15;
            }
        });

        assertThat(Iterables.isEmpty(filtered), is(true));
    }

    @Test
    public void testTransform() throws Exception {
        List<String> transformed = FluentIterable.from(personList).transform(new Function<Person, String>() {
            @Override
            public String apply(Person input) {

                return Joiner.on('#').join(input.getLastName(), input.getFirstName(), input.getAge());
            }
        }).toList();
        assertThat(transformed.get(1), is("Flintstone#Fred#32"));
    }


}
