package bbejeck.guava.chapter2.charmatcher;

import com.google.common.base.CharMatcher;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 3/19/13
 * Time: 11:04 PM
 */
public class CharMatcherTest {

    @Test
    public void testRemoveLinebreaks(){
        String stringWithLinebreaks = "This is an example\n"+
                                      "of a String with linebreaks\n"+
                                      "we want on one line";
        String expected = "This is an example of a String with linebreaks we want on one line";
        String scrubbed = CharMatcher.BREAKING_WHITESPACE.replaceFrom(stringWithLinebreaks,' ');
        assertThat(scrubbed,is(expected));
    }

    @Test
    public void testRemoveWhiteSpace(){
        String tabsAndSpaces = "String  with      spaces     and           tabs";
        String expected = "String with spaces and tabs";
        String scrubbed = CharMatcher.WHITESPACE.collapseFrom(tabsAndSpaces,' ');
        assertThat(scrubbed,is(expected));
    }

    @Test
    public void testTrimRemoveWhiteSpace(){
        String tabsAndSpaces = "    String  with      spaces     and           tabs";
        String expected = "String with spaces and tabs";
        String scrubbed = CharMatcher.WHITESPACE.trimAndCollapseFrom(tabsAndSpaces,' ');
        assertThat(scrubbed,is(expected));
    }

    @Test
    public void testRetainFrom(){
        String lettersAndNumbers = "foo989yxbar234";
        String expected = "989234";
        String retained = CharMatcher.JAVA_DIGIT.retainFrom(lettersAndNumbers);
        assertThat(expected,is(retained));
    }

    @Test
    public void testCombineMatchers(){
        CharMatcher cm = CharMatcher.inRange('A','E');
        assertThat(cm.retainFrom("aaaABbbccCdddDEeee"),is("ABCDE"));
    }
}
