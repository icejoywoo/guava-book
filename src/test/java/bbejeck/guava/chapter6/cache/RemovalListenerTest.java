package bbejeck.guava.chapter6.cache;

import bbejeck.guava.common.model.Book;
import bbejeck.guava.common.model.TradeAccount;
import bbejeck.guava.common.service.BookServiceImpl;
import bbejeck.guava.common.service.TradeAccountService;
import com.google.common.base.Ticker;
import com.google.common.cache.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Bill Bejeck
 * Date: 4/20/13
 * Time: 10:17 PM
 */
public class RemovalListenerTest {

    private static TradeAccountService tradeAccountService;
    private static BookServiceImpl bookService;

    @BeforeClass
    public static void startUpBeforeAll() {
        bookService = new BookServiceImpl();
        tradeAccountService = new TradeAccountService();
    }

    @AfterClass
    public static void tearDownAfterAll() throws Exception {
        bookService.shutdown();
        tradeAccountService.shutdown();
    }

    @Test
    public void testLoadingCacheExpireAfterWrite() throws Exception {
        TradeAccountRemovalListener removalListener = new TradeAccountRemovalListener();
        LoadingCache<String, TradeAccount> tradeAccountCache = CacheBuilder.newBuilder()
                .expireAfterWrite(5L, TimeUnit.MILLISECONDS)
                .maximumSize(5000L)
                .removalListener(removalListener)
                .ticker(Ticker.systemTicker())
                .build(new CacheLoader<String, TradeAccount>() {
                    @Override
                    public TradeAccount load(String key) throws Exception {
                        return tradeAccountService.getTradeAccountById(key);
                    }
                });

        //223,"Rogers, Jim",250000.12
        TradeAccount tradeAccount = tradeAccountCache.get("223");
        assertThat(tradeAccount.getBalance(), is(250000.12));
        Thread.sleep(10L);
        tradeAccountCache.get("223");
        assertThat(removalListener.getRemovalCause(), is(RemovalCause.EXPIRED));
        assertThat(removalListener.getRemovedValue(), is(tradeAccount));
    }

    @Test
    public void testRemovalAfterLastAccessed() throws Exception {
        BookRemovalListener bookRemovalListener = new BookRemovalListener();
        LoadingCache<String, Book> bookCache = CacheBuilder.newBuilder()
                .expireAfterAccess(10, TimeUnit.MILLISECONDS)
                .softValues()
                .recordStats()
                .removalListener(bookRemovalListener)
                .build(new CacheLoader<String, Book>() {
                    @Override
                    public Book load(String key) throws Exception {
                        return bookService.findBookByIsbn(key);
                    }
                });

        Book book = bookCache.get("ISBN-234567");
        assertThat(book.getAuthor(), is("Vandeley, Art"));
        assertThat(book.getIsbn(), is("ISBN-234567"));
        Thread.sleep(20);
        //Need to call again to force eviction
        Book bookII = bookCache.get("ISBN-234567");
        assertThat(bookII.getAuthor(), is("Vandeley, Art"));
        assertThat(bookII.getIsbn(), is("ISBN-234567"));
        CacheStats cacheStats = bookCache.stats();
        assertThat(cacheStats.evictionCount(),is(1l));
        assertThat(bookRemovalListener.getRemovalCause(), is(RemovalCause.EXPIRED));
        assertThat(bookRemovalListener.getRemovedKey(), is("ISBN-234567"));
        assertThat(bookRemovalListener.getRemovedValue(), is(book));


    }

    @Test
    public void testInvalidateBadValue() throws Exception {
        BookRemovalListener bookRemovalListener = new BookRemovalListener();
        LoadingCache<String, Book> bookCache = CacheBuilder.newBuilder()
                .expireAfterAccess(10, TimeUnit.HOURS)
                .softValues()
                .recordStats()
                .removalListener(bookRemovalListener)
                .build(new CacheLoader<String, Book>() {
                    @Override
                    public Book load(String key) throws Exception {
                        return bookService.findBookByIsbn(key);
                    }
                });

        Book book = bookCache.get("ISBN-234567");
        assertThat(book.getTitle(), is("Be an Architect"));
        bookCache.invalidate("ISBN-234567");
        assertThat(bookRemovalListener.getRemovalCause(), is(RemovalCause.EXPLICIT));
        assertThat(bookRemovalListener.getRemovedValue().getTitle(), is("Be an Architect"));
    }

    @Test
    public void testRefreshingCacheValues() throws Exception {
        TradeAccountRemovalListener removalListener = new TradeAccountRemovalListener();
        LoadingCache<String, TradeAccount> tradeAccountCache = CacheBuilder.newBuilder()
                .concurrencyLevel(10)
                .refreshAfterWrite(5L, TimeUnit.MILLISECONDS)
                .removalListener(removalListener)
                .ticker(Ticker.systemTicker())
                .recordStats()
                .build(new CacheLoader<String, TradeAccount>() {
                    @Override
                    public TradeAccount load(String key) throws Exception {
                        return tradeAccountService.getTradeAccountById(key);
                    }
                });

        //223,"Rogers, Jim",250000.12
        TradeAccount tradeAccount = tradeAccountCache.get("223");
        assertThat(tradeAccount.getBalance(), is(250000.12));
        Thread.sleep(10L);
        tradeAccountCache.get("223");
        CacheStats stats = tradeAccountCache.stats();
        assertThat(stats.loadSuccessCount(),is(2l));
        assertThat(removalListener.getRemovalCause(), is(RemovalCause.REPLACED));
        assertThat(removalListener.getRemovedValue(), is(tradeAccount));
    }

}
