package bbejeck.guava.chapter2.ascii;

import org.junit.Test;

import com.google.common.base.CaseFormat;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 3/18/13
 * Time: 10:36 AM
 */
public class CaseFormatTest {

    @Test
    public void testToLowerCamelCaseFromLowerHyphen(){
        assertThat(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL,"foo-bar"),is("fooBar"));
    }

    @Test
    public void testToUpperCamelCaseFromLowerUnderScore(){
        assertThat(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,"foo_bar"),is("FooBar"));
    }

    @Test
    public void testToUpperUnderScoreFromLowerCamelCase(){
        assertThat(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE,"fooBar"),is("FOO_BAR"));
    }
}
