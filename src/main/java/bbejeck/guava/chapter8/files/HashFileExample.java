package bbejeck.guava.chapter8.files;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

/**
 * User: Bill Bejeck
 * Date: 5/3/13
 * Time: 8:01 AM
 */
public class HashFileExample {

    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/sampleTextFileOne.txt");
        HashCode hashCode = Files.hash(file, Hashing.md5());
        System.out.println(hashCode);
    }
}
