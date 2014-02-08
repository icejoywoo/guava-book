package bbejeck.guava.chapter9.hashing;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * User: Bill Bejeck
 * Date: 5/8/13
 * Time: 11:12 PM
 */
public class HashingExamples {

    public static void main(String[] args) {
        HashFunction adler32 = Hashing.adler32();
        HashFunction crc32 = Hashing.crc32();
        HashFunction gfh = Hashing.goodFastHash(128);
        HashFunction murmur3_32 = Hashing.murmur3_32();
        HashFunction murmur3_128 = Hashing.murmur3_128();

        HashFunction sha1 = Hashing.sha1();
        HashFunction sha256 = Hashing.sha256();
        HashFunction sha512 = Hashing.sha512();

        String testString = "FooBarBaz";
        System.out.println("adler32 hash " + adler32.hashString(testString).toString());
        System.out.println("crc32 hash " + crc32.hashString(testString).toString());
        System.out.println("gfh hash "+ gfh.hashString(testString).toString());
        System.out.println("murmur3_32 hash " + murmur3_32.hashString(testString).toString());
        System.out.println("murmur3_128 hash " + murmur3_128.hashString(testString).toString());
        System.out.println("Sha1 hash " + sha1.hashString(testString).toString());
        System.out.println("Sha256 hash " + sha256.hashString(testString).toString());
        System.out.println("Sha512 hash " + sha512.hashString(testString).toString());
    }

}
