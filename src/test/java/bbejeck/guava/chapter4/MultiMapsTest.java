package bbejeck.guava.chapter4;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 4/9/13
 * Time: 11:46 PM
 */
public class MultiMapsTest {


    @Test
    public void testArrayListMultiMap(){
        ArrayListMultimap<String,String> multiMap = ArrayListMultimap.create();
        multiMap.put("Foo","1");
        multiMap.put("Foo","2");
        multiMap.put("Foo","3");
        List<String> expected = Lists.newArrayList("1","2","3");
        assertEquals(multiMap.get("Foo"),expected);
    }

    @Test
    public void testArrayListMultiMapSize(){
        ArrayListMultimap<String,String> multiMap = ArrayListMultimap.create();
        multiMap.put("Foo","1");
        multiMap.put("Foo","2");
        multiMap.put("Foo","3");
        multiMap.put("Bar","1");
        multiMap.put("Bar","2");
        multiMap.put("Bar","3");
        Collection<String> expected = Lists.newArrayList("1","2","3","1","2","3");
        assertThat(multiMap.size(),is(6));
        assertArrayEquals(multiMap.values().toArray(), expected.toArray());
    }

    @Test
    public void testArrayListMultiMapSizeAsMap(){
        ArrayListMultimap<String,String> multiMap = ArrayListMultimap.create();
        multiMap.put("Foo","1");
        multiMap.put("Foo","2");
        multiMap.put("Foo","3");
        multiMap.put("Bar","1");
        multiMap.put("Bar","2");
        multiMap.put("Bar","3");
        Map<String,Collection<String>> map = multiMap.asMap();
        assertThat(map.size(),is(2));
        map.get("Foo").remove("3");
        assertThat(multiMap.size(), is(5));
        multiMap.put("Baz", "1");
        assertThat(map.size(), is(3));
        map.get("Foo").add("4");
        assertThat(multiMap.size(), is(7));

    }

    @Test
    public void testArrayListMultimapSameKeyValue(){
        ArrayListMultimap<String,String> multiMap = ArrayListMultimap.create();
        multiMap.put("Bar","1");
        multiMap.put("Bar","3");
        multiMap.put("Bar","2");
        multiMap.put("Bar","3");
        multiMap.put("Bar","3");
        List<String> expected = Lists.newArrayList("1","3","2","3","3");
        assertEquals(multiMap.get("Bar"),expected);
    }

    @Test
    public void testHashMultiMapSameKeyValue(){
        HashMultimap<String,String> multiMap = HashMultimap.create();
        multiMap.put("Bar","1");
        multiMap.put("Bar","2");
        multiMap.put("Bar","3");
        multiMap.put("Bar","3");
        multiMap.put("Bar","3");
        assertThat(multiMap.size(),is(3));
    }
}
