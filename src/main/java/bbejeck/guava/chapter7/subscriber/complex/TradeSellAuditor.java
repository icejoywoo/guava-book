package bbejeck.guava.chapter7.subscriber.complex;

import bbejeck.guava.chapter7.events.SellEvent;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.apache.lucene.util.automaton.CompiledAutomaton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import static com.google.common.base.Preconditions.*;
/**
 * User: Bill Bejeck
 * Date: 4/26/13
 * Time: 12:40 PM
 */
@Component
public class TradeSellAuditor {

    private List<SellEvent> sellEvents = Lists.newArrayList();

    @Autowired
    public TradeSellAuditor(@Qualifier("salesEventBus") EventBus eventBus) {
        checkArgument(eventBus != null,"EventBus can't be null");
         eventBus.register(this);
    }

    @Subscribe
    public void auditSell(SellEvent sellEvent){
        sellEvents.add(sellEvent);
        System.out.println("Received TradeSellEvent "+sellEvent);
    }

    public List<SellEvent> getSellEvents() {
        return sellEvents;
    }
}
