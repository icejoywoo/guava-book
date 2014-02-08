package bbejeck.guava.chapter7.config;

import bbejeck.guava.chapter7.events.TradeAccountEvent;
import bbejeck.guava.chapter7.events.TradeType;
import bbejeck.guava.chapter7.publisher.simple.SimpleTradeExecutor;
import bbejeck.guava.chapter7.subscriber.simple.SimpleTradeAuditor;
import bbejeck.guava.common.model.TradeAccount;
import org.junit.Before;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * User: Bill Bejeck
 * Date: 4/26/13
 * Time: 4:31 PM
 */
public class SimpleEventBusSpringJavaConfigTest {

    SimpleTradeExecutor tradeExecutor;
    SimpleTradeAuditor  tradeAuditor;

    @Before
    public void setUp(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SimpleEventBusConfig.class);
         tradeExecutor = ctx.getBean(SimpleTradeExecutor.class);
         tradeAuditor = ctx.getBean(SimpleTradeAuditor.class);
    }

    @Test
    public void testSendMessage(){
        TradeAccount tradeAccount = new TradeAccount.Builder().build();
        tradeExecutor.executeTrade(tradeAccount,5000.65, TradeType.BUY);
        List<TradeAccountEvent> tradeAccountEvents = tradeAuditor.getTradeEvents();
        assertThat(tradeAccountEvents.get(0).getTradeAccount(),is(tradeAccount));
    }
}
