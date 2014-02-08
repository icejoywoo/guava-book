package bbejeck.guava.chapter2.splitter;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * User: Bill Bejeck
 * Date: 3/17/13
 * Time: 9:39 PM
 */
public class    SplitterTest {

    @Test
    public void testSplitter() {
        String startSring = "Washington D.C=Redskins#New York City=Giants#Philadelphia=Eagles#Dallas=Cowboys";
        Map<String,String> testMap = Maps.newLinkedHashMap();
        testMap.put("Washington D.C","Redskins");
        testMap.put("New York City","Giants");
        testMap.put("Philadelphia","Eagles");
        testMap.put("Dallas","Cowboys");
        Splitter.MapSplitter mapSplitter = Splitter.on("#").withKeyValueSeparator("=");
        Map<String,String> splitMap = mapSplitter.split(startSring);
        assertThat(testMap,is(splitMap));
    }

    @Test
    public void testSplitPattern(){
        String pattern = "\\d+";
        String text = "foo123bar45678baz";
        String[] expected = new String[]{"foo","bar","baz"};
        Iterable<String> values = Splitter.on(Pattern.compile(pattern)).split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value,is(expected[index++]));
        }
    }

    @Test
    public void testSplitStringPattern(){
        String pattern = "\\d+";
        String text = "foo123bar45678baz";
        String[] expected = new String[]{"foo","bar","baz"};
        Iterable<String> values = Splitter.onPattern(pattern).split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value,is(expected[index++]));
        }
    }

    @Test
     public void testSplit(){
        String delimiter = "&";
        String text = "foo&bar&baz";
        String[] expected = new String[]{"foo","bar","baz"};
        Iterable<String> values = Splitter.on(delimiter).split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value,is(expected[index++]));
        }
    }

    @Test
    public void testSplitTrimResults(){
        String delimiter = "&";
        String text = "foo   &  bar&   baz  ";
        String[] expected = new String[]{"foo","bar","baz"};
        Iterable<String> values = Splitter.on(delimiter).trimResults().split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value,is(expected[index++]));
        }
    }

    @Test
    public void testSplitTrimResultsII(){
        String delimiter = "&";
        String text = "1foo&bar2&2baz3";
        String[] expected = new String[]{"foo","bar","baz"};
        Iterable<String> values = Splitter.on(delimiter).trimResults(CharMatcher.JAVA_DIGIT).split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value,is(expected[index++]));
        }
    }

    @Test
    public void testSplitOnCharacter(){
        char delimiter = '|';
        String text = "foo|bar|baz";
        String[] expected = new String[]{"foo","bar","baz"};
        Iterable<String> values = Splitter.on(delimiter).split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value,is(expected[index++]));
        }
    }

    @Test
    public void testSplitOnCharacterKeepMissing(){
        char delimiter = '|';
        String text = "foo|bar|||baz";
        String[] expected = new String[]{"foo","bar","","","baz"};
        Iterable<String> values = Splitter.on(delimiter).split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value,is(expected[index++]));
        }
    }

    @Test
    public void testSplitOnCharacterOmitMissing(){
        char delimiter = '|';
        String text = "foo|bar|||baz";
        String[] expected = new String[]{"foo","bar","baz"};
        Iterable<String> values = Splitter.on(delimiter).omitEmptyStrings().split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value,is(expected[index++]));
        }
    }
}
