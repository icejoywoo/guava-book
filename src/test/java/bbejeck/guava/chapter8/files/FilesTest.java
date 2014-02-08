package bbejeck.guava.chapter8.files;


import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * User: Bill Bejeck
 * Date: 5/3/13
 * Time: 8:39 AM
 */
public class FilesTest {


    @Test
    public void appendingWritingToFileTest() throws IOException {
        File file = new File("src/test/resources/quote.txt");
        file.deleteOnExit();

        String hamletQuoteStart = "To be, or not to be";
        Files.write(hamletQuoteStart,file, Charsets.UTF_8);
        assertThat(Files.toString(file,Charsets.UTF_8),is(hamletQuoteStart));

        String hamletQuoteEnd = ",that is the question";
        Files.append(hamletQuoteEnd,file,Charsets.UTF_8);
        assertThat(Files.toString(file, Charsets.UTF_8), is(hamletQuoteStart + hamletQuoteEnd));

        String overwrite = "Overwriting the file";
        Files.write(overwrite, file, Charsets.UTF_8);
        assertThat(Files.toString(file, Charsets.UTF_8), is(overwrite));
    }

    @Test
    public void writeBytesToFileTest() throws IOException {
        File file = new File("src/main/resources/sampleTextFileOne.txt");
        File testFile = new File("src/test/resources/testOutput.txt");
        testFile.deleteOnExit();

        byte[] bytes = Files.toByteArray(file);
        Files.write(bytes,testFile);

        assertThat(Files.equal(file,testFile),is(true));

    }


}
