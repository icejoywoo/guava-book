package bbejeck.guava.chapter4;

import bbejeck.guava.common.model.Person;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Bill Bejeck
 * Date: 4/7/13
 * Time: 9:36 PM
 */
public class Collections2Test {
    private Person p1 = new Person("Wilma", "Flintsone", 30, "F");
    private Person p2 = new Person("Fred", "Flintsone", 32, "M");
    private Person p3 = new Person("Betty", "Rubble", 31, "F");
    private Person p4 = new Person("Barney", "Rubble", 33, "M");
    Collection<Person> personList = Lists.newArrayList(p1, p2, p3, p4);

    @Test
    public void testFilter() throws Exception {
        Collection<Person> filtered = Collections2.filter(personList, new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge() > 31;
            }
        });

        assertThat(filtered.contains(p2), is(true));
        assertThat(filtered.contains(p4), is(true));
        assertThat(filtered.contains(p1), is(false));
        assertThat(filtered.contains(p3), is(false));
    }

    @Test
    public void testFilterNoMatch() throws Exception {
        Collection<Person> filtered = Collections2.filter(personList, new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge() < 15;
            }
        });

        assertThat(filtered.isEmpty(), is(true));
    }

    @Test
    public void testTransform() throws Exception {
        Collection<String> transformed = Collections2.transform(personList, new Function<Person, String>() {
            @Override
            public java.lang.String apply(Person input) {

                return Joiner.on('#').join(input.getLastName(), input.getFirstName(), input.getAge());
            }
        });
    }
}
