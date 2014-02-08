package bbejeck.guava.chapter4;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Bill Bejeck
 * Date: 4/13/13
 * Time: 10:48 PM
 */
public class ImmutableCollectionsTest {

    @Test
    public void testCreateImmutableMap() {
        ImmutableMultimap<Integer, String> multimap = new ImmutableMultimap.Builder<Integer, String>().put(1, "Foo")
                .putAll(2, "Foo", "Bar", "Baz")
                .putAll(4, "Huey", "Duey", "Luey")
                .put(3, "Single").build();
        Collection<String> list = Lists.newArrayList("Single");
        assertThat(multimap.get(3), is(list));
        list = Lists.newArrayList("Huey", "Duey", "Luey");
        assertThat(multimap.get(4), is(list));

    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddImmutableCollection() {
        ImmutableList<String> immutableList = new ImmutableList.Builder<String>().add("foo").add("bar").build();
        immutableList.add("baz");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveImmuableCollection() {
        ImmutableList<String> immutableList = new ImmutableList.Builder<String>().add("foo").add("bar").build();
        immutableList.remove("bar");
    }

    @Test
    public void testChangeObjectImmutableCollection() {
        MutableObject mutableObject = new MutableObject("initialState");
        ImmutableList<MutableObject> immutableList = new ImmutableList.Builder<MutableObject>().add(mutableObject).build();
        assertThat(immutableList.get(0).getMutableProperty(), is("initialState"));
        immutableList.get(0).setMutableProperty("changedState");
        assertThat(immutableList.get(0).getMutableProperty(), is("changedState"));
    }


    private class MutableObject {
        private String mutableProperty;

        private MutableObject(String mutableProperty) {
            this.mutableProperty = mutableProperty;
        }

        private String getMutableProperty() {
            return mutableProperty;
        }

        private void setMutableProperty(String mutableProperty) {
            this.mutableProperty = mutableProperty;
        }
    }
}
