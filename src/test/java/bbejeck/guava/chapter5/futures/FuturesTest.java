package bbejeck.guava.chapter5.futures;

import bbejeck.guava.chapter5.FutureFallbackImpl;
import bbejeck.guava.common.SearchingTestBase;
import bbejeck.guava.common.support.model.Person;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: bbejeck
 */

public class FuturesTest extends SearchingTestBase {

    private int numberTasks;
    private CountDownLatch startSignal;
    private CountDownLatch doneSignal;
    private ListeningExecutorService executorService;

    @Before
    public void setUp() throws Exception {
        numberTasks = 5;
        startSignal = new CountDownLatch(1);
        doneSignal = new CountDownLatch(numberTasks);
        executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
    }

    @After
    public void tearDown() {
        executorService.shutdownNow();
    }

    @Test
    public void testWithFutureFallbackFailed() throws Exception {
        ListenableFuture<String> readFileAsString = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Files.toString(new File("bogus"), Charsets.UTF_8);
            }
        });
        ListenableFuture<String> withFallbackFutureFailed = Futures.withFallback(readFileAsString, new FutureFallbackImpl());
        assertThat(withFallbackFutureFailed.get(), is("Not Found"));
    }

    @Test
    public void testAllAsListSuccess() throws Exception {
        ListenableFuture<List<Person>> lf1 = getPersonsByFirstNameFuture("martin", false);
        ListenableFuture<List<Person>> lf2 = getPersonsByFirstNameFuture("bob", false);
        ListenableFuture<List<Person>> lf3 = getPersonsByFirstNameFuture("emily", false);
        ListenableFuture<List<Person>> lf4 = getPersonsByFirstNameFuture("mona", false);
        ListenableFuture<List<Person>> lf5 = getPersonsByFirstNameFuture("tom", false);

        ListenableFuture<List<List<Person>>> lfResults = Futures.allAsList(lf1, lf2, lf3, lf4, lf5);
        startSignal.countDown();
        List<List<Person>> listOfPersonLists = lfResults.get();
        assertThat(listOfPersonLists.size() > 0, is(true));
        for (List<Person> personList : listOfPersonLists) {
            assertThat(personList.size() > 0, is(true));
        }
    }

    @Test(expected = ExecutionException.class)
    public void testAllAsListSuccessOneFailure() throws Exception {
        ListenableFuture<List<Person>> lf1 = getPersonsByFirstNameFuture("martin", false);
        ListenableFuture<List<Person>> lf2 = getPersonsByFirstNameFuture("bob", false);
        ListenableFuture<List<Person>> lf3 = getPersonsByFirstNameFuture("emily", true);
        ListenableFuture<List<Person>> lf4 = getPersonsByFirstNameFuture("mona", false);
        ListenableFuture<List<Person>> lf5 = getPersonsByFirstNameFuture("tom", false);

        ListenableFuture<List<List<Person>>> lfResults = Futures.allAsList(lf1, lf2, lf3, lf4, lf5);
        startSignal.countDown();
        List<List<Person>> listOfPersonLists = lfResults.get();
        fail("should not get here");
    }

    @Test
    public void testSuccessfulAsListSuccessOneFailure() throws Exception {
        ListenableFuture<List<Person>> lf1 = getPersonsByFirstNameFuture("martin", true);
        ListenableFuture<List<Person>> lf2 = getPersonsByFirstNameFuture("bob", false);
        ListenableFuture<List<Person>> lf3 = getPersonsByFirstNameFuture("emily", true);
        ListenableFuture<List<Person>> lf4 = getPersonsByFirstNameFuture("mona", false);
        ListenableFuture<List<Person>> lf5 = getPersonsByFirstNameFuture("tom", false);

        ListenableFuture<List<List<Person>>> lfResults = Futures.successfulAsList(lf1, lf2, lf3, lf4, lf5);
        startSignal.countDown();
        List<List<Person>> listOfPersonLists = lfResults.get();

        assertThat(listOfPersonLists.size() == 5, is(true));

        //have null values failed
        assertThat(listOfPersonLists.get(0), is(nullValue()));
        assertThat(listOfPersonLists.get(2), is(nullValue()));

        //succeeded returned valid results
        assertThat(listOfPersonLists.get(1).size() > 0, is(true));
        assertThat(listOfPersonLists.get(3).size() > 0, is(true));
        assertThat(listOfPersonLists.get(4).size() > 0, is(true));
    }

    private ListenableFuture<List<Person>> getPersonsByFirstNameFuture(final String firstName, final boolean error) {
        return executorService.submit(new Callable<List<Person>>() {
            @Override
            public List<Person> call() throws Exception {
                startSignal.await();
                if (error) {
                    throw new RuntimeException("Ooops!");
                }
                List<String> ids = luceneSearcher.search("firstName:" + firstName);
                List<Person> persons = dbService.getPersonsById(ids);
                return persons;
            }
        });
    }

    private Callable<String> getSimpleCallable(final String label) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return label;
            }
        };
    }
}
