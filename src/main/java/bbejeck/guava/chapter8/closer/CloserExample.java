package bbejeck.guava.chapter8.closer;

import com.google.common.io.Closer;

import java.io.*;

/**
 * User: Bill Bejeck
 * Date: 5/6/13
 * Time: 10:39 PM
 */
public class CloserExample {

    public static void main(String[] args) throws IOException {
        Closer closer = Closer.create();
        try {
            File destination = new File("src/main/resources/copy.txt");
            destination.deleteOnExit();

            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/sampleTextFileOne.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(destination));
            closer.register(reader);
            closer.register(writer);

            String line;
            while((line = reader.readLine())!=null){
                writer.write(line);
            }

        } catch (Throwable t) {
            throw closer.rethrow(t);
        } finally {
            closer.close();
        }
    }
}
