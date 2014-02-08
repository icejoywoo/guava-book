package bbejeck.guava.chapter2.charsets;

import com.google.common.base.Charsets;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 3/18/13
 * Time: 11:22 AM
 */
public class CharsetsTest {


    @Test
    public void testCharsets(){
        byte[] bytes = null;
        try{
            bytes = "foobarbaz".getBytes("UTF-8");
        }catch (UnsupportedEncodingException e){
           //This really can't happen UTF-8 must be supported
        }

        byte[] bytes2 = "foobarbaz".getBytes(Charsets.UTF_8);

        assertThat(bytes,is(bytes2));
    }
}
