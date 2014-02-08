package bbejeck.guava.chapter8.source;

import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Bill Bejeck
 * Date: 5/4/13
 * Time: 11:46 AM
 */
public class ByteSourceTest {


    @Test
    public void createByteSourceFromFileTest() throws Exception {
        File f1 = new File("src/main/resources/sample.pdf");
        ByteSource byteSource = Files.asByteSource(f1);
        byte[] readBytes = byteSource.read();
        assertThat(readBytes, is(Files.toByteArray(f1)));
    }

    @Test
    public void createByteSourceFromByteArray() throws Exception {
        byte[] bytes = new byte[]{0xF, 0xF, 0xF, 0x3, 0x3};
        ByteSource byteSource = ByteStreams.asByteSource(bytes);
        byte[] readBytes = byteSource.read();
        assertThat(readBytes, is(bytes));
    }

    @Test
    public void createByteSliceTest() throws Exception {
        byte[] bytes = new byte[]{0xF, 0xF, 0xF, 0x3, 0x3, 0xA, 0xA, 0xA, 0xA, 0xA};
        byte[] expectedSlice = new byte[]{0xA, 0xA, 0xA, 0xA, 0xA};
        ByteSource byteSource = ByteStreams.asByteSource(bytes);
        ByteSource slice = byteSource.slice(5, 10);
        assertThat(slice.read(), is(expectedSlice));
    }

    @Test
    public void copyToByteSinkTest() throws Exception {
        File dest = new File("src/test/resources/sampleCompany.pdf");
        dest.deleteOnExit();
        File source = new File("src/main/resources/sample.pdf");
        ByteSource byteSource = Files.asByteSource(source);
        ByteSink byteSink = Files.asByteSink(dest);
        byteSource.copyTo(byteSink);
        assertThat(Files.toByteArray(dest), is(Files.toByteArray(source)));
    }

}
