package bbejeck.guava.chapter7.publisher;

import bbejeck.guava.chapter7.events.BuyEvent;
import bbejeck.guava.chapter7.events.SellEvent;
import bbejeck.guava.chapter7.events.TradeAccountEvent;
import bbejeck.guava.chapter7.events.TradeType;
import bbejeck.guava.common.model.TradeAccount;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * User: Bill Bejeck
 * Date: 4/26/13
 * Time: 11:29 AM
 */
public class BuySellTradeExecutor {

    private EventBus eventBus;

    public BuySellTradeExecutor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void executeTrade(TradeAccount tradeAccount, double amount, TradeType tradeType) {
        TradeAccountEvent tradeAccountEvent = processTrade(tradeAccount, amount, tradeType);
        eventBus.post(tradeAccountEvent);
    }

    private TradeAccountEvent processTrade(TradeAccount tradeAccount, double amount, TradeType tradeType) {
        Date executionTime = new Date();
        String message = String.format("Processed trade for %s of amount %n type %s @ %s", tradeAccount, amount, tradeType, executionTime);
        TradeAccountEvent tradeAccountEvent;
        if (tradeType.equals(TradeType.BUY)) {
            tradeAccountEvent = new BuyEvent(tradeAccount, amount, executionTime);

        } else {
            tradeAccountEvent = new SellEvent(tradeAccount, amount, executionTime);
        }
        System.out.println(message);
        return tradeAccountEvent;
    }
}
