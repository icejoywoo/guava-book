package bbejeck.guava.chapter6.cache;

import bbejeck.guava.common.model.TradeAccount;
import bbejeck.guava.common.service.BookServiceImpl;
import bbejeck.guava.common.service.TradeAccountService;
import com.google.common.base.Ticker;
import com.google.common.cache.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Bill Bejeck
 * Date: 4/21/13
 * Time: 9:27 PM
 */
public class CacheBuilderSpecTest {
    private static TradeAccountService tradeAccountService;

    @BeforeClass
    public static void startUpBeforeAll() {
        tradeAccountService = new TradeAccountService();
    }

    @AfterClass
    public static void tearDownAfterAll() throws Exception {
        tradeAccountService.shutdown();
    };

    @Test
    public void testCacheBuilderSpec() throws Exception{
        TradeAccountRemovalListener removalListener = new TradeAccountRemovalListener();
        String spec = "concurrencyLevel=10,expireAfterAccess=1s,softValues";
        CacheBuilderSpec cacheBuilderSpec = CacheBuilderSpec.parse(spec);
        CacheBuilder cacheBuilder = CacheBuilder.from(cacheBuilderSpec);
        LoadingCache<String, TradeAccount> tradeAccountCache = cacheBuilder.ticker(Ticker.systemTicker())
            .removalListener(removalListener)
            .build(new CacheLoader<String, TradeAccount>() {
                @Override
                public TradeAccount load(String key) throws Exception {
                    return tradeAccountService.getTradeAccountById(key);
                }
            });

        //112,"Pennypacker, HJ",700889.32
        TradeAccount tradeAccount = tradeAccountCache.get("112");
        assertThat(tradeAccount.getBalance(), is(700889.32));
        Thread.sleep(1500L);
        tradeAccountCache.get("112");
        assertThat(removalListener.getRemovalCause(), is(RemovalCause.EXPIRED));
        assertThat(removalListener.getRemovedValue(), is(tradeAccount));

    }
}
