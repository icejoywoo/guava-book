package bbejeck.guava.chapter6.cache;

import bbejeck.guava.common.model.TradeAccount;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * User: Bill Bejeck
 * Date: 4/20/13
 * Time: 11:49 PM
 */
public class TradeAccountRemovalListener extends BaseRemovalListener<String, TradeAccount> {


    public void onRemoval(RemovalNotification<String, TradeAccount> notification) {
        this.removalCause = notification.getCause();
        this.removedKey = notification.getKey();
        this.removedValue = notification.getValue();
    }
}
