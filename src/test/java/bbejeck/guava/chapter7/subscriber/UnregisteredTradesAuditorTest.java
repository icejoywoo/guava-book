package bbejeck.guava.chapter7.subscriber;

import bbejeck.guava.chapter7.EventBusTestBase;
import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * User: Bill Bejeck
 * Date: 4/28/13
 * Time: 9:12 PM
 */
public class UnregisteredTradesAuditorTest extends EventBusTestBase {
    private EventBus eventBus;
    private UnregisteredTradesAuditor unregisteredTradesAuditor;


    @Before
    public void setUp() throws Exception {
       eventBus = getEventBus();
       unregisteredTradesAuditor = new UnregisteredTradesAuditor(eventBus);
    }

    @Test
    public void postThenPostAgainUnregistered() {
        eventBus.post(buyEventBuilder().build());
        eventBus.post(sellEventBuilder().build());
        assertThat(unregisteredTradesAuditor.getBuyEvents().size(),is(1));
        assertThat(unregisteredTradesAuditor.getSellEvents().size(),is(1));

        unregisteredTradesAuditor.getBuyEvents().clear();
        unregisteredTradesAuditor.getSellEvents().clear();
        unregisteredTradesAuditor.unregister();

        eventBus.post(buyEventBuilder().build());
        eventBus.post(sellEventBuilder().build());
        assertThat(unregisteredTradesAuditor.getBuyEvents().isEmpty(),is(true));
        assertThat(unregisteredTradesAuditor.getSellEvents().isEmpty(),is(true));


    }
}
