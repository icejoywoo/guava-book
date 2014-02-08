package bbejeck.guava.chapter8.sink;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 5/4/13
 * Time: 11:19 PM
 */
public class CharSinkTest {

    CharSink charSink;
    File sinkFile;

    @Before
    public void setUp(){
        sinkFile = new File("src/test/resources/sinkExample.txt");
        sinkFile.deleteOnExit();
        charSink = Files.asCharSink(sinkFile, Charsets.UTF_8);
    }


    @Test
    public void stringToCharSinkTest() throws Exception {
        String string = "the quick brown fox jumps over the lazy dog";
        charSink.write(string);
        assertThat(Files.toString(sinkFile,Charsets.UTF_8),is(string));
    }

    @Test
    public void stringListToCharSinkTest() throws Exception {
        List<String> list = Lists.newArrayList("the quick brown","fox jumps over","the lazy dog");
        String expectedContent = "the quick brown\nfox jumps over\nthe lazy dog\n";
        charSink.writeLines(list);
        assertThat(Files.toString(sinkFile,Charsets.UTF_8),is(expectedContent));
    }

    @Test
    public void readerToCharSinkTest() throws Exception {
        File source = new File("src/main/resources/sampleTextFileOne.txt");
        BufferedReader reader = Files.newReader(source,Charsets.UTF_8);
        charSink.writeFrom(reader);
        assertThat(Files.toString(sinkFile,Charsets.UTF_8),is(Files.toString(source,Charsets.UTF_8)));
    }
}
