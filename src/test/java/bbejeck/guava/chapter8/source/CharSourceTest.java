package bbejeck.guava.chapter8.source;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 5/4/13
 * Time: 11:19 PM
 */
public class CharSourceTest {

    private CharSource charSource;

    @Before
    public void setUp(){
        File sourceFile = new File("src/main/resources/lines.txt");
        charSource = Files.asCharSource(sourceFile, Charsets.UTF_8);
    }

    @Test
    public void charSourceReadStringTest() throws Exception {
        String expectedContent = "The quick brown\nfox jumps over\nthe lazy dog";
        String readString = charSource.read();
        assertThat(readString,is(expectedContent));
    }

    @Test
    public void charSourceReadLinesTest() throws Exception {
        List<String> expectedList = Lists.newArrayList("The quick brown", "fox jumps over", "the lazy dog");
        List<String> readList = charSource.readLines();
        assertThat(readList,is(expectedList));
    }

    @Test
    public void copyToCharSinkTest() throws Exception {
        File source = new File("src/main/resources/sampleTextFileTwo.txt");
        File dest = new File("src/test/resources/sampleCopy.txt");
        dest.deleteOnExit();
        charSource = Files.asCharSource(source,Charsets.UTF_8);
        CharSink charSink = Files.asCharSink(dest,Charsets.UTF_8);
        charSource.copyTo(charSink);
        assertThat(Files.toString(dest,Charsets.UTF_8),is(Files.toString(source,Charsets.UTF_8)));
    }
}
