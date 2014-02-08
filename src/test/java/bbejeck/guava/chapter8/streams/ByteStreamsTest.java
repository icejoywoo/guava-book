package bbejeck.guava.chapter8.streams;

import com.google.common.io.*;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 5/4/13
 * Time: 2:21 PM
 */
public class ByteStreamsTest {

    @Test
    public void limitByteStreamTest() throws Exception {
        File binaryFile = new File("src/main/resources/sample.pdf");
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(binaryFile));
        InputStream limitedInputStream = ByteStreams.limit(inputStream,10);
        assertThat(limitedInputStream.available(),is(10));
        assertThat(inputStream.available(),is(218882));
    }
}
