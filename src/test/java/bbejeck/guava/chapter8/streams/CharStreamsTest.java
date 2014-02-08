package bbejeck.guava.chapter8.streams;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.common.io.OutputSupplier;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 5/5/13
 * Time: 9:04 PM
 */
public class CharStreamsTest {

    @Test
    public void joinTest() throws Exception {
        File f1 = new File("src/main/resources/sampleTextFileOne.txt");
        File f2 = new File("src/main/resources/sampleTextFileTwo.txt");
        File f3 = new File("src/main/resources/lines.txt");
        File joinedOutput = new File("src/test/resources/joined.txt");
        joinedOutput.deleteOnExit();

        List<InputSupplier<InputStreamReader>> inputSuppliers = getInputSuppliers(f1,f2,f3);

        InputSupplier<Reader> joinedSupplier = CharStreams.join(inputSuppliers);
        OutputSupplier<OutputStreamWriter> outputSupplier = Files.newWriterSupplier(joinedOutput, Charsets.UTF_8);
        String expectedOutputString = joinFiles(f1,f2,f3);

        CharStreams.copy(joinedSupplier,outputSupplier);
        String joinedOutputString  = joinFiles(joinedOutput);
        assertThat(joinedOutputString,is(expectedOutputString));
    }

    private String joinFiles(File ...files) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (File file : files) {
             builder.append(Files.toString(file,Charsets.UTF_8));
        }
        return builder.toString();
    }

    private List<InputSupplier<InputStreamReader>> getInputSuppliers(File ...files){
        List<InputSupplier<InputStreamReader>> list  = Lists.newArrayList();
        for (File file : files) {
            list.add(Files.newReaderSupplier(file,Charsets.UTF_8));
        }
       return list;
    }


}
