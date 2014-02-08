package bbejeck.guava.chapter7.publisher.simple;

import bbejeck.guava.chapter7.events.TradeAccountEvent;
import bbejeck.guava.chapter7.events.TradeType;
import bbejeck.guava.common.model.TradeAccount;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: Bill Bejeck
 * Date: 4/26/13
 * Time: 11:29 AM
 */
@Component
public class SimpleTradeExecutor {

    private EventBus eventBus;

    @Autowired
    public SimpleTradeExecutor(EventBus eventBus) {
        this.eventBus = checkNotNull(eventBus, "EventBus can't be null");
    }

    public void executeTrade(TradeAccount tradeAccount, double amount, TradeType tradeType) {
        TradeAccountEvent tradeAccountEvent = processTrade(tradeAccount, amount, tradeType);
        eventBus.post(tradeAccountEvent);
    }

    protected TradeAccountEvent processTrade(TradeAccount tradeAccount, double amount, TradeType tradeType) {
        Date executionTime = new Date();
        String message = String.format("Processed trade for %s of amount %n type %s @ %s", tradeAccount, amount, tradeType, executionTime);
        TradeAccountEvent tradeAccountEvent = new TradeAccountEvent(tradeAccount, amount, executionTime, tradeType);
        System.out.println(message);
        return tradeAccountEvent;
    }
}
