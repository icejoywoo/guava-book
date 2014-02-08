package bbejeck.guava.chapter6.cache;

/**
 * User: Bill Bejeck
 * Date: 4/22/13
 * Time: 9:37 PM
 */

import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;

public abstract class BaseRemovalListener<K, V> implements RemovalListener<K, V> {

    protected RemovalCause removalCause;
    protected K removedKey;
    protected V removedValue;


    public RemovalCause getRemovalCause() {
        return removalCause;
    }

    public K getRemovedKey() {
        return removedKey;
    }

    public V getRemovedValue() {
        return removedValue;
    }
}
