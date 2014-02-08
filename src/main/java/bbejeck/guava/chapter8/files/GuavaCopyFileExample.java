package bbejeck.guava.chapter8.files;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

/**
 * User: Bill Bejeck
 * Date: 5/2/13
 * Time: 12:05 AM
 */
public class GuavaCopyFileExample {
    public static void main(String[] args) {
        File original = new File("src/main/resources/sampleTextFileOne.txt");
        File copy = new File("src/main/resources/copy.txt");

        try {
            Files.copy(original, copy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
