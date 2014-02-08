package bbejeck.guava.chapter5;

import com.google.common.util.concurrent.FutureFallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * User: Bill Bejeck
 * Date: 4/17/13
 * Time: 10:46 PM
 */
public class FutureFallbackImpl implements FutureFallback<String> {

    @Override
    public ListenableFuture<String> create(Throwable t) throws Exception {
        if (t instanceof FileNotFoundException) {
            SettableFuture<String> settableFuture = SettableFuture.create();
            settableFuture.set("Not Found");
            return settableFuture;
        }
        throw new RuntimeException(t);
    }
}
