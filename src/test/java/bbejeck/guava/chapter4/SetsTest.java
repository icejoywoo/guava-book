package bbejeck.guava.chapter4;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 4/13/13
 * Time: 9:12 PM
 */
public class SetsTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testCartesianProduct(){
        Set<String> s1 = Sets.newHashSet("Foo","Bar");
        Set<String> s2 = Sets.newHashSet("Jim","Bob");
        Set<List<String>> cartesian  = Sets.cartesianProduct(s1, s2);
        List<String> list = Lists.newArrayList();
        list.add("Foo");
        list.add("Jim");
        assertThat(cartesian.contains(list),is(true));
        list.clear();
        list.add("Foo");
        list.add("Bob");
        assertThat(cartesian.contains(list),is(true));
        list.clear();
        list.add("Bar");
        list.add("Jim");
        assertThat(cartesian.contains(list),is(true));
        list.clear();
        list.add("Bar");
        list.add("Bob");
        assertThat(cartesian.contains(list),is(true));
    }

    @Test
    public void testSetDifference() {
        Set<String> s1 = Sets.newHashSet("1","2","3");
        Set<String> s2 = Sets.newHashSet("3","2","4");
        Sets.SetView<String> sv = Sets.difference(s1,s2);
        assertThat(sv.size()==1 && sv.contains("1"),is(true));

        sv = Sets.difference(s2,s1);
        assertThat(sv.size()==1 && sv.contains("4"),is(true));
    }

    @Test
    public void testSymmetricDifference(){
        Set<String> s1 = Sets.newHashSet("1","2","3");
        Set<String> s2 = Sets.newHashSet("3","2","4");
        Sets.SetView<String> sv = Sets.symmetricDifference(s1,s2);
        assertThat(sv.size()==2 && sv.contains("1") && sv.contains("4"),is(true));
    }

    @Test
    public void testIntersection(){
        Set<String> s1 = Sets.newHashSet("1","2","3");
        Set<String> s2 = Sets.newHashSet("3","2","4");
        Sets.SetView<String> sv = Sets.intersection(s1,s2);
        assertThat(sv.size()==2 && sv.contains("2") && sv.contains("3"),is(true));
    }

    @Test
    public void testUnion(){
        Set<String> s1 = Sets.newHashSet("1","2","3");
        Set<String> s2 = Sets.newHashSet("3","2","4");
        Sets.SetView<String> sv = Sets.union(s1,s2);
        assertThat(sv.size()==4 &&
                sv.contains("2") &&
                sv.contains("3") &&
                sv.contains("4") &&
                sv.contains("1"),is(true));
    }

}
