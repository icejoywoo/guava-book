package bbejeck.guava.chapter7.subscriber;

import bbejeck.guava.chapter7.EventBusTestBase;
import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Bill Bejeck
 * Date: 4/28/13
 * Time: 8:49 PM
 */
public class AllTradeAuditorTest extends EventBusTestBase  {

    private AllTradesAuditor allTradesAuditor;
    private EventBus eventBus;

    @Before
    public void setUp(){
        eventBus = getEventBus();
        allTradesAuditor = new AllTradesAuditor(eventBus);
    }

    @Test
    public void sellOnlyTest(){
        eventBus.post(sellEventBuilder().build());
        assertThat(allTradesAuditor.getSellEvents().size(),is(1));
        assertThat(allTradesAuditor.getBuyEvents().isEmpty(),is(true));
    }

    @Test
    public void buyOnlyTest(){
        eventBus.post(buyEventBuilder().build());
        assertThat(allTradesAuditor.getSellEvents().isEmpty(),is(true));
        assertThat(allTradesAuditor.getBuyEvents().size(),is(1));
    }

    @Test
    public void buySellEventsSameTimeTest(){
        eventBus.post(buyEventBuilder().build());
        eventBus.post(sellEventBuilder().build());
        assertThat(allTradesAuditor.getBuyEvents().size(),is(1));
        assertThat(allTradesAuditor.getSellEvents().size(),is(1));
    }
}
