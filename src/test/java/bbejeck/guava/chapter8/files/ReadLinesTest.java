package bbejeck.guava.chapter8.files;

/**
 * User: Bill Bejeck
 * Date: 5/2/13
 * Time: 11:22 PM
 */

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.util.List;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ReadLinesTest {

    @Test
    public void readFileIntoListOfStringsTest() throws Exception {
        File file = new File("src/main/resources/lines.txt");
        List<String> expectedLines = Lists.newArrayList("The quick brown","fox jumps over","the lazy dog");
        List<String> readLines = Files.readLines(file, Charsets.UTF_8);
        assertThat(expectedLines,is(readLines));
    }

    @Test
    public void readLinesWithProcessor() throws Exception {
        File file = new File("src/main/resources/books.data");
        List<String> expectedLines = Lists.newArrayList("Being A Great Cook","Art is Fun","Be an Architect","History of Football","Gardening My Way","How I Made Millions");
        List<String> readLines = Files.readLines(file, Charsets.UTF_8, new ToListLineProcessor());
        assertThat(expectedLines,is(readLines));
    }

}
