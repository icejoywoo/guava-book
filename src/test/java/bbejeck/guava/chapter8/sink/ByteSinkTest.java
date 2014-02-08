package bbejeck.guava.chapter8.sink;

import com.google.common.io.ByteSink;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Bill Bejeck
 * Date: 5/4/13
 * Time: 12:33 PM
 */
public class ByteSinkTest {

    ByteSink byteSink;

    @Test
    public void testCreateFileByteSink() throws Exception {
        File dest = new File("src/test/resources/byteSink.pdf");
        dest.deleteOnExit();
        byteSink = Files.asByteSink(dest);
        File file = new File("src/main/resources/sample.pdf");
        byteSink.write(Files.toByteArray(file));
        assertThat(Files.toByteArray(dest), is(Files.toByteArray(file)));
    }

    @Test
    public void testWriteFromInputStream() throws Exception {
        File dest = new File("src/test/resources/byteSink.pdf");
        dest.deleteOnExit();
        byteSink = Files.asByteSink(dest);
        File sourceFile = new File("src/main/resources/sample.pdf");
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
        byteSink.writeFrom(inputStream);
        assertThat(Files.toByteArray(dest), is(Files.toByteArray(sourceFile)));
    }
}
