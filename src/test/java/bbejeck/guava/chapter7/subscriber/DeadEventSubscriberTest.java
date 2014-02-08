package bbejeck.guava.chapter7.subscriber;

import bbejeck.guava.chapter7.EventBusTestBase;
import bbejeck.guava.chapter7.events.SellEvent;
import bbejeck.guava.chapter7.subscriber.simple.SimpleTradeAuditor;
import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * User: Bill Bejeck
 * Date: 4/28/13
 * Time: 9:00 PM
 */
public class DeadEventSubscriberTest extends EventBusTestBase{

    private DeadEventSubscriber deadEventSubscriber;
    private SimpleTradeAuditor tradeAuditor;
    private EventBus eventBus;

    @Before
    public void setUp(){
        eventBus = getEventBus();
        //Purposely mis-configured - different EventBus, common error
        tradeAuditor = new SimpleTradeAuditor(getEventBus());
        deadEventSubscriber = new DeadEventSubscriber(eventBus);
    }

    @Test
    public void testPublishNoSubscribers(){
        SellEvent sellEvent = sellEventBuilder().build();
        eventBus.post(sellEvent);
        assertThat(tradeAuditor.getTradeEvents().isEmpty(),is(true));
        assertThat(deadEventSubscriber.getDeadEvents().size(),is(1));
        SellEvent wrappedSellEvent = (SellEvent)deadEventSubscriber.getDeadEvents().get(0).getEvent();
        assertThat(wrappedSellEvent,is(sellEvent));
    }

}
