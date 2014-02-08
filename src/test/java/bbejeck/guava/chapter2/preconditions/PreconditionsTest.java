package bbejeck.guava.chapter2.preconditions;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static com.google.common.base.Preconditions.*;
/**
 * User: Bill Bejeck
 * Date: 3/20/13
 * Time: 10:13 PM
 */
public class PreconditionsTest {

    @Test(expected = NullPointerException.class)
    public void testNotNull(){
        //Making change for testing
        checkNotNull(null, "Message null");
    }

    @Test
    public void testNotNullValid(){
        //Making change for testing
        String value = "foo";
        String returned = checkNotNull(value,"Message null");
        assertThat(returned,is(value));
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithAsserts(){
       new  PreconditionExample(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidValue(){
       PreconditionExample example = new PreconditionExample("testing");
        //This works value less than 100
        example.updateCurrentIndexValue(0,99);
        assertThat(example.getValues()[0],is(99));
        //Won't accept values greater than
        example.updateCurrentIndexValue(0,1000);
        fail("Should not get here");
    }

    @Test(expected = IllegalStateException.class)
    public void testAttemptOperation(){
        PreconditionExample example = new PreconditionExample("open");
        //This works value less than 100
        example.updateCurrentIndexValue(0,10);
        example.doOperation();
        //Won't accept values greater than
        example.updateCurrentIndexValue(0,1);
        example.doOperation();
        fail("Should not get here");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testUpdateInvalidIndex(){
        PreconditionExample example = new PreconditionExample("testing");
        //This works size of array is 5
        example.updateCurrentIndexValue(4,99);
        assertThat(example.getValues()[4],is(99));
        //Won't work index out of bounds
        example.updateCurrentIndexValue(25,10);
        fail("Should not get here");
    }
}
