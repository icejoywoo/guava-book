package bbejeck.guava.chapter6.cache;

import bbejeck.guava.common.model.Book;
import com.google.common.cache.RemovalNotification;

/**
 * User: Bill Bejeck
 * Date: 4/20/13
 * Time: 11:24 PM
 */
public class BookRemovalListener extends BaseRemovalListener<String, Book> {

    @Override
    public void onRemoval(RemovalNotification<String, Book> notification) {
          this.removalCause = notification.getCause();
          this.removedKey = notification.getKey();
          this.removedValue = notification.getValue();
    }
}
