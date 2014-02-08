package bbejeck.guava.chapter2.ascii;

import com.google.common.base.Ascii;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 3/18/13
 * Time: 10:15 AM
 */
public class AsciiTest {

    @Test
    public void testIsLowerCase(){
        assertThat(Ascii.isLowerCase('b'),is(true));
    }

    @Test
    public void testIsUpperCase(){
        assertThat(Ascii.isUpperCase('A'),is(true));
    }

    @Test
    public void testToLowerCase(){
        assertThat(Ascii.toLowerCase("GUAVA"),is("guava"));
    }

    @Test
    public void testToUpperCase(){
        assertThat(Ascii.toUpperCase("guava"),is("GUAVA"));
    }
}
