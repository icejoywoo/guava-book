package bbejeck.guava.chapter8.encoding;

import bbejeck.guava.chapter8.sink.CharSinkTest;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.*;
import org.junit.Test;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Bill Bejeck
 * Date: 5/6/13
 * Time: 11:57 PM
 */
public class BaseEncodingTest {

    @Test
    public void encodeByteSinkTest()  throws Exception{
        File file = new File("src/main/resources/sample.pdf");
        File encodedFile = new File("src/main/resources/encoded.txt");
        encodedFile.deleteOnExit();
        CharSink charSink = Files.asCharSink(encodedFile, Charsets.UTF_8);

        BaseEncoding baseEncoding = BaseEncoding.base64();
        ByteSink byteSink = baseEncoding.encodingSink(charSink);
        ByteSource byteSource = Files.asByteSource(file);
        byteSource.copyTo(byteSink);

        String encodedBytes = baseEncoding.encode(byteSource.read());
        assertThat(encodedBytes,is(Files.toString(encodedFile,Charsets.UTF_8)));
    }

    @Test
    public void encodeDecodeTest() throws Exception {
        File file = new File("src/main/resources/sample.pdf");
        byte[] bytes = Files.toByteArray(file);
        BaseEncoding baseEncoding = BaseEncoding.base64();
        String encoded = baseEncoding.encode(bytes);
        assertThat(Pattern.matches("[A-Za-z0-9+/]+={1,2}", encoded), is(true));
        assertThat(baseEncoding.decode(encoded), is(bytes));
    }

    @Test
    public void encodeDecodeTestWithFormatting() throws Exception {
        File file = new File("src/main/resources/sample.pdf");
        byte[] bytes = Files.toByteArray(file);
        BaseEncoding baseEncoding = BaseEncoding.base64().withSeparator("\n", 50);
        String encoded = baseEncoding.encode(bytes);
        Iterable<String> parts = Splitter.on('\n').split(encoded);
        Pattern regex = Pattern.compile("([A-Za-z0-9/+]{40,50})(={1,2})?");
        for (String part : parts) {
            Matcher matcher = regex.matcher(part);
            assertThat(matcher.matches(), is(true));
        }
        assertThat(baseEncoding.decode(encoded), is(bytes));
    }
}
