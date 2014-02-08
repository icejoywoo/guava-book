package bbejeck.guava.chapter7.subscriber;

import bbejeck.guava.chapter7.EventBusTestBase;
import bbejeck.guava.chapter7.events.TradeAccountEvent;
import bbejeck.guava.chapter7.events.TradeType;
import bbejeck.guava.chapter7.subscriber.simple.SimpleTradeAuditor;
import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Bill Bejeck
 * Date: 4/28/13
 * Time: 8:24 PM
 */
public class SimpleTradeAuditorTest extends EventBusTestBase {

    private SimpleTradeAuditor auditor;
    private EventBus eventBus;
    private TradeAccountEvent.Builder builder;

    @Before
    public void setUp() {
        eventBus = getEventBus();
        auditor = new SimpleTradeAuditor(eventBus);
        builder = getTradeAccountEventBuilder();
    }

    @Test
    public void testSubscribeBuy() throws Exception {
        TradeAccountEvent tradeAccountEvent = builder.tradeType(TradeType.BUY).amount(5000.89).build();
        eventBus.post(tradeAccountEvent);
        List<TradeAccountEvent> events = auditor.getTradeEvents();
        assertThat(events.get(0).getTradeType(), is(TradeType.BUY));
        assertThat(events.get(0).getAmount(), is(5000.89));
    }

    @Test
    public void testSubscribeSell() throws Exception {
        TradeAccountEvent tradeAccountEvent = builder.tradeType(TradeType.SELL).amount(5000.89).build();
        eventBus.post(tradeAccountEvent);
        List<TradeAccountEvent> events = auditor.getTradeEvents();
        assertThat(events.get(0).getTradeType(), is(TradeType.SELL));
        assertThat(events.get(0).getAmount(), is(5000.89));
    }

    @Test
    public void testSubscribeBuySell() throws Exception {
        TradeAccountEvent tradeAccountEvent = builder.tradeType(TradeType.SELL).amount(5000.89).build();
        eventBus.post(tradeAccountEvent);
        tradeAccountEvent = builder.tradeType(TradeType.BUY).amount(80000).build();
        eventBus.post(tradeAccountEvent);
        List<TradeAccountEvent> events = auditor.getTradeEvents();

        assertThat(events.get(0).getTradeType(), is(TradeType.SELL));
        assertThat(events.get(0).getAmount(), is(5000.89));

        assertThat(events.get(1).getTradeType(), is(TradeType.BUY));
        assertThat(events.get(1).getAmount(), is(80000D));
    }

}
