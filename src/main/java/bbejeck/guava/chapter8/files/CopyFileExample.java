package bbejeck.guava.chapter8.files;

import java.io.*;

/**
 * User: Bill Bejeck
 * Date: 5/1/13
 * Time: 11:34 PM
 */

public class CopyFileExample {
    public static void main(String[] args) {

        File original = new File("src/main/resources/sampleTextFileOne.txt");
        File copy = new File("src/main/resources/copy.txt");
        byte[] buffer = new byte[1024];
        FileInputStream is = null;
        FileOutputStream os = null;
        int numberRead;
        try {
            is = new FileInputStream(original);
            os = new FileOutputStream(copy, false);
            while((numberRead = is.read(buffer))!= -1){
                os.write(buffer,0,numberRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                is.close();
                os.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
