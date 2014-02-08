package bbejeck.guava.chapter2.strings;

import org.junit.Test;
import static com.google.common.base.Strings.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 3/29/13
 * Time: 8:53 PM
 */
public class StringsTest {

    @Test
    public void testStringPadEnd(){
        String expected = "boom!!!!!!";
        String returned = padEnd("boom",10,'!');
        assertThat(returned,is(expected));
    }


    @Test
    public void testStringPadStart(){
        String expected = "000000000001";
        String returned = padStart("1",12,'0');
        assertThat(returned,is(expected));
    }

    @Test
    public void testNullToEmpty(){
        assertThat(nullToEmpty("foo"),is("foo"));
        assertThat(nullToEmpty(null),is(""));
    }

    @Test
    public void tesEmptyToNull(){
        assertThat(nullToEmpty("foo"),is("foo"));
        assertThat(emptyToNull(""),is(nullValue()));
        assertThat(emptyToNull("  "),is("  "));
    }

    @Test
    public void testIsNullOrEmpty(){
        assertThat(isNullOrEmpty(""),is(true));
        assertThat(isNullOrEmpty("  "),is(false));
        assertThat(isNullOrEmpty(null),is(true));
        assertThat(isNullOrEmpty("foo"),is(false));
    }
}
