package bbejeck.guava.chapter2.joiner;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.Writer;
import java.util.Map;

/**
 * User: Bill Bejeck
 * Date: 3/15/13
 * Time: 9:04 PM
 *
 * This class contains various unit tests for working with the Joiner class
 */
public class JoinerTest {



    @Test
    public void testMapJoiner() {
        //Using LinkedHashMap so that the original order is preserved
        String expectedString = "Washington D.C=Redskins#New York City=Giants#Philadelphia=Eagles#Dallas=Cowboys";
        Map<String,String> testMap = Maps.newLinkedHashMap();
        testMap.put("Washington D.C","Redskins");
        testMap.put("New York City","Giants");
        testMap.put("Philadelphia","Eagles");
        testMap.put("Dallas","Cowboys");
        String returnedString = Joiner.on("#").withKeyValueSeparator("=").join(testMap);
        assertThat(returnedString,is(expectedString));
    }

    @Test
    public void testJoinStrings(){
        String[] values = new String[]{"foo", "bar","baz"};
        String returned = Joiner.on("|").join(values);
        assertThat(returned,is("foo|bar|baz"));
    }

    @Test
    public void testJoinStringBuilder(){
        String[] values = new String[]{"foo", "bar","baz"};
        StringBuilder builder = new StringBuilder();
        StringBuilder returned = Joiner.on("|").appendTo(builder,values);
        assertThat(returned,is(builder));
        assertThat(returned.toString(),is("foo|bar|baz"));
    }

    @Test
    public void testJoinFileWriter() throws Exception{
        File tempFile = new File("testTempFile.txt");
        tempFile.deleteOnExit();
        CharSink charSink = Files.asCharSink(tempFile, Charsets.UTF_8);
        Writer writer = charSink.openStream();
        String[] values = new String[]{"foo", "bar","baz"};
        Joiner.on("|").appendTo(writer,values);
        writer.flush();
        writer.close();
        String fromFileString = Files.toString(tempFile,Charsets.UTF_8);
        assertThat(fromFileString,is("foo|bar|baz"));

    }

    @Test
    public void testJoinStringsSkipNull(){
        String[] values = new String[]{"foo",null,"bar"};
        String returned = Joiner.on("#").skipNulls().join(values);
        assertThat(returned,is("foo#bar"));
    }

    @Test
    public void testJoinStringsUseForNull(){
        String[] values = new String[]{"foo",null,"bar"};
        String returned = Joiner.on("#").useForNull("missing").join(values);
        assertThat(returned,is("foo#missing#bar"));
    }

    @Test (expected = NullPointerException.class)
    public void testJoinStringsNoNullHandler(){
        String[] values = new String[]{"foo",null,"bar"};
        Joiner.on("#").join(values);
        fail("Should not get here");
    }
}
