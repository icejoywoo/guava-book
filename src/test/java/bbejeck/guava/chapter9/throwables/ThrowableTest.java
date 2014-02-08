package bbejeck.guava.chapter9.throwables;

import com.google.common.base.Throwables;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 5/11/13
 * Time: 9:29 PM
 */
public class ThrowableTest {

    @Test
    public void testGetCausalChain() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        List<Throwable> throwables = null;
        Callable<FileInputStream> fileCallable = new Callable<FileInputStream>() {
            @Override
            public FileInputStream call() throws Exception {
                return new FileInputStream("Bogus file");
            }
        };
        Future<FileInputStream> fisFuture = executor.submit(fileCallable);
        try {
            fisFuture.get();
        } catch (Exception e) {
            throwables = Throwables.getCausalChain(e);
        }
        assertThat(throwables.get(0).getClass().isAssignableFrom(ExecutionException.class),is(true));
        assertThat(throwables.get(1).getClass().isAssignableFrom(FileNotFoundException.class),is(true));
        executor.shutdownNow();
    }

    @Test
    public void testGetRootCause() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Throwable cause = null;
        final String nullString = null;
        Callable<String> stringCallable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return nullString.substring(0,2);
            }
        };
        Future<String> stringFuture = executor.submit(stringCallable);
        try {
            stringFuture.get();
        } catch (Exception e) {
            cause = Throwables.getRootCause(e);
        }
        assertThat(cause.getClass().isAssignableFrom(NullPointerException.class),is(true));
        executor.shutdownNow();
    }

}
