package bbejeck.guava.chapter8.files;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

/**
 * User: Bill Bejeck
 * Date: 5/2/13
 * Time: 9:35 PM
 */
public class GuavaMoveFileExample {

    public static void main(String[] args) {
        File original = new File("src/main/resources/copy.txt");
        File newFile = new File("src/main/resources/newFile.txt");
        try{
            Files.move(original, newFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
