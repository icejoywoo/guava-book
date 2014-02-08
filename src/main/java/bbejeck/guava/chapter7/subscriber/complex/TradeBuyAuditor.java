package bbejeck.guava.chapter7.subscriber.complex;

import bbejeck.guava.chapter7.events.BuyEvent;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
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
public class TradeBuyAuditor {

    private List<BuyEvent> buyEvents = Lists.newArrayList();

    @Autowired
    public TradeBuyAuditor(@Qualifier("buysEventBus")EventBus eventBus) {
         checkArgument(eventBus != null,"EventBus can't be null");
         eventBus.register(this);
    }

    @Subscribe
    public void auditBuy(BuyEvent buyEvent){
        buyEvents.add(buyEvent);
        System.out.println("Received TradeBuyEvent "+buyEvent);
    }

    public List<BuyEvent> getBuyEvents() {
        return buyEvents;
    }
}
