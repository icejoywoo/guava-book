package bbejeck.guava.chapter7.subscriber;

import bbejeck.guava.chapter7.events.BuyEvent;
import bbejeck.guava.chapter7.events.SellEvent;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.List;

/**
 * User: Bill Bejeck
 * Date: 4/26/13
 * Time: 12:46 PM
 */
public class AllTradesAuditor {

    private List<BuyEvent> buyEvents = Lists.newArrayList();
    private List<SellEvent> sellEvents = Lists.newArrayList();

    public AllTradesAuditor(EventBus eventBus) {
        eventBus.register(this);
    }

    @Subscribe
    public void auditSell(SellEvent sellEvent){
        sellEvents.add(sellEvent);
        System.out.println("Received TradeSellEvent "+sellEvent);
    }

    @Subscribe
    public void auditBuy(BuyEvent buyEvent){
        buyEvents.add(buyEvent);
        System.out.println("Received TradeBuyEvent "+buyEvent);
    }


    public List<BuyEvent> getBuyEvents() {
        return buyEvents;
    }

    public List<SellEvent> getSellEvents() {
        return sellEvents;
    }
}
