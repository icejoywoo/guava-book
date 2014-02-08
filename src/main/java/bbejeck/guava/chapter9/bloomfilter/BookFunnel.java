package bbejeck.guava.chapter9.bloomfilter;

import bbejeck.guava.common.model.Book;
import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

/**
 * User: Bill Bejeck
 * Date: 5/10/13
 * Time: 7:28 AM
 */
public enum  BookFunnel implements Funnel<Book> {
    //This is the single enum value
    FUNNEL;
    public void funnel(Book from, PrimitiveSink into) {
        into.putBytes(from.getIsbn().getBytes(Charsets.UTF_8))
            .putDouble(from.getPrice());
    }
}
