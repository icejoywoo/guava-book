package bbejeck.guava.chapter5.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 7/17/13
 * Time: 5:10 PM
 */
public class RateLimiterTest {


     @Test
     public void rateLimiterTest() throws Exception {
         final CountDownLatch waitSignal = new CountDownLatch(10);
         final ProtectedResource protectedResource = new ProtectedResource();
         Runnable expensiveThread = new Runnable() {
             @Override
             public void run() {
                protectedResource.expensiveOperation();
                waitSignal.countDown();
             }
         };
         Executor executor = Executors.newFixedThreadPool(10);
         for (int i = 0; i <10; i++) {
               executor.execute(expensiveThread);
         }
         waitSignal.await();
         assertThat(waitSignal.getCount(),is(0L));
     }



    private class ProtectedResource {
         private RateLimiter rateLimiter = RateLimiter.create(2.0);


         public void expensiveOperation(){
             rateLimiter.acquire();
             System.out.println("Expensive operation run by "+Thread.currentThread()+" @ ["+new Date()+"]");
         }

    }
}
