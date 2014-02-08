package bbejeck.guava.chapter8.files;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 * User: Bill Bejeck
 * Date: 5/2/13
 * Time: 10:00 PM
 */
public class GuavaReadingWritingFilesExample {

    public static void main(String[] args) throws Exception {
        List<String> sentences = Lists.newArrayList();
        sentences.add("This is line one\n");
        sentences.add("This is line two\n");
        sentences.add("This is line three\n");
        sentences.add("This is line four\n");

        File file = new File("src/main/resources/sentences.txt");

        BufferedWriter writer = Files.newWriter(file, Charsets.UTF_8);
        for (String sentence : sentences) {
             writer.write(sentence);
        }
        writer.close();

        BufferedReader reader = Files.newReader(file,Charsets.UTF_8);
        String line;
        while((line= reader.readLine()) != null){
            System.out.println(line);
        }
        reader.close();
    }
}
