package bbejeck.guava.chapter7.subscriber;

import bbejeck.guava.chapter7.events.BuyEvent;
import bbejeck.guava.chapter7.events.SellEvent;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.CountDownLatch;

/**
 * User: Bill Bejeck
 * Date: 4/29/13
 * Time: 11:12 PM
 */
public class SlowProcessSubscriber {

    private CountDownLatch doneSignal;

    public SlowProcessSubscriber(AsyncEventBus asyncEventBus, CountDownLatch doneSignal) {
        asyncEventBus.register(this);
        this.doneSignal = doneSignal;
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleEventConcurrent(BuyEvent event) {
        pause(300l);
        doneSignal.countDown();
    }

    @Subscribe
    public void handleEventNonConcurrent(SellEvent event) {
        pause(300l);
        doneSignal.countDown();
    }

    private void pause(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            //Ignore
        }
    }
}
