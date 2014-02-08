package bbejeck.guava.chapter9.hashing;

import bbejeck.guava.common.model.City;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

/**
 * User: Bill Bejeck
 * Date: 5/11/13
 * Time: 10:46 PM
 */
public class HasherExamples {


    public static void main(String[] args) {
        HashFunction murmur3_128 = Hashing.murmur3_128();
        Hasher murmurHasher = murmur3_128.newHasher();
        City city = new City.Builder().build();
        HashCode hashCode = murmurHasher.putString(city.getName())
                                        .putDouble(city.getAverageRainfall()).hash();
        System.out.println(hashCode);

        HashFunction sha256 = Hashing.sha256();
        Hasher sha256Hasher = sha256.newHasher();
         hashCode = sha256Hasher.putString(city.getName())
                .putDouble(city.getAverageRainfall()).hash();
        System.out.println(hashCode);
    }
}
