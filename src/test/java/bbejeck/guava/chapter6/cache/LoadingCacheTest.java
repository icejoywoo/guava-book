package bbejeck.guava.chapter6.cache;

import bbejeck.guava.common.model.TradeAccount;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * User: Bill Bejeck
 * Date: 7/22/13
 * Time: 10:53 PM
 */
public class LoadingCacheTest {
    private CacheLoader<String, TradeAccount> cacheLoader = mock(CacheLoader.class);

    @Test
    public void testCacheLoaderOnlyCalledOnce() throws Exception {
        TradeAccount expectedTradeAccount = new TradeAccount.Builder().balance(250000.12).build();
        when(cacheLoader.load("223")).thenReturn(expectedTradeAccount);
        LoadingCache<String, TradeAccount> tradeAccountCache = CacheBuilder.newBuilder()
                .maximumSize(5000L)
                .build(cacheLoader);

        TradeAccount tradeAccount = tradeAccountCache.get("223");
        assertThat(tradeAccount, is(expectedTradeAccount));

        for (int i = 0; i < 10; i++) {
            TradeAccount tradeAccount1 = tradeAccountCache.get("223");
            assertThat(tradeAccount1, is(expectedTradeAccount));
        }
        verify(cacheLoader, times(1)).load("223");
    }

    @Test
    public void testCacheLoaderCalledInitiallyAndAfterExpiration() throws Exception {
        TradeAccount expectedTradeAccount = new TradeAccount.Builder().balance(250000.12).build();
        when(cacheLoader.load("223")).thenReturn(expectedTradeAccount);
        LoadingCache<String, TradeAccount> tradeAccountCache = CacheBuilder.newBuilder()
                .maximumSize(5000L)
                .expireAfterAccess(500l, TimeUnit.MILLISECONDS)
                .build(cacheLoader);

        TradeAccount tradeAccount = tradeAccountCache.get("223");
        assertThat(tradeAccount, is(expectedTradeAccount));

        Thread.sleep(1000);

        TradeAccount tradeAccount1 = tradeAccountCache.get("223");
        assertThat(tradeAccount1, is(expectedTradeAccount));
        verify(cacheLoader, times(2)).load("223");
    }

}
